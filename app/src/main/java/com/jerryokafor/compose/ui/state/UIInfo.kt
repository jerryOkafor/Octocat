package com.jerryokafor.compose.ui.state

/**
 * @Author <Author>
 * @Project <Project>
 */

/**
 * Helps to send info and error messages to the UI
 *
 * */
data class UIInfo(val message: String? = null, val isError: Boolean = false)