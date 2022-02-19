package com.jerryokafor.compose.ui.screens.dashboard.notification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.jerryokafor.compose.ui.screens.dashboard.AppBarConfiguration

/**
 * @Author <Author>
 * @Project <Project>
 */

@Composable
fun Notifications(onAppConfigurationChange: (AppBarConfiguration) -> Unit) {
    SideEffect {
        onAppConfigurationChange(AppBarConfiguration(title = "Notifications"))
    }

}