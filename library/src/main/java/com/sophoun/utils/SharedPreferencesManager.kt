package com.sophoun.utils

import android.content.Context

open class SharedPreferencesManager(
    context: Context,
    preferenceName: String
) {
    val _sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)

    fun <T> put(key: String, value: T) {
        val edit = _sharedPreferences.edit()
        when (value) {
            is Boolean -> edit.putBoolean(key, value as Boolean)
            is Float -> edit.putFloat(key, value as Float)
            is Int -> edit.putInt(key, value as Int)
            is Long -> edit.putLong(key, value as Long)
            is String -> edit.putString(key, value as String)
        }
        edit.apply()
    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    inline fun <reified T> get(key: String): T? {
        val value = when (T::class) {
            Boolean::class -> _sharedPreferences.getBoolean(key, false)
            Float::class -> _sharedPreferences.getFloat(key, 0f)
            Int::class -> _sharedPreferences.getInt(key, 0)
            Long::class -> _sharedPreferences.getLong(key, 0)
            String::class -> _sharedPreferences.getString(key, "")
            else -> null
        }
        return try {
            value as T
        } catch (e: Exception) {
            DLog.e("$key, $e")
            null
        }
    }
}