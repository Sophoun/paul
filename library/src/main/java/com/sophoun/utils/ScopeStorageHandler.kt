package com.sophoun.utils

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.IntentSender

/**
 * A Utility class that help you easy to access android scope storage.
 * It's require android Q or above.
 * This class is require when you want to update or delete a file or group of files
 * using [ContentResolver] so [ContentResolver] will throw [RecoverableSecurityException]
 * if you doesn't have permission on those files.
 * So after [ContentResolver] throw exception, you can use this call to request those permission
 * and after user allow or deny it wills callback to [onResult] method.
 * Note:
 * - To use this class you need to create an instance of it once and use in your activity.
 * - You must call [handleRequestIntent] in [onActivityResult] to handle callback result.
 */
class ScopeStorageHandler(private val activity: Activity) {

    private var requestCode: Int = -1
    private lateinit var onResult: (success: Boolean) -> Unit

    /**
     * Request permission for android scope storage
     */
    fun sendRequestIntent(intentSender: IntentSender, requestCode: Int, onResult: (success: Boolean) -> Unit) {
        // set request code
        this.requestCode = requestCode
        // set callback result
        this.onResult = onResult
        // start request permission
        activity.startIntentSenderForResult(intentSender, requestCode, null, 0,0, 0, null)
    }

    /**
     * Call this method inside [onActivityResult]
     * to handle callback result of [sendRequestIntent]
     */
    fun handleRequestIntent(requestCode: Int, resultCode: Int) {
        if (!::onResult.isInitialized) return
        if (requestCode == this.requestCode && resultCode == RESULT_OK) {
            onResult(true)
        } else {
            onResult(false)
        }
    }
}