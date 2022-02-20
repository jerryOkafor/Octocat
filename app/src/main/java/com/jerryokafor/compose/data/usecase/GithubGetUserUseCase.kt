package com.jerryokafor.compose.data.usecase

import com.apollographql.apollo3.ApolloClient
import com.jerryokafor.compose.domain.model.Resource
import com.jerryokafor.compose.domain.model.Status
import com.jerryokafor.compose.domain.model.User
import com.jerryokafor.compose.domain.usecase.GetUserUseCase
import com.jerryokafor.compose.ktx.handle
import com.octocat.api.UserDataQuery
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 * @Author <Author>
 * @Project <Project>
 */
class GithubGetUserUseCase(
    private val apolloClient: ApolloClient
) : GetUserUseCase {
    override suspend fun invoke() = flow {
        emit(Resource.Loading)
        try {
            val response = apolloClient.query(UserDataQuery("jerryOkafor")).execute()
            val user = response.data?.viewer?.toUser()!!
            Timber.d("User: $user")
            emit(Resource.Success(user))
        } catch (e: Throwable) {
            Timber.w(e)
            val error = e.handle()
            emit(Resource.Failure(error = error))
        }
    }

}

private fun UserDataQuery.Viewer.toUser(): User = User(
    login = login,
    id = id,
    avatarUrl = avatarUrl.toString(),
    name = name.toString(),
    status = Status(emoji = status?.emoji, message = status?.message),
    company = company,
    blog = websiteUrl.toString(),
    location = location.toString(),
    email = email,
    bio = bio.toString(),
    twitterUsername = twitterUsername.toString(),
    publicRepos = publicRepos.totalCount,
    followers = followers.totalCount,
    following = following.totalCount,
    totalPrivateRepos = privateRepos.totalCount,
    ownedPrivateRepos = privateRepos.totalCount,
    createdAt = createdAt.toString(),
    updatedAt = updatedAt.toString()
)
