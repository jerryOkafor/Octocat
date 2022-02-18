package com.jerryokafor.compose.data.usecase

import com.jerryokafor.compose.AuthState
import com.jerryokafor.compose.domain.datasource.AppDataSource
import com.jerryokafor.compose.domain.usecase.AuthStateUseCase
import kotlinx.coroutines.flow.SharedFlow

/**
 * @Author <Author>
 * @Project <Project>
 */
class GithubAuthStateUseCase(appDataSource: AppDataSource) : AuthStateUseCase {
    override val authState: SharedFlow<AuthState> = appDataSource.authState
}