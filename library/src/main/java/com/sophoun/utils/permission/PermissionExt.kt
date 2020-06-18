package com.sophoun.utils.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

/**
 * This class provide all permission extension functions
 * and helper methods to make it easy
 * for checking and request new permissions
*/

/**
 * Check permission already grant or not.
 */
private fun Context.isPermissionGranted(permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED
}

/**
 * Collect permissions that not yet granted.
 * You can use this method to check permissions first
 * and then show the dialog or another screen before request
 * the actual permissions that need.
 */
fun Context.collectPermissionToRequest(permissions: List<String>): Array<String> {
    val permissionToRequest = arrayListOf<String>()
    permissions.forEach {
        if (!isPermissionGranted(it)) {
            permissionToRequest.add(it)
        }
    }
    return permissionToRequest.toTypedArray()
}

/**
 * All permissions not yet granted
 */
fun Context.isNeedPermission(permissions: List<String>): Boolean {
    return collectPermissionToRequest(permissions).isNotEmpty()
}

/**
 * Request permissions
 *
 * It's will make sure is permission need to be request.
 * If so it will start request.
 */
private fun requestPermissions(
    activity: Activity,
    permissions: List<String>,
    requestPermission: (permissionToRequest: Array<String>) -> Unit
): Boolean {
    val permissionToRequest = activity.collectPermissionToRequest(permissions)
    return if (permissionToRequest.isNotEmpty()) {
        requestPermission.invoke(permissionToRequest)
        true
    } else {
        false
    }
}

/**
 * Provide activity extension function to easy check permission
 */
fun Activity.needPermissions(permissions: List<String>, requestCode: Int): Boolean {
    return requestPermissions(this, permissions) { permissionToRequest ->
        ActivityCompat.requestPermissions(this, permissionToRequest, requestCode)
    }
}

/**
 * Provide fragment extension function to easy check permission
 */
fun Fragment.needPermissions(permissions: List<String>, requestCode: Int): Boolean {
    return requestPermissions(activity!!, permissions) { permissionToRequest ->
        requestPermissions(permissionToRequest, requestCode)
    }
}