package com.jerryokafor.compose.ui.screens.auth.login

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jerryokafor.compose.OAuthActivity
import com.jerryokafor.compose.R
import com.jerryokafor.compose.ui.screens.state.UIInfo
import com.jerryokafor.compose.ui.theme.FontSize
import com.jerryokafor.compose.ui.theme.Spacing
import com.jerryokafor.compose.ui.theme.githubBlack
import com.jerryokafor.compose.ui.util.Util
import com.jerryokafor.compose.ui.widget.InfoBadge
import com.jerryokafor.compose.ui.widget.OutlineProgressButton
import timber.log.Timber
import java.util.*

/**
 * @Author Jerry Okafor
 * @Project ComposeTemplate
 * @Date 15/10/2021 17:44
 */


@Composable
fun AnimatedVisibilityScope.Login(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val oAuthState = UUID.randomUUID().toString()

    val oAuthLoginLauncher = rememberLauncherForActivityResult(
        contract = OAuthActivity.ResultContract()
    ) {
        viewModel.onAction(
            LoginViewModel.Action.ExchangeCodeForAccessToken(
                oAuthState = oAuthState,
                oAuthResponse = it
            )
        )
    }


    val onContinueBtnClick: () -> Unit = {
        oAuthLoginLauncher.launch(oAuthState)
    }

    LoginScreenContent(
        state = state,
        onContinueBtnClick = onContinueBtnClick
    ) {
        Util.openUri(context, it)
    }
}


@Composable
fun LoginScreenContent(
    state: LoginUIState,
    onContinueBtnClick: () -> Unit,
    onOpenUri: (url: Uri) -> Unit = {}
) {
    val colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.White,
        unfocusedBorderColor = Color.Gray,
        cursorColor = Color.White,
        focusedLabelColor = Color.White
    )
    val textStyle = TextStyle(
        color = Color.White,
        fontSize = FontSize.SP14,
        fontWeight = FontWeight.Normal
    )

    val (showBadge, setShowBadge) = remember(state.info?.message, state.info?.isError) {
        mutableStateOf(state.info?.message != null)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.DP16)
    ) {

        val (logo, button, text, info) = createRefs()

        AnimatedVisibility(modifier = Modifier.constrainAs(info) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top, margin = Spacing.DP24)
        }, visible = showBadge) {
            InfoBadge(
                isError = state.info?.isError ?: false,
                message = state.info?.message ?: ""
            ) { setShowBadge(false) }
        }

        Image(
            painterResource(id = R.drawable.logo),
            contentDescription = null,
            Modifier
                .size(100.dp, 100.dp)
                .constrainAs(logo) {
                    centerTo(parent)
                }
        )

        OutlineProgressButton(modifier = Modifier
            .constrainAs(button) {
                bottom.linkTo(text.top, margin = Spacing.DP16)
                end to parent.end
                start to parent.start
            }
            .wrapContentHeight(),
            onClick = onContinueBtnClick,
            loading = state.loading,
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = githubBlack,
                contentColor = Color.White
            )) {
            Text(text = stringResource(R.string.sign_with_github))
        }

        val linkStyle = SpanStyle(
            color = Color(0xff64B5F6),
            textDecoration = TextDecoration.Underline
        )

        val tosfUrlTag = "tofsURL"
        val psUrlTag = "psURL"
        val termsOfUseAndPrivacyPolicy = buildAnnotatedString {
            append("By signing in, you accept our ")
            pushStringAnnotation(
                tag = tosfUrlTag,
                annotation = stringResource(R.string.github_terms_of_use_url)
            )
            withStyle(style = linkStyle) { append("Terms of use") }
            pop()

            append(" and ")
            pushStringAnnotation(
                tag = psUrlTag,
                annotation = stringResource(R.string.github_privacy_policy_url)
            )
            withStyle(style = linkStyle) { append("Privacy Policy") }
            pop()

        }

        ClickableText(
            modifier = Modifier.constrainAs(text) {
                bottom.linkTo(parent.bottom, margin = Spacing.DP16)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = termsOfUseAndPrivacyPolicy,
            style = TextStyle(textAlign = TextAlign.Center, fontSize = FontSize.SP16)
        ) {
            with(termsOfUseAndPrivacyPolicy) {
                getStringAnnotations(tag = tosfUrlTag, start = it, end = it).firstOrNull()?.let {
                    //Open the link here
                    Timber.d("Link: ${it.item}")
                    onOpenUri(Uri.parse(it.item))
                }
                getStringAnnotations(tag = psUrlTag, start = it, end = it).firstOrNull()?.let {
                    //Open the link here
                    Timber.d("Link: ${it.item}")
                    onOpenUri(Uri.parse(it.item))
                }
            }
        }
    }

}

@Composable
@Preview
fun LoginScreenContentPreview() {
    val state = LoginUIState(loading = false, info = UIInfo("An error message", isError = true))
    LoginScreenContent(
        state = state,
        onContinueBtnClick = {}) {

    }
}