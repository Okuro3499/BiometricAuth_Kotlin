package com.extrainch.biometricauth

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt.PromptInfo
import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.extrainch.biometricauth.databinding.ActivityMainBinding
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    private var executor: Executor? = null
    private var biometricPrompt: BiometricPrompt? = null
    private var promptInfo: PromptInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(
            this@MainActivity,
            executor!!,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    binding!!.AuthStatus.text = "Authentication error:$errString"
                    //                Toast.makeText(MainActivity.this, "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    binding!!.AuthStatus.text = "Authentication Successful"
                    //                Toast.makeText(MainActivity.this, "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    binding!!.AuthStatus.text = "Authentication Failed"
                    //                Toast.makeText(MainActivity.this, "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
                }
            })
        promptInfo = PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint")
            .setNegativeButtonText("Use Password")
            .build()
        binding!!.FingerPrint.setOnClickListener { v: View? ->
            biometricPrompt!!.authenticate(
                promptInfo!!
            )
        }
    }
}