package com.jerryokafor.compose.di

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.jerryokafor.compose.data.api.service.UserService
import com.jerryokafor.compose.data.datasource.KeyValueStore
import com.jerryokafor.compose.data.usecase.GithubAuthStateUseCase
import com.jerryokafor.compose.data.usecase.GithubGetUserUseCase
import com.jerryokafor.compose.data.usecase.GithubLogOutUseCase
import com.jerryokafor.compose.data.usecase.GithubLoginUseCase
import com.jerryokafor.compose.di.dispatchers.IoDispatcher
import com.jerryokafor.compose.di.scope.ApplicationScope
import com.jerryokafor.compose.domain.datasource.AppDataSource
import com.jerryokafor.compose.domain.datasource.AuhDataSource
import com.jerryokafor.compose.domain.usecase.AuthStateUseCase
import com.jerryokafor.compose.domain.usecase.GetUserUseCase
import com.jerryokafor.compose.domain.usecase.LogOutUseCase
import com.jerryokafor.compose.domain.usecase.LoginUseCase
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
    fun provideGithubLoginUseCase(
        appDataSource: AppDataSource,
        apolloClient: ApolloClient,
        authDataSource: AuhDataSource,
        @IoDispatcher defaultDispatcher: CoroutineDispatcher
    ): LoginUseCase =
        GithubLoginUseCase(
            appDataSource = appDataSource,
            authDataSource = authDataSource,
            apolloClient = apolloClient,
            defaultDispatcher = defaultDispatcher
        )

    @Provides
    @Singleton
    fun provideGithubLogOutUseCase(
        appDataSource: AppDataSource
    ): LogOutUseCase =
        GithubLogOutUseCase(appDataSource = appDataSource)

    @Provides
    @Singleton
    fun provideAuthStateUseCase(
        appDataSource: AppDataSource
    ): AuthStateUseCase =
        GithubAuthStateUseCase(appDataSource = appDataSource)

    @Provides
    @Singleton
    fun provideGetUserUseCase(
        appDataSource: AppDataSource,
        apolloClient: ApolloClient
    ): GetUserUseCase =
        GithubGetUserUseCase(appDataSource = appDataSource, apolloClient = apolloClient)
}