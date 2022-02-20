package com.jerryokafor.compose.ui.screens.dashboard.profile

import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.jerryokafor.compose.R
import com.jerryokafor.compose.ui.compose.GithubLinkItem
import com.jerryokafor.compose.ui.compose.ProfileContactItem
import com.jerryokafor.compose.ui.screens.dashboard.AppBarConfiguration
import com.jerryokafor.compose.ui.theme.*
import com.jerryokafor.compose.ui.theme.FontSize.SP14
import com.jerryokafor.compose.ui.theme.FontSize.SP20
import com.jerryokafor.compose.ui.theme.Spacing.DP16
import com.jerryokafor.compose.ui.theme.Spacing.DP32
import com.jerryokafor.compose.ui.theme.Spacing.DP4
import com.jerryokafor.compose.ui.theme.Spacing.DP8
import com.jerryokafor.compose.ui.util.Util
import timber.log.Timber

/**
 * @Author <Author>
 * @Project <Project>
 */
@Composable
fun Profile(
    onAppConfigurationChange: (AppBarConfiguration) -> Unit,
    viewModel: ProfileViewModel,
    onLazyListScroll: (LazyListState) -> Unit
) {
    val state = viewModel.state.collectAsState()
    SideEffect {
        state.value.user?.let {
            onAppConfigurationChange(
                AppBarConfiguration(
                    title = it.login,
                    subTitle = it.name
                )
            )
        }
    }

    ProfileContent(state = state.value, onLazyListScroll = onLazyListScroll, onRefresh = {
        viewModel.onAction(ProfileViewModel.Action.GetUserProfile(force = true))
    })
}

@Composable
fun ProfileContent(
    state: ProfileUIState,
    onRefresh: () -> Unit = {},
    onLazyListScroll: (LazyListState) -> Unit,
) {
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    val swipeRefreshState by remember(state.loading) { mutableStateOf(SwipeRefreshState(state.loading)) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val deltaY = available.y
                return Offset.Zero
            }
        }
    }

    if (lazyListState.isScrollInProgress) {
        onLazyListScroll(lazyListState)
    }

    SwipeRefresh(
//        modifier = Modifier.testTag(SWIPE_TO_REFRESH),
        state = swipeRefreshState,
        onRefresh = onRefresh
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection),
            verticalArrangement = Arrangement.spacedBy(DP16),
            state = lazyListState
        ) {
            item {
                state.user?.let {
                    Card(elevation = 2.dp) {
                        Column(
                            modifier = Modifier.padding(horizontal = DP8, vertical = DP32),
                            verticalArrangement = Arrangement.spacedBy(DP16)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = DP8),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(DP16)
                            ) {
                                Avatar(
                                    modifier = Modifier.size(width = 90.dp, height = 90.dp),
                                    uri = it.avatarUrl
                                )

                                Column {
                                    Text(
                                        text = it.name,
                                        style = MaterialTheme.typography.h3.copy(
                                            fontSize = SP20,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = it.login,
                                        style = MaterialTheme.typography.body2.copy(
                                            fontSize = SP14,
                                            color = githubGraniteGray,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                            }

                            CodeSnippetContainer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = DP8)
                            ) {
                                Text(
                                    text = with(it.status) { "$emoji $message" },
                                    style = MaterialTheme.typography.body1
                                )
                            }

                            Text(
                                modifier = Modifier.padding(horizontal = DP4),
                                text = it.bio,
                                style = MaterialTheme.typography.caption
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(DP8)) {
                                ProfileContactItem(
                                    content = {
                                        Text(
                                            text = it.location,
                                            style = MaterialTheme.typography.caption
                                        )
                                    },
                                    icon = R.drawable.ic_location
                                )
                                val clickableStyle = MaterialTheme.typography.h3.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                ProfileContactItem(
                                    content = {
                                        Text(
                                            text = it.blog,
                                            style = clickableStyle
                                        )
                                    },
                                    icon = R.drawable.ic_link
                                ) {
                                    Util.openUri(context, it.blog.toUri())

                                }

                                ProfileContactItem(
                                    content = {
                                        Text(
                                            text = it.email,
                                            style = clickableStyle
                                        )
                                    },
                                    icon = R.drawable.ic_email
                                )

                                ProfileContactItem(
                                    content = {
                                        Text(
                                            text = it.twitterUsername,
                                            style = clickableStyle
                                        )
                                    },
                                    icon = R.drawable.ic_twitter
                                ) {
                                    Util.openUri(
                                        context,
                                        "https://twitter.com/${it.twitterUsername}".toUri()
                                    )
                                }

                                ProfileContactItem(
                                    content = {
                                        val followersTag = "followers"
                                        val followingTag = "following"
                                        val clickableText = buildAnnotatedString {

                                            pushStringAnnotation(
                                                tag = followersTag,
                                                annotation = "/followers"
                                            )
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = MaterialTheme.typography.body1.fontFamily,
                                                    fontSize = MaterialTheme.typography.body1.fontSize
                                                )
                                            ) {
                                                append("${it.followers}")
                                            }

                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Normal,
                                                    fontFamily = MaterialTheme.typography.body1.fontFamily,
                                                    fontSize = MaterialTheme.typography.body1.fontSize
                                                )
                                            ) {
                                                append(" followers ")
                                            }
                                            pop()

                                            pushStringAnnotation(
                                                tag = followingTag,
                                                annotation = "/following"
                                            )
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = MaterialTheme.typography.body1.fontFamily,
                                                    fontSize = MaterialTheme.typography.body1.fontSize
                                                )
                                            ) {
                                                append("· ${it.following}")
                                            }

                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Normal,
                                                    fontFamily = MaterialTheme.typography.body1.fontFamily,
                                                    fontSize = MaterialTheme.typography.body1.fontSize
                                                )
                                            ) {
                                                append(" following")
                                            }
                                            pop()
                                        }
                                        ClickableText(text = clickableText) {
                                            with(clickableText) {
                                                getStringAnnotations(
                                                    followersTag,
                                                    start = it,
                                                    end = it
                                                ).firstOrNull()?.let {
                                                    Timber.d("Selected: ${it.item}")
                                                }
                                                getStringAnnotations(
                                                    followingTag,
                                                    start = it,
                                                    end = it
                                                ).firstOrNull()?.let {
                                                    Timber.d("Selected: ${it.item}")
                                                }
                                            }
                                        }
                                    },
                                    icon = R.drawable.ic_user
                                )
                            }
                        }

                    }
                }
            }
            item {
                Card(elevation = 2.dp) {
                    Column(modifier = Modifier.padding(bottom = DP32)) {
                        Text(
                            modifier = Modifier.padding(DP16),
                            text = "${state.user?.login}/README.md"
                        )
                        Divider()
                        Text(
                            modifier = Modifier.padding(DP16),
                            text = """
                🔭 Passionate software engineer with a knack for delivering high quality, maintainable and scalable software product in time. Currently I work for @velatech and consulting for @mintfintech - building awesome products together with my team. I read, follow and write on all things tech especially mobile application development and Machine Learning.

                🌱 Currently learning Artificial Intelligence and Machine Learning, presently, I have completed a Deep Learning Nanodegree on Udacity - first step to quenching my thirst for a career in Machine Learning.

                👯 Open to collaboration on projects that explores the applications of Machine Learning to mobile devices
            """.trimIndent()
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    elevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(bottom = DP32),
                        verticalArrangement = Arrangement.spacedBy(DP16)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = DP16, horizontal = DP16),
                            horizontalArrangement = Arrangement.spacedBy(DP16)
                        ) {
                            Icon(
                                modifier = Modifier.rotate(-90F),
                                painter = painterResource(id = R.drawable.ic_pin),
                                tint = MaterialTheme.colors.primary,
                                contentDescription = "Localized description"
                            )
                            Text(
                                text = "Pinned",
                                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            )
                        }

                        val items = (0..5).map { "Hello" }

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(DP16)) {
                            item { Spacer(modifier = Modifier.width(DP8)) }
                            items(items) {
                                PinnedCard(modifier = Modifier.width(300.dp))
                            }
                            item { Spacer(modifier = Modifier.width(DP8)) }
                        }

                        Divider()
                        Column(modifier = Modifier.wrapContentHeight()) {
                            GithubLinkItem(
                                icon = R.drawable.ic_repository,
                                text = stringResource(R.string.title_repositories),
                                subText = "175",
                                iconBackground = githubRepoColor
                            )
                            GithubLinkItem(
                                icon = R.drawable.ic_organisation,
                                text = stringResource(R.string.title_organisations),
                                subText = "2",
                                iconBackground = githubOrgColor
                            )
                            GithubLinkItem(
                                icon = R.drawable.ic_star,
                                text = stringResource(R.string.title_starred),
                                subText = "180",
                                iconBackground = githubStarColor
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun CodeSnippetContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        modifier = modifier.wrapContentHeight(),
        color = githubLotion,
        border = BorderStroke(width = 1.dp, color = githubCultured),
        shape = RoundedCornerShape(10)
    ) {
        Box(modifier = Modifier.padding(horizontal = DP8, vertical = DP16)) {
            content()
        }
    }
}

@Composable
private fun Avatar(modifier: Modifier = Modifier, uri: String?) {
    Timber.d("Using: $uri")
    Surface(
        modifier = modifier,
        shape = CircleShape,
//        border = BorderStroke(width = 1.dp, MaterialTheme.colors.primary)
    ) {
        Box {
            val painter =
                rememberImagePainter(
                    data = uri,
                    builder = {
                        crossfade(true)
                        fadeIn()
                    }
                )
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            when (painter.state) {
                is ImagePainter.State.Loading -> CircularProgressIndicator(
                    Modifier.align(Alignment.Center),
                    color = MaterialTheme.colors.secondary,
                    strokeWidth = 1.5.dp
                )
                is ImagePainter.State.Error -> {
                    Timber.d("Error loading Image: $uri")
                }
                is ImagePainter.State.Success -> {
                    Timber.d("Image: $uri loaded successfully")
                }
                is ImagePainter.State.Empty -> {
                    Timber.d("Image: $uri empty")
                }
            }
        }
    }
}

@Composable
fun PinnedCard(modifier: Modifier = Modifier) {
    Surface(
        border = BorderStroke(width = 1.dp, color = md_theme_dark_onSurfaceVariant),
        shape = RoundedCornerShape(5)
    ) {

        Column(modifier = modifier.padding(DP16)) {
            Row(horizontalArrangement = Arrangement.spacedBy(DP16)) {
                Avatar(
                    modifier = Modifier.size(width = 24.dp, height = 24.dp),
                    uri = "https://via.placeholder.com/150"
                )
                Text(text = "jerryOkafor")
            }

            Text(
                text = "TimelineView",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
            Text(
                text = "A simple Timeline View that dem.......",
                style = TextStyle(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(DP32))
            Row(
                horizontalArrangement = Arrangement.spacedBy(DP8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(width = 16.dp, height = 16.dp),
                    painter = painterResource(id = R.drawable.ic_star),
                    tint = MaterialTheme.colors.primary,
                    contentDescription = "Localized description"
                )
                Text(text = "355")

                Icon(
                    modifier = Modifier
                        .size(width = 16.dp, height = 16.dp)
                        .scale(2.5f),
                    painter = painterResource(id = R.drawable.ic_dot),
                    tint = MaterialTheme.colors.primary,
                    contentDescription = "Localized description"
                )
                Text(text = "Kotlin")
            }
        }

    }

}


@Composable
@Preview
fun ProfileContentPreview() {
    val state = ProfileUIState()
    ProfileContent(state = state, onLazyListScroll = {})
}

@Composable
@Preview
fun PinnedCardPreview() {
    PinnedCard(
        modifier = Modifier
            .wrapContentHeight()
            .width(250.dp)
    )
}