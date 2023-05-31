package com.everybodv.habibulquran.ui.auth.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.databinding.ActivityRegisterBinding
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnRegister.setSafeOnClickListener {
            finish()
        }

        binding.tvToLogin.setSafeOnClickListener {
            finish()
        }
    }
}