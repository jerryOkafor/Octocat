package com.jerryokafor.compose.ui.screens.dashboard.home

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import com.jerryokafor.compose.ui.screens.dashboard.AppBarConfiguration
import com.jerryokafor.compose.ui.screens.dashboard.NavigationItem

/**
 * @Author Jerry Okafor
 * @Project ComposeTemplate
 * @Date 15/10/2021 17:45
 */

@ExperimentalAnimationApi
@Composable
fun AnimatedVisibilityScope.Home(
    navController: NavHostController,
    onAppConfigurationChange: (AppBarConfiguration) -> Unit
) {
    SideEffect { onAppConfigurationChange(AppBarConfiguration(title = "Home")) }
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(Dp(25f)))
        val count by remember { mutableStateOf(1) }
        Box {
            AnimatedContent(modifier = Modifier.align(Alignment.Center),
                targetState = count,
                transitionSpec = {
                    // Compare the incoming number with the previous number.
                    if (targetState > initialState) {
                        // If the target number is larger, it slides up and fades in
                        // while the initial (smaller) number slides up and fades out.
                        slideInVertically { height -> height } + fadeIn() with
                                slideOutVertically { height -> -height } + fadeOut()
                    } else {
                        // If the target number is smaller, it slides down and fades in
                        // while the initial number slides down and fades out.
                        slideInVertically { height -> -height } + fadeIn() with
                                slideOutVertically { height -> height } + fadeOut()
                    }.using(
                        // Disable clipping since the faded slide-in/out should
                        // be displayed out of bounds.
                        SizeTransform(clip = false)
                    )
                }
            ) { targetCount ->
                Text(text = "Here: $targetCount")
            }

        }
    }
}

@Composable
fun Notifications(onAppConfigurationChange: (AppBarConfiguration) -> Unit) {
    SideEffect {
        onAppConfigurationChange(AppBarConfiguration(title = "Notifications"))
    }

}

@Composable
fun Explore(onAppConfigurationChange: (AppBarConfiguration) -> Unit) {
    SideEffect {
        onAppConfigurationChange(AppBarConfiguration(title = "Explore"))
    }

}
