package com.jerryokafor.compose.data.api.response

import com.google.gson.annotations.SerializedName

/**
 * @Author <Author>
 * @Project <Project>
 */
data class AccessTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val type: String,

    val scope: String
)