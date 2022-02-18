package com.jerryokafor.compose.domain.usecase

import com.jerryokafor.compose.AuthState
import kotlinx.coroutines.flow.SharedFlow

/**
 * @Author <Author>
 * @Project <Project>
 */
interface AuthStateUseCase {
    val authState: SharedFlow<AuthState>
}