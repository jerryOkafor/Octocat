package com.jerryokafor.compose

import androidx.lifecycle.ViewModel
import com.jerryokafor.compose.domain.model.OAuthAccessCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber
import javax.inject.Inject

/**
 * @Author <Author>
 * @Project <Project>
 */

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    private val _accessTokenRequest = MutableSharedFlow<OAuthAccessCode>(replay = 1)
    val accessTokenRequest = _accessTokenRequest.asSharedFlow()

    fun setAccessTokenCode(accessTokenCode: OAuthAccessCode) {
        _accessTokenRequest.tryEmit(accessTokenCode)
    }


}