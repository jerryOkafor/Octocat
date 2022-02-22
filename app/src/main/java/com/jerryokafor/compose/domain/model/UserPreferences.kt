package com.jerryokafor.compose.domain.model

import kotlinx.serialization.Serializable

/**
 * @Author <Author>
 * @Project <Project>
 */

@Serializable
data class UserPreferences(
    val accessToken: String? = null,
    val login: String? = null,
    val userId: String? = null
)