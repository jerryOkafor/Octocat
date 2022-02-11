package com.jerryokafor.compose.data.respository

import com.jerryokafor.compose.AuthState
import com.jerryokafor.compose.domain.datasource.AppDataSource
import com.jerryokafor.compose.domain.datasource.AuhDataSource
import com.jerryokafor.compose.domain.model.Resource
import com.jerryokafor.compose.domain.model.repository.AuthRepository
import com.jerryokafor.compose.ktx.handle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * @Author <Author>
 * @Project <Project>
 */
class MainAuthRepository(
    private val appDataSource: AppDataSource,
    private val authDataSource: AuhDataSource,
    private val defaultDispatcher: CoroutineDispatcher,
) : AuthRepository {
    override fun login(code: String) = flow {
        try {
            emit(Resource.Loading)
            val accessToken = authDataSource.getAccessToken(code = code)

            Timber.d("Access Token: $accessToken")

            //save access token
            appDataSource.saveAccessToken(accessToken)

            emit(Resource.Success(Unit))
        } catch (e: Throwable) {
            Timber.w(e)
            val errorMsg = e.handle()
            emit(Resource.Failure(errorMsg))
        }
    }.flowOn(defaultDispatcher)

    override val authState: SharedFlow<AuthState> = appDataSource.authState

    override suspend fun logout() = appDataSource.logout()
}