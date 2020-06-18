package com.sophoun.utils.validation

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.core.util.PatternsCompat

@SuppressLint("RestrictedApi")
object Validators {

    fun isValidEmail(email: CharSequence?): Boolean {
        return !TextUtils.isEmpty(email) && PatternsCompat.AUTOLINK_EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidUrl(url: CharSequence?): Boolean {
        return !TextUtils.isEmpty(url) && PatternsCompat.AUTOLINK_WEB_URL.matcher(url).matches()
    }

    fun isValidIpAddress(ipAddress: CharSequence?): Boolean {
        return !TextUtils.isEmpty(ipAddress) && PatternsCompat.IP_ADDRESS.matcher(ipAddress).matches()
    }
}