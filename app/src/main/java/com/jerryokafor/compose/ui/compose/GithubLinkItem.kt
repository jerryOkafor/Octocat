package com.jerryokafor.compose.ui.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jerryokafor.compose.R
import com.jerryokafor.compose.ui.theme.Spacing
import com.jerryokafor.compose.ui.theme.Spacing.DP10
import com.jerryokafor.compose.ui.theme.Spacing.DP4
import com.jerryokafor.compose.ui.theme.Spacing.DP8
import com.jerryokafor.compose.ui.theme.githubBlack

/**
 * @Author <Author>
 * @Project <Project>
 */


@Composable
fun GithubLinkItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    text: String,
    subText: String = "",
    iconBackground: Color,
    contentDescription: String = "Localized description",
    onClick: () -> Unit = {}
) {
    Surface(modifier = Modifier
        .height(55.dp)
        .fillMaxWidth(), onClick = onClick) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.DP16, vertical = DP8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.DP16)
            ) {
                Surface(
                    modifier = Modifier
                        .size(40.dp, 40.dp),
                    color = iconBackground,
                    shape = RoundedCornerShape(10)
                ) {
                    Icon(
                        modifier = Modifier.padding(DP8),
                        painter = painterResource(id = icon),
                        tint = Color.White,
                        contentDescription = contentDescription
                    )
                }
                Text(text = text, style = TextStyle(fontSize = 18.sp))
            }
            Text(modifier = Modifier.padding(DP8), text = subText)
        }
    }
}

@Composable
@Preview
fun GithubLinkItemPreview() {
    GithubLinkItem(
        text = "Repositories",
        icon = R.drawable.ic_star,
        subText = "2",
        iconBackground = githubBlack
    )
}