package com.jerryokafor.compose.ktx

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketException
import java.net.UnknownHostException

/**
 * @Author <Author>
 * @Project <Project>
 */
data class APIError(val message: String)

fun Throwable.handle(): String {
    val message: String
    when (this) {
        is SocketException -> {
            //Very bad internet
            Timber.w("Poor internet connection")
            message = "Poor internet connection, please try again"
        }
        is HttpException -> {
            //server/client error - parse error body
            message = try {
                val errorBody = this.response()?.errorBody()
                val gson = Gson()
                val type = object : TypeToken<APIError>() {}.type
                val errorResponse: APIError? = gson.fromJson(errorBody?.charStream(), type)
                errorResponse?.message ?: "Server error"
            } catch (e: Exception) {
                Timber.w("Error decoding HTTP error body: ${e.message}")
                "Unknown error, please try again"
            }
        }
        is UnknownHostException -> {
            //Probably no internet or your base url is wrong
            Timber.w("No internet or url not found")
            message = "Unable to complete request, please try again"
        }
        else -> message = "An error occurred, please try again"
    }

    return message
}