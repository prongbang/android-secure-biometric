package com.prongbang.securebiometric.key

class BiometricCryptographyKey : CryptographyKey{
    override fun key(): String = "SECURE_BIOMETRIC"
}