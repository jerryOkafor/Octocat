package com.jerryokafor.compose.data.datasource

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jerryokafor.compose.AuthState
import com.jerryokafor.compose.domain.datasource.AppDataSource
import com.jerryokafor.compose.domain.model.UserPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream

/**
 * @Author <Author>
 * @Project <Project>
 */


object UserPreferencesSerializer : Serializer<UserPreferences> {

    override val defaultValue = UserPreferences()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        try {
            return Json.decodeFromString(
                UserPreferences.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read UserPrefs", serialization)
        }
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        output.write(
            Json.encodeToString(UserPreferences.serializer(), t)
                .encodeToByteArray()
        )
    }
}

class KeyValueStore(
    private val context: Context,
    private val externalScope: CoroutineScope,
    private val defaultDispatcher: CoroutineDispatcher
) : AppDataSource {

    private val Context.dataStore by dataStore(
        fileName = "user_pref_file.json",
        serializer = UserPreferencesSerializer
    )

    override suspend fun saveUserLogin(login: String) {
        externalScope.launch(defaultDispatcher) {
            context.dataStore.updateData { it.copy(login = login) }
        }.join()
    }

    override fun getUserLogin(): Flow<String?> =
        context.dataStore.data.map { it.login }.flowOn(defaultDispatcher)

    override val authState: SharedFlow<AuthState> =
        context.dataStore.data.map {
            if (it.accessToken.isNullOrBlank()) AuthState.NO_AUTH else AuthState.AUTH
        }.flowOn(defaultDispatcher)
            .shareIn(
                scope = externalScope,
                replay = 1,
                started = SharingStarted.WhileSubscribed()
            )


    override suspend fun logout() {
        externalScope.launch(defaultDispatcher) {
            context.dataStore.updateData { it.copy(accessToken = "", login = "") }
        }.join()
    }

    override suspend fun saveAccessToken(token: String) {
        externalScope.launch(defaultDispatcher) {
            context.dataStore.updateData { it.copy(accessToken = token) }
        }.join()
    }

    override fun getAccessToken(): Flow<String?> =
        context.dataStore.data.map { it.accessToken }.flowOn(defaultDispatcher)

}