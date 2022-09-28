package com.prongbang.securebiometric.utility

import android.util.Base64

class AndroidBase64Utility : Base64Utility {

    override fun encode(input: ByteArray): String {
        return Base64.encodeToString(input, Base64.DEFAULT)
    }

    override fun decode(input: String): ByteArray {
        return Base64.decode(input, Base64.DEFAULT)
    }
}