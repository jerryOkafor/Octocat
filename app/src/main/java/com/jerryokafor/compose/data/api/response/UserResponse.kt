package com.jerryokafor.compose.data.api.response

import com.google.gson.annotations.SerializedName
import com.jerryokafor.compose.domain.model.Plan
import com.jerryokafor.compose.domain.model.SpecialRepo
import com.jerryokafor.compose.domain.model.Status
import com.jerryokafor.compose.domain.model.User

/**
 * @Author <Author>
 * @Project <Project>
 */

data class UserResponse(
    val login: String,
    val id: Long,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val url: String
)