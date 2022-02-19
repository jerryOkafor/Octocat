package com.jerryokafor.compose.ui.screens.dashboard.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.jerryokafor.compose.ui.screens.dashboard.AppBarConfiguration

/**
 * @Author <Author>
 * @Project <Project>
 */

@Composable
fun Explore(onAppConfigurationChange: (AppBarConfiguration) -> Unit) {
    SideEffect {
        onAppConfigurationChange(AppBarConfiguration(title = "Explore"))
    }

}