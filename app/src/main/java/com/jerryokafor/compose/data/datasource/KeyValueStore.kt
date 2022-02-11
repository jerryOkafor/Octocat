package com.jerryokafor.compose.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jerryokafor.compose.AuthState
import com.jerryokafor.compose.domain.datasource.AppDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @Author <Author>
 * @Project <Project>
 */
class KeyValueStore(
    private val context: Context,
    private val externalScope: CoroutineScope,
    private val defaultDispatcher: CoroutineDispatcher
) :
    AppDataSource {
    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override val authState: SharedFlow<AuthState> =
        context.dataStore.data.map {
            if (it[ACCESS_TOKEN_KEY]?.isEmpty() == true) AuthState.NO_AUTH else AuthState.AUTH
        }.flowOn(defaultDispatcher)
            .onEach {
                Timber.d("Auth Repo: $it")
            }
            .shareIn(
                scope = externalScope,
                replay = 5,
                started = SharingStarted.WhileSubscribed()
            )


    override suspend fun logout() {
        externalScope.launch(defaultDispatcher) {
            context.dataStore.edit { it[ACCESS_TOKEN_KEY] = "" }
        }.join()
    }

    override suspend fun saveAccessToken(token: String) {
        Timber.d("Saving Access token")
        externalScope.launch(defaultDispatcher) {
            context.dataStore.edit {
                it[ACCESS_TOKEN_KEY] = token
            }
        }.join()

    }

    override suspend fun getAccessToken(): Flow<String?> =
        context.dataStore.data.map { it[ACCESS_TOKEN_KEY] }.flowOn(defaultDispatcher)

}