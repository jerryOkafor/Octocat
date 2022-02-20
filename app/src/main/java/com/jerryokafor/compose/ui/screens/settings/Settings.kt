package com.jerryokafor.compose.ui.screens.settings

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.jerryokafor.compose.BuildConfig
import com.jerryokafor.compose.ui.screens.dashboard.AppBarConfiguration
import com.jerryokafor.compose.ui.theme.Spacing.DP10
import com.jerryokafor.compose.ui.theme.Spacing.DP16
import com.jerryokafor.compose.ui.theme.Spacing.DP8

/**
 * @Author <Author>
 * @Project <Project>
 */

@Composable
fun Settings(rootNavController: NavHostController, viewModel: SettingsViewModel = hiltViewModel()) {
    SettingsContent(rootNavController = rootNavController, viewModel = viewModel)
}

@Composable
fun SettingsContent(rootNavController: NavHostController, viewModel: SettingsViewModel) {
    val navController = rememberAnimatedNavController()
    val (appBarConfig, setAppBarConfig) = remember { mutableStateOf(AppBarConfiguration()) }
    val onAppConfigurationChange: (AppBarConfiguration) -> Unit = {
        setAppBarConfig(
            appBarConfig.copy(
                title = it.title,
                subTitle = it.subTitle,
                navigationIcon = it.navigationIcon,
                actions = it.actions
            )
        )
    }

    SideEffect {
        setAppBarConfig(appBarConfig.copy(title = "Settings"))
    }

    Scaffold(modifier = Modifier, backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(
                elevation = 2.dp,
                backgroundColor = MaterialTheme.colors.onPrimary,
                title = {
                    Text(
                        text = appBarConfig.title,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    )
                },
                navigationIcon = {
                    Surface(
                        modifier = Modifier.padding(DP8),
                        onClick = { rootNavController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            )
        }) { innerPadding ->
        val enterTransition = fadeIn(animationSpec = tween(200))
        val exitTransition = fadeOut(animationSpec = tween(200))

        AnimatedNavHost(
            modifier = Modifier.padding(
                bottom = innerPadding.calculateBottomPadding(),
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr)
            ),
            navController = navController,
            startDestination = "settings/home",
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
        ) {
            composable("settings/home") {
                SettingHome() {
                    when (it) {
                        SettingsActions.SignOut -> {
                            //Sign the user out here
                            viewModel.onAction(SettingsViewModel.Action.SignOut)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingHome(onSettingClick: (SettingsActions) -> Unit = {}) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(DP16)) {
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        modifier = Modifier.padding(vertical = DP8, horizontal = DP16),
                        text = "Notifications",
                        style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.height(DP8))
                    notifications.forEach {
                        SettingItem(item = it) { it.action?.let { it1 -> onSettingClick(it1) } }
                    }
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        modifier = Modifier.padding(vertical = DP8, horizontal = DP16),
                        text = "General",
                        style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.height(DP8))
                    general.forEach {
                        SettingItem(item = it) { it.action?.let { it1 -> onSettingClick(it1) } }
                    }
                }
            }
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column {
                    others.forEach {
                        SettingItem(item = it) { it.action?.let { it1 -> onSettingClick(it1) } }
                    }

                    Spacer(modifier = Modifier.height(DP16))
                    Text(
                        modifier = Modifier
                            .padding(vertical = DP16, horizontal = DP16)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Octocat v${BuildConfig.VERSION_NAME}",
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }

}

data class Setting(
    val title: String,
    val subTitle: String? = null,
    val action: SettingsActions? = null
)

val notifications = listOf(
    Setting(title = "Configure Notification"),
)

val general = listOf(
    Setting(title = "Theme", subTitle = "Follow system"),
    Setting(title = "Code Options"),
    Setting(title = "Language", subTitle = "Follow system"),
    Setting(title = "Accounts")
)

val others = listOf(
    Setting(title = "More Options"),
    Setting(title = "Share Feedback"),
    Setting(title = "Terms of Service"),
    Setting(title = "Privacy Policy"),
    Setting(title = "Open Source Libraries"),
    Setting(title = "Sign Out", action = SettingsActions.SignOut)
)

@Composable
fun SettingItem(item: Setting, onClick: () -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Column(
            modifier = Modifier.padding(vertical = DP10, horizontal = DP16),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = item.title, style = MaterialTheme.typography.caption)
            item.subTitle?.let {
                Text(
                    text = item.subTitle,
                    style = MaterialTheme.typography.caption.copy(fontSize = 11.sp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingItemPreview() {
    val setting = Setting("Theme", "Follow System")
    SettingItem(setting) {}
}