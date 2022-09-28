package com.prongbang.securebiometric

data class Biometric(
    val decrypted: String = "",
    val status: Status
) {
    data class PromptInfo(
        val title: String = "",
        val subtitle: String = "",
        val description: String = "",
        val negativeButton: String = "",
    )
    enum class Status {
        SUCCEEDED,
        ERROR,
        CANCEL
    }
}