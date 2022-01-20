package com.jerryokafor.compose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jerryokafor.compose.R
import com.jerryokafor.compose.ui.theme.AppTheme
import com.jerryokafor.compose.ui.theme.colorPrimary
import com.jerryokafor.compose.ui.theme.darkColors
import com.jerryokafor.compose.ui.theme.lightColors
import kotlinx.coroutines.delay

/**
 * @Author Jerry Okafor
 * @Project ComposeTemplate
 * @Date 19/10/2021 09:55
 */

private const val SplashWaitTime: Long = 1000

@Composable
fun Splash(modifier: Modifier = Modifier, onTimeout: () -> Unit) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) darkColors().primary else lightColors().primary)
    ) {
        // Adds composition consistency. Use the value when LaunchedEffect is first called
        val currentOnTimeout by rememberUpdatedState(onTimeout)

        LaunchedEffect(Unit) {
            delay(SplashWaitTime)
            currentOnTimeout()
        }

        Image(
            painterResource(id = R.drawable.logo),
            contentDescription = null,
            Modifier
                .size(250.dp, 100.dp)
                .align(Alignment.Center)
        )

//        Image(
//            painterResource(id = R.drawable.motto),
//            contentDescription = null,
//            Modifier
//                .scale(0.4f)
//                .align(Alignment.BottomCenter)
//                .padding(bottom = 16.dp)
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        Splash {}
    }
}