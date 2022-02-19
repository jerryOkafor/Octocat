package com.jerryokafor.compose.ui.state

/**
 * @Author <Author>
 * @Project <Project>
 */

/**
 * Reducer for UIState from previous state and action
 * */
typealias UIStateReducer<S> = (previousSate: S, action: UIState.Action) -> S