package com.jerryokafor.compose

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Slide
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract
import androidx.browser.customtabs.CustomTabsIntent
import com.jerryokafor.compose.domain.model.OAuthResponse
import timber.log.Timber

/**
 * @Author <Author>
 * @Project <Project>
 */

class OAuthActivity : ComponentActivity() {
    companion object {
        private const val OAUTH_STATE = "OAUTH_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        super.onCreate(savedInstanceState)
        Timber.d("onCreate()")

        val oAuthState = intent?.getStringExtra(OAUTH_STATE)

        val authorizationUrl = Uri.parse("https://github.com/login/oauth/authorize")
            .buildUpon()
            .appendQueryParameter("client_id", BuildConfig.clientId)
            .appendQueryParameter("redirect_url", "repo-auth://callback")
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("state", oAuthState)
            .appendQueryParameter("scope", "user,repo,public_repo,read:org, read:status,notifications")
            .build()

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, authorizationUrl)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.d("onNewIntent(): ${intent?.data}")
        setResult(RESULT_OK, intent)
        finish()
    }


    class ResultContract : ActivityResultContract<String, OAuthResponse?>() {
        override fun createIntent(context: Context, input: String): Intent {
            return Intent(context, OAuthActivity::class.java).apply {
                putExtra(OAUTH_STATE, input)
            }

        }

        override fun parseResult(resultCode: Int, intent: Intent?): OAuthResponse? {
            if (resultCode == RESULT_OK && intent != null) {
                val code = intent.data?.getQueryParameter("code")!!
                val state = intent.data?.getQueryParameter("state")!!
                return OAuthResponse(code = code, state = state)
            }

            return null
        }

    }

    override fun finish() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        super.finish()

    }
}