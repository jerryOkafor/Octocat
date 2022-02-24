package com.jerryokafor.compose.data.api.service

import com.jerryokafor.compose.data.api.request.MarkdownRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @Author <Author>
 * @Project <Project>
 */
interface MarkdownService {
    @POST("/markdown")
    suspend fun markdown(@Body markdownRequest: MarkdownRequest): Response<ResponseBody>
}