package com.github.ntngel1.linkedout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import dagger.hilt.android.AndroidEntryPoint
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var appLauncher: AppLauncher

    private val navigator = makeNavigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            appLauncher.coldStart()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun makeNavigator(): Navigator = object : SupportAppNavigator(
        this,
        supportFragmentManager,
        R.id.framelayout_main_fragment_container
    ) {
        override fun setupFragmentTransaction(
            command: Command,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction
        ) {
            super.setupFragmentTransaction(
                command,
                currentFragment,
                nextFragment,
                fragmentTransaction
            )

            fragmentTransaction.setReorderingAllowed(true)
        }
    }
}
