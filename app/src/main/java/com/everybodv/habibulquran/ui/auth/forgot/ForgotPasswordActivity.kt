package com.everybodv.habibulquran.ui.auth.forgot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.databinding.ActivityForgotPasswordBinding
import com.everybodv.habibulquran.utils.setSafeOnClickListener
import com.everybodv.habibulquran.utils.showToast

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

        binding.btnSend.setSafeOnClickListener {
            showToast(this, getString(R.string.email_reset_send))
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}