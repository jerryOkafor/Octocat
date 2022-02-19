package com.jerryokafor.compose.domain.usecase

import com.jerryokafor.compose.domain.model.Resource
import com.jerryokafor.compose.domain.model.User
import kotlinx.coroutines.flow.Flow


/**
 * @Author <Author>
 * @Project <Project>
 */
interface GetUserUseCase {
    suspend operator fun invoke(): Flow<Resource<User>>
}