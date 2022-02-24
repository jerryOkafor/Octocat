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
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.jerryokafor.compose.domain.model.Owner
import com.jerryokafor.compose.domain.model.PinnedItem
import com.jerryokafor.compose.ui.compose.GithubLinkItem
import com.jerryokafor.compose.ui.compose.HtmlText
import com.jerryokafor.compose.ui.compose.ProfileContactItem
import com.jerryokafor.compose.ui.screens.dashboard.AppBarConfiguration
import com.jerryokafor.compose.ui.theme.*
import com.jerryokafor.compose.ui.theme.FontSize.SP14
import com.jerryokafor.compose.ui.theme.FontSize.SP16
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
                                Row(horizontalArrangement = Arrangement.spacedBy(DP16)) {
                                    HtmlText(
                                        text = it.status.emojiHTML.toString(),
                                        maxLines = 1,
                                        style = MaterialTheme.typography.body1
                                    )

                                    Text(
                                        text = it.status.message.toString(),
                                        style = MaterialTheme.typography.body1
                                    )
                                }

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
                            text = "${state.user?.login}/README.md",
                            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Light)
                        )
                        Divider()
                        HtmlText(
                            modifier = Modifier.padding(DP16),
                            text = state.user?.specialRepo?.readme.toString(),
                            style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Light)
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
                        modifier = Modifier.padding(vertical = DP16),
                        verticalArrangement = Arrangement.spacedBy(DP16)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = DP16),
                            horizontalArrangement = Arrangement.spacedBy(DP16)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .rotate(-90F)
                                    .size(width = 16.dp, height = 16.dp),
                                painter = painterResource(id = R.drawable.ic_pin),
                                tint = githubGraniteGray,
                                contentDescription = "Pinned Item"
                            )
                            Text(
                                text = "Pinned",
                                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            )
                        }

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(DP16)) {
                            item { Spacer(modifier = Modifier.width(DP8)) }

                            state.user?.pinnedItems?.let {
                                items(it) { item ->
                                    PinnedCard(
                                        modifier = Modifier
                                            .width(300.dp).height(160.dp),
                                        pinnedItem = item
                                    )
                                }
                            }
                            item { Spacer(modifier = Modifier.width(DP8)) }
                        }

                        Spacer(modifier = Modifier.height(DP8))
                        Divider()
                        Column(modifier = Modifier.wrapContentHeight()) {
                            GithubLinkItem(
                                icon = R.drawable.ic_repository,
                                text = stringResource(R.string.title_repositories),
                                subText = (state.user?.repositories ?: 0).toString(),
                                iconBackground = githubRepoColor
                            )
                            GithubLinkItem(
                                icon = R.drawable.ic_organisation,
                                text = stringResource(R.string.title_organisations),
                                subText = (state.user?.organizations ?: 0).toString(),
                                iconBackground = githubOrgColor
                            )
                            GithubLinkItem(
                                icon = R.drawable.ic_star,
                                text = stringResource(R.string.title_starred),
                                subText = (state.user?.starredRepositories ?: 0).toString(),
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
fun PinnedCard(modifier: Modifier = Modifier, pinnedItem: PinnedItem, onClick: () -> Unit = {}) {
    Surface(
        border = BorderStroke(width = 1.dp, color = md_theme_dark_onSurfaceVariant),
        shape = RoundedCornerShape(5),
        onClick = onClick
    ) {

        Column(modifier = modifier.padding(DP16)) {
            Row(horizontalArrangement = Arrangement.spacedBy(DP16)) {
                Avatar(
                    modifier = Modifier.size(width = 25.dp, height = 25.dp),
                    uri = pinnedItem.owner.avatarUrl
                )
                Text(
                    text = pinnedItem.owner.login, style = MaterialTheme.typography.body2.copy(
                        fontSize = SP16,
                        color = githubGraniteGray,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Text(
                text = pinnedItem.name,
                style = MaterialTheme.typography.caption.copy(
                    fontSize = SP16,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = pinnedItem.description,
                style = MaterialTheme.typography.body2.copy(
                    fontSize = SP14,
                    fontWeight = FontWeight.Normal
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(DP16))
            Row(
                horizontalArrangement = Arrangement.spacedBy(DP8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(width = 20.dp, height = 20.dp)
                        .scale(1.3F),
                    painter = painterResource(id = R.drawable.ic_star),
                    tint = githubStarColor,
                    contentDescription = "Pinned Item Star"
                )
                Text(
                    text = pinnedItem.stargazers.toString(),
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = SP14,
                        fontWeight = FontWeight.Normal
                    ),
                )

                Surface(
                    modifier = Modifier
                        .size(width = 16.dp, height = 16.dp),
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(50)
                ) {}
                Text(
                    text = pinnedItem.primaryLanguage.toString(),
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = SP14,
                        fontWeight = FontWeight.Normal
                    ),
                )
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
    val pinnedItem =
        PinnedItem(
            id = "",
            name = "TimelineView",
            description = "A simple Timeline View that dem.......",
            stargazers = 33,
            primaryLanguage = "Kotlin",
            owner = Owner(login = "jerryOkafor", avatarUrl = "https://via.placeholder.com/150")
        )
    PinnedCard(
        modifier = Modifier
            .wrapContentHeight()
            .width(250.dp),
        pinnedItem = pinnedItem
    )
}