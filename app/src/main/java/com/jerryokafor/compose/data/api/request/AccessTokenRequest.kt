package com.jerryokafor.compose.data.api.request

import com.google.gson.annotations.SerializedName

/**
 * @Author <Author>
 * @Project <Project>
 */
data class AccessTokenRequest(
    @SerializedName("client_id")
    val clientId: String,

    @SerializedName("client_secret")
    val clientSecret: String,

    val code: String,

    @SerializedName("redirect_url")
    val redirectUrl: String
)