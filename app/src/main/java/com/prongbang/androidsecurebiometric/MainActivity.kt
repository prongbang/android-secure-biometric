package com.prongbang.androidsecurebiometric

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prongbang.androidsecurebiometric.databinding.ActivityMainBinding
import com.prongbang.securebiometric.Biometric
import com.prongbang.securebiometric.SecureBiometricManager
import com.prongbang.securebiometric.SecureBiometricPromptManager
import com.prongbang.securebiometric.cipher.BiometricCryptography
import com.prongbang.securebiometric.cipher.Cryptography
import com.prongbang.securebiometric.token.BiometricToken
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @Inject
    lateinit var secureBiometricManager: SecureBiometricManager

    @Inject
    lateinit var cryptography: Cryptography

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {

        with(binding) {

            createBiometricToken.setOnClickListener {
                // Mock biometric token
                val biometricToken = "MOCK_BIOMETRIC_TOKEN_FROM_SERVER"

                // Encrypt
                val cipherBiometricToken = cryptography.encrypt(biometricToken)

                // Save cipher biometric token to local storage
                MockBiometricToken.token = cipherBiometricToken

                resultCipherBiometricTokenText.text = cipherBiometricToken
            }

            authenticationBiometric.setOnClickListener {

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
                                    resultBiometricTokenText.text =
                                        "SUCCESS: ${biometric.decrypted}"

                                    // TODO Send biometric.decrypted to Server
                                    // val result = loginWithBiometricToken(biometric.decrypted)
                                }
                                Biometric.Status.ERROR -> {
                                    resultBiometricTokenText.text = "ERROR"
                                }
                                Biometric.Status.CANCEL -> {
                                    resultBiometricTokenText.text = "CANCEL"
                                }
                            }
                        }
                    })
            }
        }
    }
}