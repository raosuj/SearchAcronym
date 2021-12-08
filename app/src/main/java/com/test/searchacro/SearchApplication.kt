package com.test.searchacro

import android.app.Application

class SearchApplication: Application() {

    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}