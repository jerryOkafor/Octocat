package com.jerryokafor.compose.ui.util

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

/**
 * @Author <Author>
 * @Project <Project>
 */
object Util {
    @JvmStatic
    fun openUri(context: Context, uri: Uri) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, uri)
    }
}