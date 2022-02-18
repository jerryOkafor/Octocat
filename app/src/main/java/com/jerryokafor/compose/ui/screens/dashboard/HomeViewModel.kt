package com.jerryokafor.compose.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryokafor.compose.domain.datasource.AppDataSource
import com.jerryokafor.compose.domain.usecase.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author <Author>
 * @Project <Project>
 */

@HiltViewModel
class HomeViewModel @Inject constructor(private val logOut: LogOutUseCase) : ViewModel() {

    //Todo: refactor, use on action
    fun logout() {
        viewModelScope.launch {
            logOut()
        }
    }
}