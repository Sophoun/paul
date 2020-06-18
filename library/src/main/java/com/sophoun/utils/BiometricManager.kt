package com.sophoun.utils

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.AuthenticationCallback
import androidx.biometric.BiometricPrompt.AuthenticationResult
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.lang.Exception

class BiometricManager {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo
    private lateinit var activity: FragmentActivity
    private lateinit var fragment: Fragment

    constructor(fragmentActivity: FragmentActivity) {
        this.activity = fragmentActivity
    }

    constructor(fragment: Fragment) {
        this.fragment = fragment
    }

    private fun getContext(): Context {
        if (::activity.isInitialized)
            return activity.applicationContext
        if (::fragment.isInitialized) {
            return fragment.requireContext()
        }
        throw Exception("No context provided.")
    }

    fun isAvailable(): Boolean {
        var returnFlag = false
        val biometricManager = BiometricManager.from(getContext())
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                DLog.i("BiometricManager: BIOMETRIC_ERROR_HW_UNAVAILABLE")
                returnFlag = false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                DLog.i("BiometricManager: BIOMETRIC_ERROR_NONE_ENROLLED")
                returnFlag = false
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                DLog.i("BiometricManager: BIOMETRIC_ERROR_NO_HARDWARE")
                returnFlag = false
            }
            BiometricManager.BIOMETRIC_SUCCESS -> {
                DLog.i("BiometricManager: BIOMETRIC_SUCCESS")
                returnFlag = true
            }
        }
        return returnFlag
    }

    fun setBiometricCallback(
        onAuthenticationError: (errorCode: Int, errString: CharSequence) -> Unit,
        onAuthenticationFailed: () -> Unit,
        onAuthenticationSucceeded: (result: AuthenticationResult) -> Unit
    ) {
        val authenticationCallback = object: AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                DLog.i("BiometricManager: onAuthenticationError")
                onAuthenticationError.invoke(errorCode, errString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                DLog.i("BiometricManager: onAuthenticationFailed")
                onAuthenticationFailed.invoke()
            }

            override fun onAuthenticationSucceeded(result: AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                DLog.i("BiometricManager: onAuthenticationSucceeded")
                onAuthenticationSucceeded.invoke(result)
            }
        }

        biometricPrompt = if (::fragment.isInitialized) {
            BiometricPrompt(fragment, ContextCompat.getMainExecutor(getContext()), authenticationCallback)
        } else if (::activity.isInitialized) {
            BiometricPrompt(fragment, ContextCompat.getMainExecutor(getContext()), authenticationCallback)
        } else {
            throw Exception("Activity or Fragment isn't initialized yet.")
        }
    }

    fun setPromptInfo(
        title: String,
        description: String,
        subtitle: String = "",
        credentialAllowed: Boolean = false,
        negativeButtonText: String = "Cancel",
        confirmationRequired: Boolean = false
    ) {
        val promptInfoBuilder = PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setDeviceCredentialAllowed(credentialAllowed)
            .setConfirmationRequired(confirmationRequired)

        if (subtitle.isNotEmpty()) {
            promptInfoBuilder.setSubtitle(subtitle)
        }

        if (!credentialAllowed) {
            promptInfoBuilder.setNegativeButtonText(negativeButtonText)
        }

        this.promptInfo = promptInfoBuilder.build()
    }

    fun authenticate() {
        if (!::promptInfo.isInitialized) {
            throw Exception("PromptInfo is not initialized yet.")
        }
        if (!::biometricPrompt.isInitialized) {
            throw Exception("BiometricPrompt is not initialized yet.")
        }

        biometricPrompt.authenticate(promptInfo)
    }
}