package com.jerryokafor.compose.domain.datasource

/**
 * @Author <Author>
 * @Project <Project>
 */
interface AuhDataSource {
    suspend fun getAccessToken(code: String): String
}