package com.github.ntngel1.linkedout.lib_telegram

import android.content.Context
import com.github.ntngel1.linkedout.lib_extensions.DeviceSpecsUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import org.drinkless.td.libcore.BuildConfig
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Well, it's not so easy to understand.
 * So much stuff with concurrency and handling different states
 *
 * TODO: Maybe possible to make it easier?
 *       Also it will be nice to remove runBlocking calls
 */
@Singleton
class TelegramClient @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val updatesHandler = Client.ResultHandler { result ->
        onUpdateReceived(result as TdApi.Update)
    }

    private val updateExceptionHandler = Client.ExceptionHandler { throwable ->
        onUpdateError(throwable)
    }

    private val exceptionHandler = Client.ExceptionHandler { throwable ->
        onError(throwable)
    }

    private val client = Client.create(updatesHandler, updateExceptionHandler, exceptionHandler)

    private val initializedStateChannel = Channel<Unit>(capacity = Channel.CONFLATED)
    private val phoneNumberStateChannel = Channel<Unit>(capacity = Channel.CONFLATED)

    init {
        client.send(TdApi.SetLogVerbosityLevel(VERBOSITY_LEVEL_ERRORS), null)
    }

    suspend fun send(query: TdApi.Function): TdApi.Object = suspendCoroutine { cont ->
        Timber.d("Received query = $query")
        client.send(query, { result ->
            Timber.d("Received result from query = $query. Result = $result")
            cont.resume(result)
        }, { throwable ->
            Timber.e("Received exception from query = $query. Exception = $throwable")
            cont.resumeWithException(throwable)
        })
    }

    suspend fun waitForInitialization() {
        initializedStateChannel.receive()
    }

    suspend fun waitForPhoneNumber() {
        phoneNumberStateChannel.receive()
    }

    private fun onUpdateReceived(update: TdApi.Update) {
        Timber.d("Received update = $update")
        when (update) {
            is TdApi.UpdateAuthorizationState -> handleAuthorizationStateUpdate(update)
        }
    }

    private fun handleAuthorizationStateUpdate(update: TdApi.UpdateAuthorizationState) {
        if (update.authorizationState !is TdApi.AuthorizationStateWaitTdlibParameters &&
            update.authorizationState !is TdApi.AuthorizationStateWaitEncryptionKey
        ) {
            runBlocking { initializedStateChannel.send(Unit) }
        }

        when (update.authorizationState) {
            is TdApi.AuthorizationStateWaitTdlibParameters -> client.send(
                makeSetTdLibParametersRequest(),
                null
            )
            is TdApi.AuthorizationStateWaitEncryptionKey -> {
                client.send(TdApi.SetDatabaseEncryptionKey(byteArrayOf())) {
                    client.send(
                        TdApi.CheckDatabaseEncryptionKey(
                            byteArrayOf()
                        ),
                        null
                    )
                }
            }
            is TdApi.AuthorizationStateWaitPhoneNumber -> runBlocking {
                phoneNumberStateChannel.send(Unit)
            }
        }
    }

    private fun onUpdateError(throwable: Throwable) {
        Timber.e("onUpdateError: throwable = $throwable")
    }

    private fun onError(throwable: Throwable) {
        Timber.e("onError: throwable = $throwable")
    }

    private fun makeSetTdLibParametersRequest(): TdApi.SetTdlibParameters {
        // Cannot use named parameters directly in constructor (because it's Java),
        // so having this fields in there just for readability
        val useTestEnvironment = BuildConfig.DEBUG
        val databaseDirectory = context.filesDir.absolutePath + "/"
        val filesDirectory = context.getExternalFilesDir(null)!!.absolutePath + "/"
        val useFileDatabase = true
        val useChatInfoDatabase = true
        val useMessageDatabase = true
        val useSecretChats = false
        val apiId = BuildConfig.API_ID
        val apiHash = BuildConfig.API_HASH
        val systemLanguageCode = Locale.getDefault().toLanguageTag()
        val deviceModel = DeviceSpecsUtil.getDeviceModel()
        val systemVersion = DeviceSpecsUtil.getSystemVersion()
        val applicationVersion = BuildConfig.VERSION_CODE.toString()
        val enableStorageOptimizer = true
        val ignoreFileNames = false

        return TdApi.SetTdlibParameters(
            TdApi.TdlibParameters(
                useTestEnvironment,
                databaseDirectory,
                filesDirectory,
                useFileDatabase,
                useChatInfoDatabase,
                useMessageDatabase,
                useSecretChats,
                apiId,
                apiHash,
                systemLanguageCode,
                deviceModel,
                systemVersion,
                applicationVersion,
                enableStorageOptimizer,
                ignoreFileNames
            )
        )
    }

    companion object {
        private const val VERBOSITY_LEVEL_ERRORS = 1
    }
}