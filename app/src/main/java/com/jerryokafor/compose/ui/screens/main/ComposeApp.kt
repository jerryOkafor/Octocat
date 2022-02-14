package com.jerryokafor.compose.ui.screens.main

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jerryokafor.compose.AuthState
import com.jerryokafor.compose.ui.screens.splash.Splash
import com.jerryokafor.compose.ui.screens.dashboard.AppDashboard
import com.jerryokafor.compose.ui.screens.auth.login.Login
import com.jerryokafor.compose.ui.theme.AppTheme
import timber.log.Timber

/**
 * @Author <Author>
 * @Project <Project>
 */

enum class SplashState { Shown, Completed }

@Composable
fun ComposeApp(
    viewModel: ComposeAppViewModel = hiltViewModel()
) {
    val authState by viewModel.authState.collectAsState(initial = AuthState.NO_AUTH)

    val navController = rememberAnimatedNavController()
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    val transitionState = remember { MutableTransitionState(SplashState.Shown) }
    val transition = updateTransition(transitionState, label = "splashTransition")

    val splashAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300) }, label = "splashAlpha"
    ) { if (it == SplashState.Shown) 1f else 0f }

    val contentAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300) }, label = "contentAlpha"
    ) {
        if (it == SplashState.Shown) 0f else 1f
    }

    val statusBarColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = 300) },
        label = "statusBarColor"
    ) {
        if (it == SplashState.Shown) Color.Transparent else Color.Transparent
    }

    val contentTopPadding by transition.animateDp(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) },
        label = "contentTopPadding"
    ) {
        if (it == SplashState.Shown) 0.dp else 50.dp
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
    }
    val enterTransition = fadeIn(animationSpec = tween(700))
    val exitTransition = fadeOut(animationSpec = tween(700))

    AppTheme {
        Box {
            Splash(
                modifier = Modifier.alpha(splashAlpha),
                onTimeout = { transitionState.targetState = SplashState.Completed }
            )

            Box(
                modifier = Modifier
                    .alpha(contentAlpha)
                    .padding(top = contentTopPadding)
            ) {
                Crossfade(targetState = authState) {
                    Timber.d("Auth: $it")
                    when (it) {
                        AuthState.AUTH -> AppDashboard()
                        AuthState.NO_AUTH -> {
                            Scaffold {
                                AnimatedNavHost(
                                    navController = navController,
                                    startDestination = "login",
                                    enterTransition = { enterTransition },
                                    exitTransition = { exitTransition },
                                ) {
                                    composable(
                                        "login",
                                        arguments = listOf(navArgument("code") {
                                            type = NavType.StringType
                                        },
                                            navArgument("state") {
                                                type = NavType.StringType
                                            }),
                                        deepLinks = listOf(navDeepLink {
                                            uriPattern =
                                                "repo-auth://callback?code={code}&state={state}"
                                        }
                                        )
                                    ) { backstackEntry ->
                                        Login(navController = navController)
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

