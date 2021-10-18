package com.sophoun.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable

inline fun <reified T : Activity> Context.navigateTo(isRoot: Boolean = false, extras: Map<String, Any> = emptyMap()) {
    val intent = Intent()
    intent.apply {
        applyExtras(extras)
    }
    val callWithFlag = if (isRoot) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
    } else intent
    startActivity(callWithFlag)
}

inline fun <reified T : Activity> Activity.navigateTo(isRoot: Boolean = false, extras: Map<String, Any> = emptyMap()) {
    val intent = Intent()
    intent.apply {
        applyExtras(extras)
    }
    val callWithFlag = if (isRoot) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
    } else intent
    startActivity(callWithFlag)
}

fun Intent.applyExtras(extras: Map<String, Any>) {
    extras.keys.forEach { key ->
        val value: Any? = extras[key]
        when (value) {
            is Int -> putExtra(key, value)
            is Long -> putExtra(key, value)
            is String -> putExtra(key, value)
            is Parcelable -> putExtra(key, value)
            is Serializable -> putExtra(key, value)
            else -> error("can't apply extra $key - unknown type")
        }
    }
}

inline fun <reified T : Activity> Fragment.navigateTo(isRoot: Boolean = false, extras: Map<String, Any> = emptyMap()) {
    activity?.navigateTo<T>(isRoot, extras) ?: error("parent activity is null")
}
