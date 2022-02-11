package com.jerryokafor.compose.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jerryokafor.compose.BuildConfig
import com.jerryokafor.compose.data.api.AuthService
import com.jerryokafor.compose.data.datasource.GithubAuthDataSource
import com.jerryokafor.compose.di.dispatchers.ClientId
import com.jerryokafor.compose.di.dispatchers.ClientSecret
import com.jerryokafor.compose.domain.datasource.AuhDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * @Author <Author>
 * @Project <Project>
 */

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class OAuthRetrofit

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApiRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //Todo : use a different OkHttClient for 0AUth
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
//            .addInterceptor(AuthorizationInterceptor(localDataSource))

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    @ApiRetrofit
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    @Provides
    @Singleton
    @OAuthRetrofit
    fun provideOAuthRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.OAUTH_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideAuthApiService(@OAuthRetrofit retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)


    @Provides
    @Singleton
    fun provideAuthDataSource(
        authService: AuthService,
        @ClientId clientId: String,
        @ClientSecret clientSecret: String
    ): AuhDataSource = GithubAuthDataSource(
        authService = authService,
        clientId = clientId,
        clientSecret = clientSecret
    )
}