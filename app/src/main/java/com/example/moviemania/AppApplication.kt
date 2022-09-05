package com.example.moviemania

import android.app.Application

class AppApplication: Application() {

    companion object {
        private lateinit var INSTANCE: AppApplication
        @JvmStatic
        fun getInstance() = INSTANCE
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}