package com.jerryokafor.compose.ui.screens.main

import androidx.lifecycle.ViewModel
import com.jerryokafor.compose.AuthState
import com.jerryokafor.compose.domain.model.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

/**
 * @Author <Author>
 * @Project <Project>
 */
@HiltViewModel
class ComposeAppViewModel
@Inject constructor(authRepository: AuthRepository) : ViewModel() {
    val authState: SharedFlow<AuthState> = authRepository.authState
}