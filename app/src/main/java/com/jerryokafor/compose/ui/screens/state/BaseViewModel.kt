package com.jerryokafor.compose.ui.screens.state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * @Author <Author>
 * @Project <Project>
 */

/**
 * Reducer kind of ViewModel that implements Unidirectional Data Flow (UDF) and single
 * point of action invocation while exposing an immutable data flow for composable views
 * in the form of [StateFlow]
 * */
abstract class BaseViewModel<T : UIState, A : UIAction>(
    initialState: () -> T,
    reducer: UIStateReducer<T>?
) :
    ViewModel() {


    /**
     * Private Mutable state holder, helps to hold the state of the
     * view while exposing an Immutable state as [StateFlow]
     * */
    protected val _state = MutableStateFlow(initialState())

    /**
     * An Immutable public state flow that is exposed to the view
     * */
    val state = _state.asStateFlow()

    /**
     * Redux kind of state reducer. This defines how the next state is computed
     * from the current event/Action and  the previous state
     * */
    protected val nextState: UIStateReducer<T> =
        reducer ?: { previousSate: T, _: UIState.Action ->
            previousSate
        }

    /**
     * Represents actions of type [A] that can be pushed from the the
     * view to the  ViewModel in line with Unidirectional Data Floe (UDF)
     * */
    protected open fun onAction(action: A) {}
}