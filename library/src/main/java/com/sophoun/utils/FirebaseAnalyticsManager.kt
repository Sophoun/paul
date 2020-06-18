package com.sophoun.utils

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import java.io.Serializable

class FirebaseAnalyticsManager(private val context: Context) {

    private val mFirebaseAnalytics: FirebaseAnalytics get() {
        return FirebaseAnalytics.getInstance(context)
    }

    /**
     * Send an event
     * @event Event name
     * @properties Properties for event
     */
    fun logEvent(event: String, properties: Map<String, Any>) {
        val bundle = Bundle().apply {
            properties.forEach {
                when (val value: Any = it.value) {
                    is Int -> putInt(it.key, value)
                    is Long -> putLong(it.key, value)
                    is String -> putString(it.key, value)
                    is Parcelable -> putParcelable(it.key, value)
                    is Serializable -> putSerializable(it.key, value)
                    else -> Log.e("error::", "unknown key: $it.key and value: $value")
                }
            }
        }

        mFirebaseAnalytics.logEvent(event, bundle)
    }

    /**
     * Send user property
     * @key Key of property
     * @value Value of property
     */
    fun setUserProperty(key: String, value: String) {
        mFirebaseAnalytics.setUserProperty(key, value)
    }

    /**
     * Send current screen view
     * @activity current activity
     * @screenName viewing screen
     * @clazz viewing screen class
     */
    fun setCurrentScreen(activity: Activity?, screenName: String, clazz: Class<*>? = null) {
        if (activity == null) return
        mFirebaseAnalytics.setCurrentScreen(activity, screenName, clazz?.simpleName)
    }

    /**
     * Set `user id` for firebase analytic
     * @userId [user id] [please follow the firebase document rule]
     */
    fun setUserId(userId: String) {
        mFirebaseAnalytics.setUserId(userId)
    }
}