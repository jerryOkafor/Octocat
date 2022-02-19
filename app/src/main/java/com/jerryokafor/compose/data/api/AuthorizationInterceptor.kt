package com.jerryokafor.compose.data.api

import com.jerryokafor.compose.domain.datasource.AppDataSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Singleton

/**
 * @Author <Author>
 * @Project <Project>
 */

@Singleton
class AuthorizationInterceptor(private val dataStore: AppDataSource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().signedRequest()
        return chain.proceed(newRequest)
    }

    private fun Request.signedRequest(): Request {
        val accessToken = runBlocking { dataStore.getAccessToken().firstOrNull() } ?: ""
        return newBuilder()
            .header("Authorization", "token $accessToken")
            .build()
    }
}