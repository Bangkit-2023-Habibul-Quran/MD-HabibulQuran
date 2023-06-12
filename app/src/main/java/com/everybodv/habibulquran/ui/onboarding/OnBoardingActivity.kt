package com.everybodv.habibulquran.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.databinding.ActivityOnBoardingBinding
import com.everybodv.habibulquran.ui.auth.LoginActivity
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnStart.setSafeOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}