package com.prongbang.androidsecurebiometric

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.prongbang.androidsecurebiometric.extensions.activity
import com.prongbang.securebiometric.BiometricPromptInfoBuilder
import com.prongbang.securebiometric.SecureBiometricManager
import com.prongbang.securebiometric.SecureBiometricPromptManager
import com.prongbang.securebiometric.cipher.Cryptography
import com.prongbang.securebiometric.cipher.KeyStoreCipher
import com.prongbang.securebiometric.executor.ExecutorCreator
import com.prongbang.securebiometric.token.BiometricToken
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object MainModule {

    @Provides
    fun provideMockBiometricToken(biometricToken: MockBiometricToken): BiometricToken =
        biometricToken

    @Provides
    fun provideSecureBiometricPromptManager(
        activity: FragmentActivity,
        cryptography: Cryptography,
        biometricToken: BiometricToken,
        executorCreator: ExecutorCreator,
        keyStoreCipher: KeyStoreCipher,
        biometricPromptInfoBuilder: BiometricPromptInfoBuilder,
    ): SecureBiometricManager =
        SecureBiometricPromptManager(
            activity = activity,
            cryptography = cryptography,
            biometricToken = biometricToken,
            executorCreator = executorCreator,
            keyStoreCipher = keyStoreCipher,
            biometricPromptInfoBuilder = biometricPromptInfoBuilder,
        )
}