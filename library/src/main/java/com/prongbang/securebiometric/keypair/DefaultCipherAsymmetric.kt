package com.prongbang.securebiometric.keypair

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import com.prongbang.securebiometric.exception.GenerateKeyPairException
import com.prongbang.securebiometric.exception.PrivateKeyNotFoundException
import com.prongbang.securebiometric.exception.PublicKeyNotFoundException
import java.security.*

class DefaultCipherAsymmetric : CipherAsymmetric {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getPublicKey(key: String): PublicKey {
        return try {
            getKeyPair(key).public
        } catch (e: Exception) {
            e.printStackTrace()
            throw PublicKeyNotFoundException(message = e.cause?.message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getPrivateKey(key: String): PrivateKey {
        return try {
            getKeyStore()?.getKey(key, null) as? PrivateKey
                ?: let {
                    getKeyPair(key)
                    getKeyStore()?.getKey(key, null) as PrivateKey
                }
        } catch (e: Exception) {
            e.printStackTrace()
            throw PrivateKeyNotFoundException(message = e.cause?.message)
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun getKeyPair(key: String): KeyPair {
        return try {
            val keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA,
                ANDROID_KEY_STORE
            )
            val builder = KeyGenParameterSpec.Builder(
                key, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
            builder.setBlockModes(KeyProperties.BLOCK_MODE_ECB)
            builder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
            builder.setUserAuthenticationRequired(true)
            builder.setInvalidatedByBiometricEnrollment(false)

            keyPairGenerator.initialize(builder.build())
            keyPairGenerator.generateKeyPair()

        } catch (e: Exception) {
            e.printStackTrace()
            throw GenerateKeyPairException(message = e.cause?.message)
        }
    }

    private fun getKeyStore(): KeyStore? {
        val mKeyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        mKeyStore?.load(null)
        return mKeyStore
    }

    companion object {
        const val ANDROID_KEY_STORE = "AndroidKeyStore"
    }
}