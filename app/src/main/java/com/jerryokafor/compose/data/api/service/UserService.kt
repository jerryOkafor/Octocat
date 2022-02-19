package com.jerryokafor.compose.data.api.service

import com.jerryokafor.compose.data.api.request.UserResponse
import retrofit2.http.GET

/**
 * @Author <Author>
 * @Project <Project>
 */
interface UserService {
    @GET("/user")
    suspend fun user(): UserResponse
}