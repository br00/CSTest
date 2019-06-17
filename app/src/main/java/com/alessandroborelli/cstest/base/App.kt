package com.alessandroborelli.cstest.base

import android.app.Application
import android.content.Context

class App: Application() {

    companion object {
        lateinit var sApplicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        sApplicationContext = applicationContext
    }

}