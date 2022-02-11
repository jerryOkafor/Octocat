package com.jerryokafor.compose.ui.widget

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jerryokafor.compose.ui.theme.DefaultButtonHeight
import com.jerryokafor.compose.ui.util.ComposeTestTag

/**
 * @Author <Author>
 * @Project <Project>
 */

@Composable
fun OutlineProgressButton(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    onClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = null,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = ButtonDefaults.outlinedBorder,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {


    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        val (progress, button) = createRefs()

        Crossfade(targetState = loading, animationSpec = tween(400)) {
            if (it) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(DefaultButtonHeight)
                        .constrainAs(progress) {
                            centerHorizontallyTo(parent)
                        }) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag(ComposeTestTag.PROGRESS_INDICATOR),
                        color = MaterialTheme.colors.secondary,
                        strokeWidth = Dp(value = 2F)
                    )
                }

            } else {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(DefaultButtonHeight)
                        .constrainAs(button) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                        }
                        .testTag(ComposeTestTag.BTN_LOGIN),
                    onClick = onClick,
                    enabled = enabled,
                    interactionSource = interactionSource,
                    elevation = elevation,
                    shape = shape,
                    border = border,
                    colors = colors,
                    contentPadding = contentPadding,
                    content = content
                )
            }
        }

    }
}

@Composable
@Preview
fun OutlineProgressButtonPreview() {
    Column {
        OutlineProgressButton(loading = true, onClick = {}) {
            Text(text = "Click me")
        }
    }
}