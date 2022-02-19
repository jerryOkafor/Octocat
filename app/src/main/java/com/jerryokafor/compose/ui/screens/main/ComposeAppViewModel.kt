package com.jerryokafor.compose.ui.screens.main

import com.jerryokafor.compose.AuthState
import com.jerryokafor.compose.domain.usecase.AuthStateUseCase
import com.jerryokafor.compose.ui.screens.auth.login.LoginUIState
import com.jerryokafor.compose.ui.state.BaseViewModel
import com.jerryokafor.compose.ui.state.UIAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

/**
 * @Author <Author>
 * @Project <Project>
 */


@HiltViewModel
class ComposeAppViewModel
@Inject constructor(authStateUseCase: AuthStateUseCase) :
    BaseViewModel<LoginUIState, UIAction>(
        initialState = { LoginUIState() },
        reducer = { previousSate, _ ->
            previousSate
        }) {
    val authState: SharedFlow<AuthState> = authStateUseCase.authState


}