package com.jerryokafor.compose.ui.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jerryokafor.compose.R
import com.jerryokafor.compose.ui.theme.Spacing.DP16
import com.jerryokafor.compose.ui.theme.Spacing.DP4
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
        modifier = Modifier.height(32.dp),
        onClick = onClick,
        shape = RoundedCornerShape(CornerSize(15))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = DP8, vertical = DP4),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(DP16),
        ) {
            Icon(
                modifier = Modifier.size(24.dp, 24.dp),
                painter = painterResource(id = icon),
                tint = MaterialTheme.colors.primary,
                contentDescription = iconContentDescription
            )
            content()
        }
    }
}

@Composable
@Preview
fun ProfileContactItemPreview() {
    ProfileContactItem(content = { Text(text = "jerryokafor.com") }, icon = R.drawable.ic_link) {}
}