package com.everybodv.habibulquran.ui.profile.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.databinding.ActivityEditProfileBinding
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Edit Profil"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSave.setSafeOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}