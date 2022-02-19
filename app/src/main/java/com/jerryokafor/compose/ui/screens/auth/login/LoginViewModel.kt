package com.jerryokafor.compose.ui.screens.auth.login

import androidx.lifecycle.viewModelScope
import com.jerryokafor.compose.domain.model.OAuthResponse
import com.jerryokafor.compose.domain.model.onFailure
import com.jerryokafor.compose.domain.model.onLoading
import com.jerryokafor.compose.domain.model.onSuccess
import com.jerryokafor.compose.domain.usecase.LoginUseCase
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

data class LoginUIState(
    override val loading: Boolean = false,
    override val info: UIInfo? = null
) : UIState {
    sealed class State : UIState.Action {
        data class Login(
            override val loading: Boolean = false,
            override val info: UIInfo? = null
        ) : State()


    }
}


@HiltViewModel
class LoginViewModel @Inject constructor(private val login: LoginUseCase) :
    BaseViewModel<LoginUIState, LoginViewModel.Action>(
        initialState = { LoginUIState() },
        reducer = { previousSate, action ->
            when (action) {
                is LoginUIState.State.Login -> previousSate.copy(
                    loading = action.loading,
                    info = action.info
                )
                else -> previousSate
            }

        }) {

    //Optional
    public override fun onAction(action: Action) {
        when (action) {
            is Action.ExchangeCodeForAccessToken -> {
                if (action.oAuthResponse == null || action.oAuthState != action.oAuthResponse.state) {
                    val errorState =
                        nextState(
                            _state.value,
                            LoginUIState.State.Login(
                                loading = false,
                                info = UIInfo(message = "Invalid login attempt, please try again")
                            )
                        )
                    _state.value = errorState
                    return
                }

                viewModelScope.launch {
                    login(action.oAuthResponse.code).collect { resource ->
                        Timber.d("Resource: $resource")
                        resource.onLoading {
                            val loadingState =
                                nextState(_state.value, LoginUIState.State.Login(loading = true))
                            _state.value = loadingState
                        }

                        resource.onSuccess {
                            val successState =
                                nextState(_state.value, LoginUIState.State.Login(loading = false))
                            _state.value = successState
                        }

                        resource.onFailure {
                            val info = UIInfo(message = it, isError = true)
                            val failureState =
                                nextState(
                                    _state.value,
                                    LoginUIState.State.Login(loading = false, info = info)
                                )
                            _state.value = failureState
                        }

                    }
                }
            }
        }
    }

    sealed class Action : UIAction {
        data class ExchangeCodeForAccessToken(
            val oAuthState: String,
            val oAuthResponse: OAuthResponse?
        ) : Action()
    }
}





