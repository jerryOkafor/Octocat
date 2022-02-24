package com.jerryokafor.compose.data.usecase

import com.apollographql.apollo3.ApolloClient
import com.jerryokafor.compose.data.api.request.MarkdownRequest
import com.jerryokafor.compose.data.api.service.MarkdownService
import com.jerryokafor.compose.data.mapping.toUser
import com.jerryokafor.compose.domain.datasource.AppDataSource
import com.jerryokafor.compose.domain.model.Resource
import com.jerryokafor.compose.domain.usecase.GetUserUseCase
import com.jerryokafor.compose.ktx.handle
import com.octocat.api.UserProfileQuery
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 * @Author <Author>
 * @Project <Project>
 */
class GithubGetUserUseCase(
    private val appDataSource: AppDataSource,
    private val markdownService: MarkdownService,
    private val apolloClient: ApolloClient
) : GetUserUseCase {
    override suspend fun invoke() = flow {
        emit(Resource.Loading)
        try {
            val userLogin = appDataSource.getUserLogin().first()

            //Todo: Improve error handling, use custom errors
            if (userLogin.isNullOrEmpty()) throw Exception("User not logged in")

            val response = apolloClient.query(UserProfileQuery(userLogin)).execute()
            val user = response.data?.user?.toUser()!!

            val html = markdownService.markdown(
                markdownRequest = MarkdownRequest(
                    text = user.specialRepo.readme,
                    context = "$userLogin/$userLogin"
                )
            )

           val newUser =  user.copy(specialRepo = user.specialRepo.copy(readme = html.body()?.string().toString()))

            Timber.d("User: ${user.specialRepo}")
            Timber.d("HTMl: ${html.body()?.string().toString()}")

            emit(Resource.Success(newUser))
        } catch (e: Throwable) {
            Timber.w(e)
            val error = e.handle()
            emit(Resource.Failure(error = error))
        }
    }

}