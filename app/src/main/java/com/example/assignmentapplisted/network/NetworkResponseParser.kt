package com.example.assignmentapplisted.network


import retrofit2.Response

fun <T : Any> networkCall(response: Response<T>): ApiResult<T> {
    return if (response.isSuccessful) {
        response.body()?.let { ApiResult.Success(response.body()) }
            ?: ApiResult.Error("Response Is Empty")
    }
    else
        ApiResult.Error(response.message())
}