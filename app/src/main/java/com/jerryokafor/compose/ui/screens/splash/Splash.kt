package com.jerryokafor.compose.ui.screens.splash

import androidx.compose.foundation.Image
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
        modifier = modifier.fillMaxSize()
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
                .size(100.dp, 100.dp)
                .align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        Splash {}
    }
}