package com.jerryokafor.compose.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jerryokafor.compose.NavigateBackButton
import com.jerryokafor.compose.NavigateButton

/**
 * @Author Jerry Okafor
 * @Project ComposeTemplate
 * @Date 15/10/2021 17:45
 */

@ExperimentalAnimationApi
@Composable
fun AnimatedVisibilityScope.SignUp(navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
        Spacer(Modifier.height(Dp(25f)))
        NavigateButton(
            "Navigate to Red",
            Modifier
                .wrapContentWidth()
                .then(Modifier.align(Alignment.CenterHorizontally))
        ) { navController.navigate("Red") }
        Text(
            "Green",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .animateEnterExit(
                    enter = fadeIn(animationSpec = tween(250, delayMillis = 450)),
                    exit = ExitTransition.None
                ),
            color = Color.White, fontSize = 80.sp, textAlign = TextAlign.Center
        )
        NavigateBackButton(navController)
    }
}