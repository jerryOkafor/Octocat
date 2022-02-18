package com.jerryokafor.compose.ui.screens.state

/**
 * @Author <Author>
 * @Project <Project>
 */

open class UIState(open val loading: Boolean = false, open val info: UIInfo? = null) {
    open class Action {
        open val loading: Boolean = false
        open val info: UIInfo? = null
    }
}