package com.jerryokafor.compose.di

import com.jerryokafor.compose.BuildConfig
import com.jerryokafor.compose.di.dispatchers.ClientId
import com.jerryokafor.compose.di.dispatchers.ClientSecret
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @Author <Author>
 * @Project <Project>
 */

@Module
@InstallIn(SingletonComponent::class)
object PropertiesModule {

    @Provides
    @ClientId
    fun provideClientId(): String = BuildConfig.clientId

    @Provides
    @ClientSecret
    fun provideClientSecret(): String = BuildConfig.clientSecret
}