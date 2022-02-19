package com.jerryokafor.compose.ui.state

/**
 * @Author <Author>
 * @Project <Project>
 */

/**
 * Base Class for UIState
 * */
interface UIState {
    val loading: Boolean
    val info: UIInfo?

    interface Action {
        val loading: Boolean
        val info: UIInfo?
    }
}