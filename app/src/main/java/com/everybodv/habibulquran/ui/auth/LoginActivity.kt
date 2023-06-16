package com.everybodv.habibulquran.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.everybodv.habibulquran.MainActivity
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.Token
import com.everybodv.habibulquran.data.UserID
import com.everybodv.habibulquran.data.local.AuthPreferences
import com.everybodv.habibulquran.data.local.UserIdPreferences
import com.everybodv.habibulquran.databinding.ActivityLoginBinding
import com.everybodv.habibulquran.ui.auth.forgot.ForgotPasswordActivity
import com.everybodv.habibulquran.ui.auth.register.RegisterActivity
import com.everybodv.habibulquran.ui.auth.verification.VerificationActivity
import com.everybodv.habibulquran.utils.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var token: Token
    private lateinit var userID: UserID
    private lateinit var authPreferences: AuthPreferences
    private lateinit var userIdPreferences: UserIdPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        authPreferences = AuthPreferences(this)
        userIdPreferences = UserIdPreferences(this)
        token = Token(authPreferences)
        userID = UserID(userIdPreferences)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val authViewModel : AuthViewModel by viewModels { factory }

        binding.tvVerif.setSafeOnClickListener {
            val intent = Intent(this, VerificationActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setSafeOnClickListener {
            val email = binding.customEmail.text.toString().trim()
            val password = binding.customPassword.text.toString().trim()
            authViewModel.isLoading.observe(this) {
                showLoading(binding.progressBar, it)
            }
            authViewModel.isEnabled.observe(this) {
                binding.btnLogin.isEnabled = it
            }
            when {
                password.isBlank() or email.isBlank() -> {
                    showToast(this, getString(R.string.pls_fill_auth))
                }
                !email.matches(Const.emailPattern) -> {
                    showToast(this, getString(R.string.email_format_wrong))
                }
                else -> {
                    authViewModel.login(email, password)
                    authViewModel.logMsg.observe(this) {
                        it.getContentIfNotHandled()?.let {
                            showToast(this, getString(R.string.auth_not_matched))
                        }
                    }
                    authViewModel.loginData.observe(this) { loginData ->
                        token.setToken(loginData.token)
                        userID.setId(loginData.id)
                        startActivity(Intent(this, MainActivity::class.java))
                        showToast(this, "${getString(R.string.login_success)} ${loginData.name}")
                    }
                }
            }
        }

        binding.tvRegister.setSafeOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvForgot.setSafeOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}