package com.jerryokafor.compose.ui.screens.dashboard.profile

import androidx.lifecycle.viewModelScope
import com.jerryokafor.compose.domain.model.User
import com.jerryokafor.compose.domain.model.onFailure
import com.jerryokafor.compose.domain.model.onLoading
import com.jerryokafor.compose.domain.model.onSuccess
import com.jerryokafor.compose.domain.usecase.GetUserUseCase
import com.jerryokafor.compose.ui.state.BaseViewModel
import com.jerryokafor.compose.ui.state.UIAction
import com.jerryokafor.compose.ui.state.UIInfo
import com.jerryokafor.compose.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * @Author <Author>
 * @Project <Project>
 */

data class ProfileUIState(
    override val loading: Boolean = false,
    override val info: UIInfo? = null,
    val user: User? = null
) : UIState {
    sealed class State : UIState.Action {
        open val user: User? = null

        data class GetUser(
            override val loading: Boolean = false,
            override val info: UIInfo? = null,
            override val user: User? = null
        ) : State()


    }
}

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getUser: GetUserUseCase) :
    BaseViewModel<ProfileUIState, ProfileViewModel.Action>(initialState = { ProfileUIState() },
        reducer = { previousSate, action ->
            when (action) {
                is ProfileUIState.State.GetUser -> previousSate.copy(
                    loading = action.loading,
                    info = action.info,
                    user = action.user ?: previousSate.user
                )
                else -> previousSate
            }
        }) {


    override fun onAction(action: Action) {
        when (action) {
            is Action.GetUserProfile -> {
                viewModelScope.launch {
                    getUser().collect { resource ->
                        Timber.d("Resource: $resource")
                        resource.onLoading {
                            val loadingState = nextState(
                                _state.value,
                                ProfileUIState.State.GetUser(loading = true)
                            )
                            _state.value = loadingState
                        }

                        resource.onSuccess {
                            val successState = nextState(
                                _state.value,
                                ProfileUIState.State.GetUser(loading = false, user = it)
                            )
                            _state.value = successState
                        }

                        resource.onFailure {
                            val uiInfo = UIInfo(message = it)
                            val failureState = nextState(
                                _state.value,
                                ProfileUIState.State.GetUser(loading = false, info = uiInfo)
                            )
                            _state.value = failureState
                        }
                    }
                }
            }
        }

    }

    sealed class Action : UIAction {
        data class GetUserProfile(val force: Boolean = false) : Action()
    }
}