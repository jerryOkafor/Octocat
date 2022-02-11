package com.jerryokafor.compose.di

import android.content.Context
import com.jerryokafor.compose.data.datasource.KeyValueStore
import com.jerryokafor.compose.data.respository.MainAuthRepository
import com.jerryokafor.compose.di.dispatchers.IoDispatcher
import com.jerryokafor.compose.di.scope.ApplicationScope
import com.jerryokafor.compose.domain.datasource.AppDataSource
import com.jerryokafor.compose.domain.datasource.AuhDataSource
import com.jerryokafor.compose.domain.model.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

/**
 * @Author <Author>
 * @Project <Project>
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDataSource(
        @ApplicationContext context: Context,
        @ApplicationScope externalScope: CoroutineScope,
        @IoDispatcher defaultDispatcher: CoroutineDispatcher
    ): AppDataSource = KeyValueStore(
        context = context,
        externalScope = externalScope,
        defaultDispatcher = defaultDispatcher
    )

    @Provides
    @Singleton
    fun provideAuthRepository(
        appDataSource: AppDataSource,
        authDataSource: AuhDataSource,
        @IoDispatcher defaultDispatcher: CoroutineDispatcher
    ): AuthRepository =
        MainAuthRepository(
            appDataSource = appDataSource,
            authDataSource = authDataSource,
            defaultDispatcher = defaultDispatcher
        )
}