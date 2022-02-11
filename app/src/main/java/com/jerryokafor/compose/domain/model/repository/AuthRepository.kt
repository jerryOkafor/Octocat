package com.jerryokafor.compose.domain.model.repository

import com.jerryokafor.compose.AuthState
import com.jerryokafor.compose.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

/**
 * @Author <Author>
 * @Project <Project>
 */
interface AuthRepository {
    fun login(code: String): Flow<Resource<Unit>>
    val authState: SharedFlow<AuthState>
    suspend fun logout()
}