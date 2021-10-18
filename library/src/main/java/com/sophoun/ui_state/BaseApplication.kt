package com.sophoun.ui.state

import android.app.Application

/**
 * A base application class.
 *
 * This class is initialized with some library like:
 *
 * - Koin (Dependency injection)
 * - LeakCanary (Leak detector)
 * - Logger (Pretty logger)
 *
 * ### Current doesn't use
 */
abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
