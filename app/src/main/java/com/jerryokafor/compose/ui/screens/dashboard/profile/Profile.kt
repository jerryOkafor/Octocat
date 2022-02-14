package com.jerryokafor.compose.ui.screens.dashboard.profile

import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.navigationBarsPadding
import com.jerryokafor.compose.R
import com.jerryokafor.compose.ui.screens.dashboard.AppBarConfiguration
import com.jerryokafor.compose.ui.theme.*
import com.jerryokafor.compose.ui.theme.FontSize.SP16
import com.jerryokafor.compose.ui.theme.FontSize.SP28
import com.jerryokafor.compose.ui.theme.Spacing.DP16
import com.jerryokafor.compose.ui.theme.Spacing.DP32
import com.jerryokafor.compose.ui.theme.Spacing.DP8
import timber.log.Timber

/**
 * @Author <Author>
 * @Project <Project>
 */
@Composable
fun Profile(
    onAppConfigurationChange: (AppBarConfiguration) -> Unit,
    onLazyListScroll: (LazyListState) -> Unit
) {
    SideEffect {
        onAppConfigurationChange(
            AppBarConfiguration(
                title = "jerryOkafor",
                subTitle = "Jerry Hanks Okafor"
            )
        )
    }

    ProfileContent(onLazyListScroll = onLazyListScroll)
}

@Composable
fun ProfileContent(onLazyListScroll: (LazyListState) -> Unit) {
    val lazyListState = rememberLazyListState()
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
            .background(color = md_theme_dark_onSurfaceVariant),
        verticalArrangement = Arrangement.spacedBy(DP32),
        state = lazyListState
    ) {
        item {
            Card(elevation = 2.dp) {
                Column(
                    modifier = Modifier.padding(horizontal = DP16, vertical = DP32),
                    verticalArrangement = Arrangement.spacedBy(DP16)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(DP16)
                    ) {
                        Avatar(uri = "https://via.placeholder.com/150")
                        Column {
                            Text(text = "Jerry Hanks Okafor", style = TextStyle(fontSize = SP28))
                            Text(text = "jerryOkafor")
                        }
                    }

                    CodeSnippetContainer(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Saving the world")
                    }

                    Text(text = "Passionate Mobile (Android & iOS) Software Engineer | Machine Learning")

                    Row(horizontalArrangement = Arrangement.spacedBy(DP16)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location),
                            tint = MaterialTheme.colors.primary,
                            contentDescription = "Localized description"
                        )
                        Text(text = "Lagos")
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(DP16)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_link),
                            tint = MaterialTheme.colors.primary,
                            contentDescription = "Localized description"
                        )
                        Text(text = "jerryhanksokafor.com")
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(DP16)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_email),
                            tint = MaterialTheme.colors.primary,
                            contentDescription = "Localized description"
                        )
                        Text(text = "jerryhanksokafor@gmail.com")
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(DP16)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_twitter),
                            tint = MaterialTheme.colors.primary,
                            contentDescription = "Localized description"
                        )
                        Text(text = "@Noms0")
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(DP16)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_user),
                            tint = MaterialTheme.colors.primary,
                            contentDescription = "Localized description"
                        )
                        Text(text = "48 followers · 32 following")
                    }
                }

            }
        }
        item {
            Surface {
                Column(modifier = Modifier.padding(bottom = DP32)) {
                    Text(modifier = Modifier.padding(DP16), text = "jerryOkafor/README.md")
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
            Surface {
                Column(modifier = Modifier.padding(bottom = DP32)) {
                    Text(modifier = Modifier.padding(DP16), text = "jerryOkafor/README.md")
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
    }
}

@Composable
fun CodeSnippetContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        modifier = modifier.wrapContentHeight(),
        color = md_theme_dark_onSurfaceVariant,
        shape = RoundedCornerShape(10)
    ) {
        Box(modifier = Modifier.padding(DP8)) {
            content()
        }
    }
}

@Composable
private fun Avatar(uri: String) {
    Surface(
        Modifier.size(width = 95.dp, height = 95.dp),
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
                    color = Color.LightGray,
                    strokeWidth = 1.5.dp
                )
                is ImagePainter.State.Error -> {
                }
                is ImagePainter.State.Success -> {
                }
                is ImagePainter.State.Empty -> {
                }
            }
        }
    }
}

@Composable
@Preview
fun ProfileContentPreview() {
    ProfileContent() {}
}