package com.github.ntngel1.linkedout.lib_telegram

import android.content.Context
import com.github.ntngel1.linkedout.lib_utils.DeviceSpecsUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import org.drinkless.td.libcore.BuildConfig
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
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
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

    private val updatesHandler = Client.ResultHandler { result ->
        onUpdateReceived(result as TdApi.Update)
    }

    private val updateExceptionHandler = Client.ExceptionHandler { throwable ->
        onUpdateError(throwable)
    }

    private val exceptionHandler = Client.ExceptionHandler { throwable ->
        onError(throwable)
    }

    val client: Client = Client.create(updatesHandler, updateExceptionHandler, exceptionHandler)
    val authorizationStatesChannel = BroadcastChannel<TdApi.AuthorizationState>(Channel.CONFLATED)

    init {
        client.send(TdApi.SetLogVerbosityLevel(VERBOSITY_LEVEL_ERRORS), null)
    }

    suspend inline fun <reified T> send(query: TdApi.Function): T = suspendCoroutine { cont ->
        Timber.d("Received query = $query")

        client.send(query, { result ->
            Timber.d("Received result from query = ${query}\n Result = $result")
            if (result is T) {
                cont.resume(result)
            } else {
                val error = result as TdApi.Error
                cont.resumeWithException(
                    TelegramException(error.code, error.message)
                )
            }
        }, { throwable ->
            Timber.e("Received exception from query = $query\n Exception = $throwable")
            cont.resumeWithException(throwable)
        })
    }

    suspend fun execute(query: TdApi.Function): Unit = suspendCoroutine { cont ->
        Timber.d("Received query = $query")

        client.send(query, { result ->
            Timber.d("Received result from query = ${query}\n Result = $result")
            if (result !is TdApi.Error) {
                cont.resume(Unit)
            } else {
                cont.resumeWithException(
                    TelegramException(result.code, result.message)
                )
            }
        }, { throwable ->
            Timber.e("Received exception from query = $query\n Exception = $throwable")
            cont.resumeWithException(throwable)
        })
    }

    private fun onUpdateReceived(update: TdApi.Update) {
        Timber.d("Received update = $update")
        when (update) {
            is TdApi.UpdateAuthorizationState -> handleAuthorizationStateUpdate(update)
        }
    }

    private fun handleAuthorizationStateUpdate(update: TdApi.UpdateAuthorizationState) = launch {
        authorizationStatesChannel.send(update.authorizationState)
        when (update.authorizationState) {
            is TdApi.AuthorizationStateWaitTdlibParameters -> client.send(
                makeSetTdLibParametersRequest(),
                null
            )
            is TdApi.AuthorizationStateWaitEncryptionKey -> {
                client.send(TdApi.SetDatabaseEncryptionKey(byteArrayOf()), null)
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