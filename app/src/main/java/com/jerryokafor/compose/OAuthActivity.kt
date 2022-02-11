package com.jerryokafor.compose

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.os.bundleOf
import timber.log.Timber
import java.util.*

/**
 * @Author <Author>
 * @Project <Project>
 */

data class OAuthResponse(val code: String, val state: String)
class OAuthActivity : ComponentActivity() {
    companion object {
        private const val OAUTH_STATE = "OAUTH_STATE"
        private const val OAUTH_ACCESS_CODE = "AUTH_ACCESS_CODE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate()")

        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            // set an exit transition
            exitTransition = Fade()
        }

        val oAuthState = intent?.getStringExtra(OAUTH_STATE)

        val authorizationUrl = Uri.parse("https://github.com/login/oauth/authorize")
            .buildUpon()
            .appendQueryParameter("client_id", BuildConfig.clientId)
            .appendQueryParameter("redirect_url", "repo-auth://callback")
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("state", oAuthState)
            .appendQueryParameter("scope", "user,repo,notifications")
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
}