package com.example.assignmentapplisted.network

import com.example.assignmentapplisted.home.data.OpenInDAO
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET


interface CommonApiService {
@GET("/api/v1/dashboardNew")
suspend fun get():Response<OpenInDAO>
}