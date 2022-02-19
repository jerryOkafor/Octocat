package com.jerryokafor.compose.data.usecase

import com.jerryokafor.compose.data.api.request.toUser
import com.jerryokafor.compose.data.api.service.UserService
import com.jerryokafor.compose.domain.model.Resource
import com.jerryokafor.compose.domain.usecase.GetUserUseCase
import com.jerryokafor.compose.ktx.handle
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 * @Author <Author>
 * @Project <Project>
 */
class GithubGetUserUseCase(private val userService: UserService) : GetUserUseCase {
    override suspend fun invoke() = flow {
        emit(Resource.Loading)
        try {
            val userResponse = userService.user()
            val user = userResponse.toUser()
            emit(Resource.Success(user))
        } catch (e: Throwable) {
            Timber.w(e)
            val error = e.handle()
            emit(Resource.Failure(error = error))
        }
    }


}