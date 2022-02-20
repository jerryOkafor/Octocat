package com.jerryokafor.compose.domain.model

import com.jerryokafor.compose.data.api.request.PlanResponse

/**
 * @Author <Author>
 * @Project <Project>
 */


data class Plan(
    val name: String,
    val space: Int,
    val privateRepos: Int,
    val collaborators: Int
)

data class Status(val emojiHTML: String?, val message: String?)

data class User(
    val login: String,
    val id: String,
    val avatarUrl: String,
    val name: String,
    val status: Status,
    val company: String?,
    val blog: String,
    val location: String,
    val email: String,
    val bio: String,
    val twitterUsername: String,
    val publicRepos: Int,
    val followers: Int,
    val following: Int,
    val totalPrivateRepos: Int,
    val ownedPrivateRepos: Int,
    val repositories: Int = 0,
    val organizations: Int = 0,
    val starredRepositories: Int = 0,
    val createdAt: String,
    val updatedAt: String
)