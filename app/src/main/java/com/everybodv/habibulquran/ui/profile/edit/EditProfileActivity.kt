package com.everybodv.habibulquran.ui.profile.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.databinding.ActivityEditProfileBinding
import com.everybodv.habibulquran.ui.utility.DatePickerFragment
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.setSafeOnClickListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditProfileActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

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

        binding.btnDatePicker.setSafeOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(supportFragmentManager, Const.DATE_PICKER_TAG)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        binding.tvDate.text = dateFormat.format(calendar.time)
    }
}