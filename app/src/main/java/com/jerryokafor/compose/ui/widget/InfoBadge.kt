package com.jerryokafor.compose.ui.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.jerryokafor.compose.R
import com.jerryokafor.compose.ui.theme.Spacing
import com.jerryokafor.compose.ui.theme.error
import com.jerryokafor.compose.ui.theme.seed

/**
 * @Author <Author>
 * @Project <Project>
 */

@Composable
fun InfoBadge(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    message: String,
    cancellable: Boolean = true,
    onCancel: () -> Unit = {}
) {

    val bgColor = (if (isError)
        error.copy(alpha = 0.7F)
    else
        seed.copy(alpha = 0.5F))
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20),
        color = bgColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.Dp10),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.9f),
                text = message,
                color = Color.White
            )

            AnimatedVisibility(visible = cancellable) {
                Image(
                    modifier = Modifier
                        .size(Spacing.Dp18, Spacing.Dp18)
                        .clickable { onCancel() },
                    painter = painterResource(id = R.drawable.ic_close),
                    colorFilter = ColorFilter.tint(color = Color.White),
                    contentDescription = "",

                    )
            }
        }
    }
}