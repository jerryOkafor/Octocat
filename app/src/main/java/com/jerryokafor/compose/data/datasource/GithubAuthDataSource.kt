package com.jerryokafor.compose.data.datasource

import com.jerryokafor.compose.data.api.AuthService
import com.jerryokafor.compose.data.api.request.AccessTokenRequest
import com.jerryokafor.compose.di.dispatchers.ClientId
import com.jerryokafor.compose.di.dispatchers.ClientSecret
import com.jerryokafor.compose.domain.datasource.AuhDataSource

/**
 * @Author <Author>
 * @Project <Project>
 */

class GithubAuthDataSource(
    private val authService: AuthService,
    @ClientId private val clientId: String,
    @ClientSecret private val clientSecret: String
) : AuhDataSource {
    override suspend fun getAccessToken(code: String): String = authService.getAccessToken(
        AccessTokenRequest(
            clientId = clientId,
            clientSecret = clientSecret,
            code = code,
            redirectUrl = "repo-auth://callback"
        )
    ).accessToken
}