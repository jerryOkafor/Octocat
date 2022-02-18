package com.jerryokafor.compose.ui.screens.state

/**
 * @Author <Author>
 * @Project <Project>
 */

typealias UIStateReducer<S> = (previousSate: S, action: UIState.Action) -> S