package com.everybodv.habibulquran.ui.auth.forgot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.databinding.ActivityForgotPasswordBinding
import com.everybodv.habibulquran.ui.auth.AuthViewModel
import com.everybodv.habibulquran.utils.*

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.reset_password)
        }

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val authViewModel : AuthViewModel by viewModels { factory }

        binding.btnSend.setSafeOnClickListener {
            val email = binding.customEmailReset.text.toString().trim()
            authViewModel.isLoading.observe(this) {
                showLoading(binding.progressBarReset, it)
            }
            authViewModel.isEnabled.observe(this) {
                binding.btnSend.isEnabled = it
            }
            if (!email.matches(Const.emailPattern)) {
                showToast(this, getString(R.string.email_format_wrong))
            } else {
                authViewModel.forgotResetPass(email)
                authViewModel.logMsg.observe(this) {
                    it.getContentIfNotHandled()?.let {
                        showToast(this, getString(R.string.email_not_registered))
                    }
                }

                authViewModel.forgotData.observe(this) {
                    if (!it.error) {
                        finish()
                        showToast(this, getString(R.string.email_reset_send))
                    }
                }

            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}