package com.jerryokafor.compose.data.api.request

import com.google.gson.annotations.SerializedName
import com.jerryokafor.compose.domain.model.Plan
import com.jerryokafor.compose.domain.model.Status
import com.jerryokafor.compose.domain.model.User

/**
 * @Author <Author>
 * @Project <Project>
 */

data class PlanResponse(
    val name: String,
    val space: Int,
    @SerializedName("private_repos")
    val privateRepos: Int,
    val collaborators: Int
)

data class UserResponse(
    val login: String,
    val id: Long,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val url: String,
    @SerializedName("followers_url")
    val followersUrl: String,
    @SerializedName("following_url")
    val followingUrl: String,
    @SerializedName("git_url")
    val gitUrl: String?,
    @SerializedName("starred_url")
    val starredUrl: String,
    @SerializedName("subscription_url")
    val subscriptionUrl: String?,
    @SerializedName("organisation_url")
    val organisationUrl: String?,
    @SerializedName("repos_url")
    val reposUrl: String,
    @SerializedName("events_url")
    val eventsUrl: String,
    val type: String,
    @SerializedName("site_admin")
    val siteAdmin: Boolean = false,
    val name: String,
    val company: String?,
    val blog: String,
    val location: String,
    val email: String,
    val hireable: Boolean = false,
    val bio: String,
    @SerializedName("twitter_username")
    val twitterUsername: String,
    @SerializedName("public_repos")
    val publicRepos: Int,
    @SerializedName("public_gists")
    val publicGits: Int,
    val followers: Int,
    val following: Int,
    @SerializedName("private_gists")
    val privateGists: Int,
    @SerializedName("total_private_repos")
    val totalPrivateRepos: Int,
    @SerializedName("owned_private_repos")
    val ownedPrivateRepos: Int,
    @SerializedName("disk_usage")
    val diskUsage: Int,
    val collaborators: Int,
    val plan: PlanResponse,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

fun UserResponse.toUser(): User = User(
    login = login,
    id = id.toString(),
    avatarUrl = avatarUrl,
    name = name,
    status = Status("", ""),
    company = company,
    blog = blog,
    location = location,
    email = email,
    bio = bio,
    twitterUsername = twitterUsername,
    publicRepos = publicRepos,
    followers = followers,
    following = following,
    totalPrivateRepos = totalPrivateRepos,
    ownedPrivateRepos = ownedPrivateRepos,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun PlanResponse.toPlan(): Plan =
    Plan(name = name, space = space, privateRepos = privateRepos, collaborators = collaborators)