package com.jerryokafor.compose.data.usecase

import com.apollographql.apollo3.ApolloClient
import com.jerryokafor.compose.domain.datasource.AppDataSource
import com.jerryokafor.compose.domain.datasource.AuhDataSource
import com.jerryokafor.compose.domain.model.Resource
import com.jerryokafor.compose.domain.usecase.LoginUseCase
import com.jerryokafor.compose.ktx.handle
import com.octocat.api.UserQuery
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

/**
 * @Author <Author>
 * @Project <Project>
 */

class GithubLoginUseCase(
    private val appDataSource: AppDataSource,
    private val authDataSource: AuhDataSource,
    private val apolloClient: ApolloClient,
    private val defaultDispatcher: CoroutineDispatcher
) : LoginUseCase {
    override suspend operator fun invoke(code: String) = flow {
        try {
            emit(Resource.Loading)
            val accessToken = authDataSource.getAccessToken(code = code)
            //save access token
            appDataSource.saveAccessToken(accessToken)

            //retrieve current users details
            val userResponse = apolloClient.query(UserQuery()).execute()
            userResponse.data?.viewer?.login?.let { appDataSource.saveUserLogin(it) }


            emit(Resource.Success(Unit))
        } catch (e: Throwable) {
            Timber.w(e)
            val errorMsg = e.handle()
            emit(Resource.Failure(errorMsg))
        }
    }.flowOn(defaultDispatcher)
}