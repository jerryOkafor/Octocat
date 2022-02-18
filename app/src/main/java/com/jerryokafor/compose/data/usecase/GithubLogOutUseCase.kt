package com.jerryokafor.compose.data.usecase

import com.jerryokafor.compose.domain.datasource.AppDataSource
import com.jerryokafor.compose.domain.usecase.LogOutUseCase

/**
 * @Author <Author>
 * @Project <Project>
 */

class GithubLogOutUseCase(
    private val appDataSource: AppDataSource
) : LogOutUseCase {
    override suspend fun invoke() = appDataSource.logout()
}