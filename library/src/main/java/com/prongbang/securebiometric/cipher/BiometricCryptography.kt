package com.prongbang.securebiometric.cipher

import javax.crypto.Cipher

interface BiometricCryptography {
    fun encrypt(plaintext: String): String
    fun decrypt(cipherText: String): String
    fun decrypt(cipher: Cipher, cipherText: String): String

    companion object {
        const val ALGORITHM_CONFIG = "AES"
        const val CIPHER_SYMMETRIC_CONFIG = "AES/GCM/NoPadding"
        const val CIPHER_ASYMMETRIC_CONFIG = "RSA/ECB/PKCS1Padding"
        const val SEPARATE_TEXT = ":"
    }
}