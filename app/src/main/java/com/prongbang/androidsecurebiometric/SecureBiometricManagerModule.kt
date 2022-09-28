package com.prongbang.androidsecurebiometric

import com.prongbang.securebiometric.BiometricPromptInfoBuilder
import com.prongbang.securebiometric.BiometricPromptInfoBuilderImpl
import com.prongbang.securebiometric.cipher.BiometricCryptography
import com.prongbang.securebiometric.cipher.BiometricKeyStoreCipher
import com.prongbang.securebiometric.cipher.Cryptography
import com.prongbang.securebiometric.cipher.KeyStoreCipher
import com.prongbang.securebiometric.executor.ExecutorCreator
import com.prongbang.securebiometric.executor.MainExecutorCreator
import com.prongbang.securebiometric.key.BiometricCryptographyKey
import com.prongbang.securebiometric.key.CryptographyKey
import com.prongbang.securebiometric.keypair.BiometricKeyStoreManager
import com.prongbang.securebiometric.keypair.KeyStoreManager
import com.prongbang.securebiometric.utility.AndroidBase64Utility
import com.prongbang.securebiometric.utility.Base64Utility
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SecureBiometricManagerModule {

    @Binds
    fun provideBiometricCryptography(cryptography: BiometricCryptography): Cryptography

    @Binds
    fun provideBiometricKeyStoreCipher(keyStoreCipher: BiometricKeyStoreCipher): KeyStoreCipher

    @Binds
    fun provideMainExecutorCreator(executorCreator: MainExecutorCreator): ExecutorCreator

    @Binds
    fun provideBiometricCryptographyKey(cryptographyKey: BiometricCryptographyKey): CryptographyKey

    @Binds
    fun provideBiometricKeyStoreManager(keyStoreManager: BiometricKeyStoreManager): KeyStoreManager

    @Binds
    fun provideBiometricPromptInfoBuilder(biometricPromptInfoBuilder: BiometricPromptInfoBuilderImpl): BiometricPromptInfoBuilder

    @Binds
    fun provideAndroidBase64Utility(base64Utility: AndroidBase64Utility): Base64Utility

}