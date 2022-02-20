package com.jerryokafor.compose.ui.screens.dashboard.home

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jerryokafor.compose.R
import com.jerryokafor.compose.ui.compose.GithubLinkItem
import com.jerryokafor.compose.ui.screens.dashboard.AppBarConfiguration
import com.jerryokafor.compose.ui.screens.dashboard.NavigationItem
import com.jerryokafor.compose.ui.screens.dashboard.profile.PinnedCard
import com.jerryokafor.compose.ui.theme.*
import com.jerryokafor.compose.ui.theme.Spacing.DP16
import com.jerryokafor.compose.ui.theme.Spacing.DP8

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
    HomeContent()
}


@Composable
fun HomeContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(DP16),
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                elevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(bottom = Spacing.DP16, top = DP16),
                    verticalArrangement = Arrangement.spacedBy(Spacing.DP16)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = DP16)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "My Work")
                        Surface(
                            onClick = {},
                            modifier = Modifier.wrapContentSize(),
                            shape = RoundedCornerShape(50)
                        ) {
                            Icon(
                                modifier = Modifier.padding(Spacing.DP8),
                                painter = painterResource(id = R.drawable.ic_more_horizontal),
                                tint = MaterialTheme.colors.secondary,
                                contentDescription = "Local Content Description"
                            )
                        }
                    }

                    Column {
                        GithubLinkItem(
                            icon = R.drawable.ic_issues,
                            text = stringResource(R.string.title_issues),
                            iconBackground = githubIssuesColor
                        )
                        GithubLinkItem(
                            icon = R.drawable.ic_pull_request,
                            text = stringResource(R.string.title_pull_requests),
                            iconBackground = githubPullRequestColor
                        )
                        GithubLinkItem(
                            icon = R.drawable.ic_dicussion,
                            text = stringResource(R.string.title_discussions),
                            iconBackground = githubDiscussionsColor
                        )
                        GithubLinkItem(
                            icon = R.drawable.ic_repository,
                            text = stringResource(R.string.title_repositories),
                            iconBackground = githubRepoColor
                        )
                        GithubLinkItem(
                            icon = R.drawable.ic_organisation,
                            text = stringResource(R.string.title_organisations),
                            iconBackground = githubOrgColor
                        )

                    }

                    Spacer(modifier = Modifier.height(DP8))
                    Divider(modifier = Modifier.padding(horizontal = DP16))
//                    Spacer(modifier = Modifier.height(DP8))
                    Column(
                        modifier = Modifier.padding(horizontal = DP16),
                        verticalArrangement = Arrangement.spacedBy(DP16)
                    ) {
                        Text(text = "Favourites")
                        Spacer(modifier = Modifier.height(DP16))
                        Text(
                            text = "Add favorite repository for quick access at any time, without having to search",
                            style = TextStyle(fontWeight = FontWeight.Light),
                            textAlign = TextAlign.Center
                        )

                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                            onClick = { /*TODO*/ },
                            border = BorderStroke(
                                width = 2.dp,
                                color = MaterialTheme.colors.background
                            )
                        ) {
                            Text(text = "ADD FAVOURITE")
                        }
                    }

                    Spacer(modifier = Modifier.height(DP8))
                    Divider(modifier = Modifier.padding(horizontal = DP16))
//                    Spacer(modifier = Modifier.height(DP8))
                    Column(
                        modifier = Modifier.padding(horizontal = DP16),
                        verticalArrangement = Arrangement.spacedBy(DP16)
                    ) {
                        Text(text = "Shortcuts")
                        Spacer(modifier = Modifier.height(DP16))
                        Text(text = "The things your need, one tap away")
                        Text(
                            text = "Fast access your list of issues, Pull Requests, or Discussions",
                            style = TextStyle(fontWeight = FontWeight.Light),
                            textAlign = TextAlign.Center
                        )

                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                            onClick = { /*TODO*/ },
                            border = BorderStroke(
                                width = 2.dp,
                                color = MaterialTheme.colors.background
                            )
                        ) {
                            Text(text = "GET STARTED")
                        }
                    }

                    Spacer(modifier = Modifier.height(DP8))
                    Divider(modifier = Modifier.padding(horizontal = DP16))
//                    Spacer(modifier = Modifier.height(DP8))
                    Column(
                        modifier = Modifier.padding(horizontal = DP16),
                        verticalArrangement = Arrangement.spacedBy(DP16)
                    ) {
                        Text(text = "Recent")
                        Spacer(modifier = Modifier.height(DP16))
                        Text(text = "The things your need, one tap away")
                    }
                }

            }
        }
    }
}
