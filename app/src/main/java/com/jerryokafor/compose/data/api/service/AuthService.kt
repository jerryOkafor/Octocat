package com.jerryokafor.compose.data.api.service

import com.jerryokafor.compose.data.api.request.AccessTokenRequest
import com.jerryokafor.compose.data.api.response.AccessTokenResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * @Author <Author>
 * @Project <Project>
 */
interface AuthService {

    @POST("access_token")
    @Headers("Accept: application/json")
    suspend fun getAccessToken(@Body accessTokenRequest: AccessTokenRequest): AccessTokenResponse
}