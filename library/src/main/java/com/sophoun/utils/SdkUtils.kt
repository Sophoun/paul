package com.sophoun.utils

import android.os.Build

object SdkUtils {

    fun targetSdk(targetVersion: Int, above: () -> Unit, below: () -> Unit) {
        if (Build.VERSION.SDK_INT >= targetVersion) {
            above()
        } else {
            below()
        }
    }
}