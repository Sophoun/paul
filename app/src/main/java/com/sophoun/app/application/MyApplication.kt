package com.sophoun.app.application

import android.app.Application
import com.sophoun.utils.DLog
import com.sophoun.utils.DLog.Level.DEBUG


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DLog.level(DEBUG)
        DLog.d("Application class onCreate()")
    }
}