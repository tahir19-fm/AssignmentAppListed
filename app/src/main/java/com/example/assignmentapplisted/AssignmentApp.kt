package com.example.assignmentapplisted

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AssignmentApp:Application() {
    companion object {
        @JvmStatic
        var instance: AssignmentApp? = null
    }

    override fun onCreate() {
        Log.d("builddnjksfb", "onCreate:${BuildConfig.DEBUG} ")
        instance = this
        super.onCreate()
    }
}