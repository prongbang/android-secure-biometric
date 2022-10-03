# android-secure-biometric

Library for using BiometricPrompt with CryptoObject on Android.

[![](https://jitpack.io/v/prongbang/android-secure-biometric.svg)](https://jitpack.io/#prongbang/android-secure-biometric)

## Preview

![img.png](screenshot/img.png)

## Setup

- `build.gradle`

```groovy
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

- `settings.gradle`

```groovy
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

- `app/build.gradle`

```groovy
implementation 'com.github.prongbang:android-secure-biometric:0.0.3'
```

## How to use

- Create class `PinBiometricToken`

```kotlin
class PinBiometricToken @Inject constructor() : BiometricToken {

    override fun cipherText(): String = token

    // Mock storage 
    companion object {
        var token = ""
    }
}
```

- New Instance

```kotlin
val pinBiometricToken = PinBiometricToken()
val secureBiometricManager = SecureBiometricPromptManager.newInstance(this, pinBiometricToken)
val biometricCryptography = BiometricCryptography.newInstance()
```

- Encrypt

```kotlin
// Mock biometric token from server
val biometricToken = "MOCK_BIOMETRIC_TOKEN_FROM_SERVER"

// Encrypt
val cipherBiometricToken = biometricCryptography.encrypt(biometricToken)

// Save cipher biometric token to local storage
PinBiometricToken.token = cipherBiometricToken

print("Cipher Text: $cipherBiometricToken")
```

- Decrypt with Biometric

```kotlin
// Authenticate with Biometric
val promptInfo = Biometric.PromptInfo(
    title = "BIOMETRIC",
    subtitle = "Please scan biometric to Login Application",
    description = "description here",
    negativeButton = "CANCEL"
)

secureBiometricManager.authenticate(
    promptInfo,
    object : SecureBiometricPromptManager.Result {
        override fun callback(biometric: Biometric) {
            when (biometric.status) {
                Biometric.Status.SUCCEEDED -> {
                    // TODO Send biometric.decrypted to Server
                    // val result = loginWithBiometricToken(biometric.decrypted)
                }
                Biometric.Status.ERROR -> {}
                Biometric.Status.CANCEL -> {}
            }
        }
    })
```