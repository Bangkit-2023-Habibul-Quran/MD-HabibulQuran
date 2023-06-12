package com.everybodv.habibulquran.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.everybodv.habibulquran.MainActivity
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.Token
import com.everybodv.habibulquran.data.local.AuthPreferences
import com.everybodv.habibulquran.ui.onboarding.OnBoardingActivity
import com.everybodv.habibulquran.utils.showToast

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var authPreferences: AuthPreferences
    private lateinit var token: Token

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        authPreferences = AuthPreferences(this)
        token = Token(authPreferences)

        Handler(Looper.getMainLooper()).postDelayed({
            token()
        }, 500)
    }

    private fun token() {
        token.getToken().observe(this) { key ->
            if (key != null) {
                if (!key.equals("NotFound")) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, OnBoardingActivity::class.java))
                }
            } else {
                showToast(this, "NotFound")
            }
        }
    }
}