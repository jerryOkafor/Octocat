package com.jerryokafor.compose.data.mapping

import com.jerryokafor.compose.domain.model.Status
import com.jerryokafor.compose.domain.model.User
import com.octocat.api.UserProfileQuery

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
    createdAt = createdAt.toString(),
    updatedAt = updatedAt.toString()
)
