package com.jerryokafor.compose.data.mapping

import com.jerryokafor.compose.domain.model.Owner
import com.jerryokafor.compose.domain.model.PinnedItem
import com.jerryokafor.compose.domain.model.Status
import com.jerryokafor.compose.domain.model.User
import com.octocat.api.UserProfileQuery
import kotlin.math.log

/**
 * @Author <Author>
 * @Project <Project>
 */

fun UserProfileQuery.User.toUser(): User = User(
    login = login,
    id = id,
    avatarUrl = avatarUrl.toString(),
    name = name.toString(),
    status = Status(emojiHTML = status?.emojiHTML.toString(), message = status?.message),
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
    repositories = repositories.totalCount,
    starredRepositories = starredRepositories.totalCount,
    organizations = organizations.totalCount,
    pinnedItems = pinnedItems.edges?.mapNotNull {
        it?.node?.onRepository?.let { item ->
            PinnedItem(
                id = item.id,
                name = item.name,
                description = item.description.toString(),
                stargazers = item.stargazers.totalCount,
                primaryLanguage = item.primaryLanguage?.name,
                owner = Owner(login = item.owner.login, avatarUrl = item.owner.avatarUrl.toString())
            )
        }
    } ?: emptyList(),
    createdAt = createdAt.toString(),
    updatedAt = updatedAt.toString()
)
