package com.jerryokafor.compose.data.api.request

/**
 * @Author <Author>
 * @Project <Project>
 */
data class MarkdownRequest(val text: String, val mode: String = "markdown", val context: String)