package com.jerryokafor.compose.domain.datasource

import com.jerryokafor.compose.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

/**
 * @Author <Author>
 * @Project <Project>
 */
interface AppDataSource {
    val authState: SharedFlow<AuthState>
    suspend fun logout()
    suspend fun saveAccessToken(token: String)
    fun getAccessToken(): Flow<String?>

    suspend fun saveUserLogin(login: String)
    fun getUserLogin(): Flow<String?>
}