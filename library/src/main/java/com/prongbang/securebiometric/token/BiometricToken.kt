package com.prongbang.securebiometric.token

interface BiometricToken {
    fun cipherText(): String
}