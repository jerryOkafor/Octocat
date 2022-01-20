package com.jerryokafor.compose

import android.app.Application
import timber.log.Timber

/**
 * @Author Jerry Okafor
 * @Project ComposeTemplate
 * @Date 15/10/2021 06:33
 */
class ComposeTemplateApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }
}