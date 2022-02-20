package com.jerryokafor.compose

import android.app.Application
import androidx.core.provider.FontRequest
import androidx.emoji2.text.EmojiCompat
import androidx.emoji2.text.FontRequestEmojiCompatConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * @Author Jerry Okafor
 * @Project ComposeTemplate
 * @Date 15/10/2021 06:33
 */
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val fontRequest = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Noto Color Emoji Compat",
            R.array.com_google_android_gms_fonts_certs
        )
        val config = FontRequestEmojiCompatConfig(applicationContext, fontRequest)
            .setReplaceAll(true)
            .registerInitCallback(object : EmojiCompat.InitCallback() {
                override fun onInitialized() {
                    Timber.d("EmojiCompat initialized")
                }

                override fun onFailed(throwable: Throwable?) {
                    Timber.e("EmojiCompat initialization failed: ${throwable?.message}")
                }
            })
        EmojiCompat.init(config)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}