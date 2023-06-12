package com.everybodv.habibulquran.ui.auth.verification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.databinding.ActivityVerificationBinding
import com.everybodv.habibulquran.ui.auth.AuthViewModel
import com.everybodv.habibulquran.utils.*

class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.verification)
            setDisplayHomeAsUpEnabled(true)
        }

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val authViewModel : AuthViewModel by viewModels { factory }

        binding.btnVerif.setSafeOnClickListener {
            val email = binding.customEmailReset.text.toString().trim()
            authViewModel.isLoading.observe(this) {
                showLoading(binding.progressBarVerif, it)
            }
            authViewModel.isEnabled.observe(this) {
                binding.btnVerif.isEnabled = it
            }
            if (!email.matches(Const.emailPattern)) {
                showToast(this, getString(R.string.email_format_wrong))
            } else {
                authViewModel.verifyEmail(email)
                authViewModel.logMsg.observe(this) {
                    it.getContentIfNotHandled()?.let {
                        showToast(this, getString(R.string.email_not_registered))
                    }
                }
                authViewModel.verifyData.observe(this) {
                    if (!it.error) {
                        finish()
                        showToast(this, getString(R.string.verification_sent))
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