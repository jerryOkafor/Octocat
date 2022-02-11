package com.jerryokafor.compose.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jerryokafor.compose.ui.screens.home.HomeViewModel
import com.jerryokafor.compose.ui.theme.Spacing

/**
 * @Author Jerry Okafor
 * @Project ComposeTemplate
 * @Date 15/10/2021 17:44
 */

@ExperimentalAnimationApi
@Composable
fun AnimatedVisibilityScope.Home(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Blue)
            .padding(Spacing.Dp32)
    ) {

        Text(
            "Blue",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .animateEnterExit(
                    enter = fadeIn(animationSpec = tween(250, delayMillis = 450)),
                    exit = ExitTransition.None
                ),
            color = Color.White, fontSize = 80.sp, textAlign = TextAlign.Center
        )

        OutlinedButton(onClick = { viewModel.logout() }) {
            Text(text = "Logout")
        }

    }
}
