package com.prongbang.securebiometric.cipher

import android.os.Build
import androidx.annotation.RequiresApi
import com.prongbang.securebiometric.exception.DecryptException
import com.prongbang.securebiometric.exception.EncryptException
import com.prongbang.securebiometric.utility.Base64Utility
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class DefaultBiometricCryptography constructor(
    private val biometricCipher: BiometricCipher,
    private val base64Utility: Base64Utility,
) : BiometricCryptography {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun encrypt(plaintext: String): String {
        return try {
            val cipher = biometricCipher.getEncryptCipher()
            val keyGenerator: KeyGenerator =
                KeyGenerator.getInstance(BiometricCryptography.ALGORITHM_CONFIG)
            keyGenerator.init(256)
            val secretKey: SecretKey = keyGenerator.generateKey()
            val encryptedSecretKey = cipher.doFinal(secretKey.encoded) ?: byteArrayOf()
            val sCipher = biometricCipher.getSecureEncryptCipher(secretKey)
            val cipherText = sCipher.doFinal(plaintext.toByteArray())
            val encryptedSecretKeyBase64 = base64Utility.encode(encryptedSecretKey)
            val encryptedIvBase64 = base64Utility.encode(sCipher.iv)
            val cipherTextBase64 = base64Utility.encode(cipherText)

            "$encryptedSecretKeyBase64${BiometricCryptography.SEPARATE_TEXT}$encryptedIvBase64${BiometricCryptography.SEPARATE_TEXT}$cipherTextBase64"
        } catch (e: Exception) {
            e.printStackTrace()
            throw EncryptException(message = e.cause?.message)
        }
    }

    override fun decrypt(cipherText: String): String {
        return try {
            val cipher = biometricCipher.getDecryptCipher()
            decrypt(cipher, cipherText)

        } catch (e: Exception) {
            throw DecryptException(message = e.cause?.message)
        }
    }

    override fun decrypt(cipher: Cipher, cipherText: String): String {
        return try {
            val cipherSplits = cipherText.split(BiometricCryptography.SEPARATE_TEXT)
            val encryptedSecretKey = base64Utility.decode(cipherSplits[0])
            val iv = base64Utility.decode(cipherSplits[1])
            val rawCipherText = base64Utility.decode(cipherSplits[2])

            val secretKey = cipher.doFinal(encryptedSecretKey)
            val sCipher = biometricCipher.getSecureDecryptCipher(iv, secretKey)
            val ivSpec = IvParameterSpec(iv)
            val secretSpec: SecretKey = SecretKeySpec(
                secretKey, 0, secretKey.size,
                BiometricCryptography.ALGORITHM_CONFIG
            )
            sCipher.init(Cipher.DECRYPT_MODE, secretSpec, ivSpec)
            String(sCipher.doFinal(rawCipherText))
        } catch (e: Exception) {
            throw DecryptException(message = e.cause?.message)
        }
    }

}