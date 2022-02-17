package com.jerryokafor.compose.ui.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.jerryokafor.compose.ui.theme.Spacing.DP16
import com.jerryokafor.compose.ui.theme.Spacing.DP8

/**
 * @Author <Author>
 * @Project <Project>
 */
@Composable
fun ProfileContactItem(
    content: @Composable () -> Unit,
    @DrawableRes icon: Int,
    iconContentDescription: String = "Localized description",
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.wrapContentHeight(),
        onClick = onClick,
        shape = MaterialTheme.shapes.small.copy(CornerSize(20))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DP8, vertical = DP8),
            horizontalArrangement = Arrangement.spacedBy(DP16),
        ) {
            Icon(
                painter = painterResource(id = icon),
                tint = MaterialTheme.colors.primary,
                contentDescription = iconContentDescription
            )
            content()
        }
    }
}