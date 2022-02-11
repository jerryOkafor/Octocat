package com.jerryokafor.compose.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryokafor.compose.domain.datasource.AppDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author <Author>
 * @Project <Project>
 */

@HiltViewModel
class HomeViewModel @Inject constructor(private val appDataSource: AppDataSource) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            appDataSource.logout()
        }
    }
}