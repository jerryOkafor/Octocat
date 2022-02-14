package com.jerryokafor.compose.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryokafor.compose.domain.model.OAuthResponse
import com.jerryokafor.compose.domain.model.onFailure
import com.jerryokafor.compose.domain.model.onLoading
import com.jerryokafor.compose.domain.model.onSuccess
import com.jerryokafor.compose.domain.model.repository.AuthRepository
import com.jerryokafor.compose.ui.screens.state.UIInfo
import com.jerryokafor.compose.ui.screens.state.UIState
import com.jerryokafor.compose.ui.screens.state.UIStateAction
import com.jerryokafor.compose.ui.screens.state.UIStateReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : UIState()

sealed class LoginUIStateAction : UIStateAction() {
    data class Login(
        override val loading: Boolean = false,
        override val info: UIInfo? = null
    ) : LoginUIStateAction()


}

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _state = MutableStateFlow(LoginUIState())
    val state = _state.asStateFlow()

    private val loginUIState: UIStateReducer<LoginUIState> = { previousSate, action ->
        when (action) {
            is LoginUIStateAction.Login -> previousSate.copy(
                loading = action.loading,
                info = action.info
            )
            else -> previousSate
        }
    }

    fun exchangeCodeForAccessToken(oAuthState: String, oAuthResponse: OAuthResponse?) {
        if (oAuthResponse == null || oAuthState != oAuthResponse.state) {
            val errorState =
                loginUIState(
                    _state.value,
                    LoginUIStateAction.Login(
                        loading = false,
                        info = UIInfo(message = "Invalid login attempt, please try again")
                    )
                )
            _state.value = errorState
            return
        }

        viewModelScope.launch {
            authRepository.login(oAuthResponse.code).collect { resource ->
                Timber.d("Resource: $resource")
                resource.onLoading {
                    val loadingState =
                        loginUIState(_state.value, LoginUIStateAction.Login(loading = true))
                    _state.value = loadingState
                }

                resource.onSuccess {
                    val successState =
                        loginUIState(_state.value, LoginUIStateAction.Login(loading = false))
                    _state.value = successState
                }

                resource.onFailure {
                    val info = UIInfo(message = it, isError = true)
                    val failureState =
                        loginUIState(
                            _state.value,
                            LoginUIStateAction.Login(loading = false, info = info)
                        )
                    _state.value = failureState
                }

            }
        }


    }


}





