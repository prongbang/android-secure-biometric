package com.prongbang.securebiometric.cipher

import android.os.Build
import androidx.annotation.RequiresApi
import com.prongbang.securebiometric.keypair.CipherAsymmetric
import com.prongbang.securebiometric.key.CryptographyKey
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class DefaultBiometricCipher(
    private val cryptographyKey: CryptographyKey,
    private val cipherAsymmetric: CipherAsymmetric
) : BiometricCipher {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getEncryptCipher(): Cipher {
        val cipher = Cipher.getInstance(BiometricCryptography.CIPHER_ASYMMETRIC_CONFIG)
        cipher?.init(
            Cipher.ENCRYPT_MODE,
            cipherAsymmetric.getPublicKey(cryptographyKey.key())
        )
        return cipher
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getSecureEncryptCipher(secretKey: SecretKey): Cipher {
        val sCipher = Cipher.getInstance(BiometricCryptography.CIPHER_SYMMETRIC_CONFIG)
        sCipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return sCipher
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getDecryptCipher(): Cipher {
        val cipher = Cipher.getInstance(BiometricCryptography.CIPHER_ASYMMETRIC_CONFIG)
        cipher?.init(Cipher.DECRYPT_MODE, cipherAsymmetric.getPrivateKey(cryptographyKey.key()))
        return cipher
    }

    override fun getSecureDecryptCipher(iv: ByteArray, secretKey: ByteArray): Cipher {
        val sCipher = Cipher.getInstance(
            BiometricCryptography.CIPHER_SYMMETRIC_CONFIG
        )
        val ivSpec = IvParameterSpec(iv)
        val secretSpec: SecretKey = SecretKeySpec(
            secretKey,
            0,
            secretKey.size,
            BiometricCryptography.ALGORITHM_CONFIG
        )
        sCipher.init(Cipher.DECRYPT_MODE, secretSpec, ivSpec)

        return sCipher
    }

}