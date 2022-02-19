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

data class User(
    val login: String,
    val id: Long,
    val avatarUrl: String,
    val url: String,
    val followersUrl: String,
    val followingUrl: String,
    val gitUrl: String?,
    val starredUrl: String,
    val subscriptionUrl: String?,
    val organisationUrl: String?,
    val reposUrl: String,
    val eventsUrl: String,
    val type: String,
    val siteAdmin: Boolean = false,
    val name: String,
    val company: String?,
    val blog: String,
    val location: String,
    val email: String,
    val hireable: Boolean = false,
    val bio: String,
    val twitterUsername: String,
    val publicRepos: Int,
    val publicGits: Int,
    val followers: Int,
    val following: Int,
    val privateGists: Int,
    val totalPrivateRepos: Int,
    val ownedPrivateRepos: Int,
    val diskUsage: Int,
    val collaborators: Int,
    val plan: Plan,
    val createdAt: String,
    val updatedAt: String
)