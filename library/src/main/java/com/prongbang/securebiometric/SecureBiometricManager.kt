package com.prongbang.securebiometric

interface SecureBiometricManager {
    fun isSupported(): Boolean
    fun isAvailable(): Boolean
    fun isUnavailable(): Boolean
    fun authenticate(info: Biometric.PromptInfo, onResult: SecureBiometricPromptManager.Result)
}