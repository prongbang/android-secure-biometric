package com.prongbang.securebiometric.cipher

import javax.crypto.Cipher
import javax.crypto.SecretKey

interface BiometricCipher {
    fun getDecryptCipher(): Cipher
    fun getEncryptCipher(): Cipher
    fun getSecureEncryptCipher(secretKey: SecretKey): Cipher
    fun getSecureDecryptCipher(iv: ByteArray, secretKey: ByteArray): Cipher
}