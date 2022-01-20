package com.jerryokafor.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jerryokafor.compose.ui.screens.Home
import com.jerryokafor.compose.ui.screens.Login
import com.jerryokafor.compose.ui.screens.SignUp
import com.jerryokafor.compose.ui.screens.Splash
import com.jerryokafor.compose.ui.theme.AppColors
import com.jerryokafor.compose.ui.theme.AppTheme
import com.jerryokafor.compose.ui.theme.darkColors
import com.jerryokafor.compose.ui.theme.lightColors
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                ComposeApp("home")
            }
        }
    }
}

enum class SplashState { Shown, Completed }

@Composable
fun ComposeApp(startDestination: String) {
    val navController = rememberAnimatedNavController()
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    val transitionState = remember { MutableTransitionState(SplashState.Shown) }
    val transition = updateTransition(transitionState, label = "splashTransition")

    val splashAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 100) }, label = "splashAlpha"
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

    AppTheme(colors = if (useDarkIcons) darkColors() else lightColors()) {
        Box {
            Splash(
                modifier = Modifier.alpha(splashAlpha),
                onTimeout = { transitionState.targetState = SplashState.Completed }
            )

            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
                    .alpha(contentAlpha)
            ) {

                Text(
                    "Blue",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),

                    color = Color.White, fontSize = 80.sp, textAlign = TextAlign.Center
                )
                NavigateButton(
                    "Login",
                    Modifier
                        .wrapContentWidth()
                        .then(Modifier.align(Alignment.CenterHorizontally))
                ) { navController.navigate("login") }
            }

            AnimatedNavHost(
                modifier = Modifier
                    .alpha(contentAlpha)
                    .padding(top = contentTopPadding),
                navController = navController,
                startDestination = startDestination,
                enterTransition = { fadeIn(animationSpec = tween(700)) },
                exitTransition = { fadeOut(animationSpec = tween(700)) },
            ) {
                composable("home") { Home(navController) }
                composable("login") { Login(navController) }
                composable("signUp") { SignUp(navController) }
            }
        }

    }
//    ExperimentalAnimationNav()
}


@Composable
fun ExperimentalAnimationNav() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController, startDestination = "Blue") {
        composable(
            "Blue",
            enterTransition = {
                when (route) {
                    "Red" -> slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                    else -> null
                }
            },
            exitTransition = {
                when (route) {
                    "Red" ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            popEnterTransition = {
                when (route) {
                    "Red" ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (route) {
                    "Red" ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            }
        ) { Home(navController) }

        composable(
            "Red",
            enterTransition = {
                when (route) {
                    "Blue" ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    "Green" ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Up,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            exitTransition = {
                when (route) {
                    "Blue" ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    "Green" ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Up,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            popEnterTransition = {
                when (route) {
                    "Blue" ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    "Green" ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Down,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (route) {
                    "Blue" ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    "Green" ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Down,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            }
        ) { Login(navController) }

        navigation(
            startDestination = "Green",
            route = "Inner",
            enterTransition = { expandIn(animationSpec = tween(700)) },
            exitTransition = { shrinkOut(animationSpec = tween(700)) }
        ) {
            composable(
                "Green",
                enterTransition = {
                    when (route) {
                        "Red" ->
                            slideIntoContainer(
                                AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700)
                            )
                        else -> null
                    }
                },
                exitTransition = {
                    when (route) {
                        "Red" ->
                            slideOutOfContainer(
                                AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700)
                            )
                        else -> null
                    }
                },
                popEnterTransition = {
                    when (route) {
                        "Red" ->
                            slideIntoContainer(
                                AnimatedContentScope.SlideDirection.Down, animationSpec = tween(700)
                            )
                        else -> null
                    }
                },
                popExitTransition = {
                    when (route) {
                        "Red" ->
                            slideOutOfContainer(
                                AnimatedContentScope.SlideDirection.Down, animationSpec = tween(700)
                            )
                        else -> null
                    }
                }
            ) { SignUp(navController) }
        }
    }
}


@Composable
fun NavigateButton(
    text: String,
    modifier: Modifier = Modifier,
    listener: () -> Unit = { }
) {
    Button(
        onClick = listener,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Composable
fun NavigateBackButton(navController: NavController) {
    // Use LocalLifecycleOwner.current as a proxy for the NavBackStackEntry
    // associated with this Composable
    if (navController.currentBackStackEntry == LocalLifecycleOwner.current &&
        navController.previousBackStackEntry != null
    ) {
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Go to Previous screen")
        }
    }
}