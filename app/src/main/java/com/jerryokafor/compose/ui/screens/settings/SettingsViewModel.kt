package com.jerryokafor.compose.ui.screens.settings

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jerryokafor.compose.domain.datasource.AppDataSource
import com.jerryokafor.compose.ui.state.BaseViewModel
import com.jerryokafor.compose.ui.state.UIAction
import com.jerryokafor.compose.ui.state.UIInfo
import com.jerryokafor.compose.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author <Author>
 * @Project <Project>
 */
data class SettingUIState(
    override val loading: Boolean = false,
    override val info: UIInfo? = null
) : UIState

@HiltViewModel
class SettingsViewModel @Inject constructor(private val appDataSource: AppDataSource) :
    BaseViewModel<SettingUIState, SettingsViewModel.Action>(initialState = { SettingUIState() }) {
    override fun onAction(action: Action) {
        when (action) {
            Action.SignOut -> {
                viewModelScope.launch {
                    appDataSource.logout()
                }
            }
        }
    }

    sealed class Action : UIAction {
        object SignOut : Action()
    }
}