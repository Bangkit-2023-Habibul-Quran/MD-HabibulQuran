package com.everybodv.habibulquran.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.everybodv.habibulquran.MainActivity
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.databinding.ActivityLoginBinding
import com.everybodv.habibulquran.ui.auth.forgot.ForgotPasswordActivity
import com.everybodv.habibulquran.ui.auth.register.RegisterActivity
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnLogin.setSafeOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.tvRegister.setSafeOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvForgot.setSafeOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}