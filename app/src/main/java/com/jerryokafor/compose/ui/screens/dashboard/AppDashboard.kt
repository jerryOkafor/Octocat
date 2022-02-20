package com.jerryokafor.compose.ui.screens.dashboard

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.Insets
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.jerryokafor.compose.R
import com.jerryokafor.compose.ui.screens.dashboard.explore.Explore
import com.jerryokafor.compose.ui.screens.dashboard.home.Home
import com.jerryokafor.compose.ui.screens.dashboard.notification.Notifications
import com.jerryokafor.compose.ui.screens.dashboard.profile.Profile
import com.jerryokafor.compose.ui.screens.dashboard.profile.ProfileViewModel
import timber.log.Timber

/**
 * @Author Jerry Okafor
 * @Project ComposeTemplate
 * @Date 15/10/2021 17:44
 */

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.ic_home, "Home")
    object Notifications :
        NavigationItem("notifications", R.drawable.ic_notifications, "Notifications")

    object Explore : NavigationItem("explore", R.drawable.ic_explore, "Explore")
    object Profile : NavigationItem("profile", R.drawable.ic_profile, "Profile")
}

@ExperimentalAnimationApi
@Composable
fun AppDashboard(
    viewModel: HomeViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    LaunchedEffect(profileViewModel) {
        profileViewModel.onAction(ProfileViewModel.Action.GetUserProfile())
    }

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
    AppDashboardContent(
        navController = navController,
        profileViewModel = profileViewModel,
        appBarConfiguration = appBarConfig,
        onAppConfigurationChange = onAppConfigurationChange
    )


}

sealed class TopAppBarAction {
    object HomeScreeSearch : TopAppBarAction()
    object HomeScreeAdd : TopAppBarAction()
    object ProfileShare : TopAppBarAction()
    object ProfileSettings : TopAppBarAction()
}

@Composable
fun AppDashboardContent(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    appBarConfiguration: AppBarConfiguration,
    onAppConfigurationChange: (AppBarConfiguration) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.toString()

    //Todo: This might be passed down to the individual Screen depending on the requirements at a time
    val topAppBarAction = remember { mutableStateOf<TopAppBarAction?>(null) }

    val onHandleTopAppBarAction: (TopAppBarAction) -> Unit = {
        topAppBarAction.value = it
    }

    var previousOffset = 0
    val onScroll: (LazyListState) -> Unit = {
        if (it.firstVisibleItemIndex == 0) {
            val scrolledY = it.firstVisibleItemScrollOffset - previousOffset
//            val translationY = scrolledY * 0.5f
            previousOffset = it.firstVisibleItemScrollOffset
            Timber.d("Translation Y: $scrolledY")
        }
    }

    Scaffold(modifier = Modifier, backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopBar(
                appBarConfiguration = appBarConfiguration,
                currentRoute = currentRoute,
                onHandleTopAppBarAction = onHandleTopAppBarAction
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = currentRoute
            )
        }) { innerPadding ->
        Navigation(
            navController = navController,
            profileViewModel = profileViewModel,
            contentPadding = innerPadding,
            onAppConfigurationChange = onAppConfigurationChange,
            onLazyListScroll = onScroll
        )
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    contentPadding: PaddingValues,
    onAppConfigurationChange: (AppBarConfiguration) -> Unit,
    onLazyListScroll: (LazyListState) -> Unit
) {
    val enterTransition = fadeIn(animationSpec = tween(200))
    val exitTransition = fadeOut(animationSpec = tween(200))

    AnimatedNavHost(
        modifier = Modifier.padding(
            bottom = contentPadding.calculateBottomPadding(),
            top = contentPadding.calculateTopPadding(),
            end = contentPadding.calculateEndPadding(LayoutDirection.Ltr),
            start = contentPadding.calculateStartPadding(LayoutDirection.Ltr)
        ),
        navController = navController,
        startDestination = NavigationItem.Home.route,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
    ) {
        composable(NavigationItem.Home.route) {
            Home(navController = navController, onAppConfigurationChange = onAppConfigurationChange)
        }
        composable(NavigationItem.Notifications.route) {
            Notifications(onAppConfigurationChange = onAppConfigurationChange)
        }
        composable(NavigationItem.Explore.route) {
            Explore(onAppConfigurationChange = onAppConfigurationChange)
        }
        composable(NavigationItem.Profile.route) {
            Profile(
                onAppConfigurationChange = onAppConfigurationChange,
                viewModel = profileViewModel,
                onLazyListScroll = onLazyListScroll
            )
        }

    }
}

@Composable
fun TopBar(
    appBarConfiguration: AppBarConfiguration,
    currentRoute: String,
    onHandleTopAppBarAction: (TopAppBarAction) -> Unit,
) {
    val elevation = when (currentRoute) {
        NavigationItem.Home.route -> 0.dp
        else -> 2.dp
    }
    Timber.d("Current Route: $currentRoute")
    TopAppBar(
        elevation = elevation,
        title = {
            Crossfade(targetState = currentRoute, animationSpec = tween(100)) {
                when (it) {
                    NavigationItem.Profile.route -> Column {
                        Text(
                            text = appBarConfiguration.title,
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)
                        )
                        Text(
                            text = appBarConfiguration.subTitle,
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                        )
                    }
                    else -> {
                        Text(
                            text = appBarConfiguration.title,
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }

        },
        backgroundColor = MaterialTheme.colors.onPrimary,
        actions = {
            Crossfade(targetState = currentRoute) {
                when (it) {
                    NavigationItem.Home.route -> Row {
                        IconButton(onClick = { onHandleTopAppBarAction(TopAppBarAction.HomeScreeSearch) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                tint = MaterialTheme.colors.primary,
                                contentDescription = "Localized description"
                            )
                        }

                        IconButton(onClick = { onHandleTopAppBarAction(TopAppBarAction.HomeScreeAdd) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add),
                                tint = MaterialTheme.colors.primary,
                                contentDescription = "Localized description"
                            )
                        }
                    }

                    NavigationItem.Profile.route -> Row {
                        IconButton(onClick = { onHandleTopAppBarAction(TopAppBarAction.ProfileShare) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_share_new),
                                tint = MaterialTheme.colors.primary,
                                contentDescription = "Localized description"
                            )
                        }

                        IconButton(onClick = { onHandleTopAppBarAction(TopAppBarAction.ProfileSettings) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_settings_new),
                                tint = MaterialTheme.colors.primary,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                }

            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController, currentRoute: String) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Notifications,
        NavigationItem.Explore,
        NavigationItem.Profile
    )


    BottomNavigation(
        backgroundColor = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .zIndex(2f)
            .padding(
                rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.navigationBars.minus(
                        Insets.Insets(
                            left = 0,
                            top = 0,
                            right = 0,
                            bottom = 24
                        )
                    ),
                    applyStart = false,
                    applyEnd = false,
                    applyBottom = true
                )
            ),
        elevation = 10.dp

    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.h6.copy(fontSize = 12.sp)
                    )
                },
                unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                selectedContentColor = MaterialTheme.colors.primary,
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar(AppBarConfiguration("Welcome"), currentRoute = "") {}
}

@Preview(showBackground = false)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberAnimatedNavController()
    BottomNavigationBar(navController = navController, currentRoute = "home")
}

@Preview(showBackground = false)
@Composable
fun AppDashboardContentPreview() {
    val navController = rememberAnimatedNavController()
//    val profileViewModel: ProfileViewModel = hiltViewModel()
//    AppDashboardContent(
//        navController = navController,
//        profileViewModel = profileViewModel,
//        appBarConfiguration = AppBarConfiguration(title = "Welcome")
//    ) {}
}


data class AppBarConfiguration(
    val title: String = "",
    val subTitle: String = "",
    val navigationIcon: Icons? = null,
    val actions: List<Icons>? = emptyList()
)
