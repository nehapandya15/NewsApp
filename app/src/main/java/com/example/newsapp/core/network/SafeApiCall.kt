package com.example.newsapp.core.network
import com.example.newsapp.core.common.NetworkResult
import retrofit2.Response
import java.io.IOException

suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> Response<T>
): NetworkResult<T> {

    return try {

        val response = apiCall()

        if (response.isSuccessful) {

            response.body()?.let {
                NetworkResult.Success(it)
            } ?: NetworkResult.Error("Empty response")

        } else {

            NetworkResult.Error(
                message = response.message(),
                code = response.code()
            )
        }

    } catch (_: IOException) {

        NetworkResult.Error("Please check your internet connection.")

    } catch (e: Exception) {

        NetworkResult.Error(e.message ?: "Something went wrong.")
    }
}