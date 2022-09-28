package com.prongbang.androidsecurebiometric

import com.prongbang.securebiometric.token.BiometricToken
import javax.inject.Inject

class MockBiometricToken @Inject constructor() : BiometricToken {

    override fun cipherText(): String = token

    companion object {
        var token = ""
    }
}