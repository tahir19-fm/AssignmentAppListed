package com.example.assignmentapplisted.home.util


import com.example.assignmentapplisted.home.data.OpenInDAO
import com.example.assignmentapplisted.network.ApiResult
import com.example.assignmentapplisted.network.CommonApiService
import com.example.assignmentapplisted.network.networkCall
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: CommonApiService) {
    suspend fun getData(): ApiResult<OpenInDAO> {
        return networkCall ( apiService.get() )
    }
}