package com.jerryokafor.compose

import android.os.Bundle
import android.transition.Fade
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.jerryokafor.compose.ui.screens.main.ComposeApp
import com.jerryokafor.compose.ui.screens.main.ComposeAppViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

enum class AuthState {
    NO_AUTH, AUTH
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                ComposeApp()
            }
        }
    }
}