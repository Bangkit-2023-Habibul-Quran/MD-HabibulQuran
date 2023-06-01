package com.everybodv.habibulquran.ui.auth.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.databinding.ActivityRegisterBinding
import com.everybodv.habibulquran.ui.utility.DatePickerFragment
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.setSafeOnClickListener
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {
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

        binding.btnDatePicker.setSafeOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(supportFragmentManager, Const.DATE_PICKER_TAG)
        }
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        binding.tvDate.text = dateFormat.format(calendar.time)
    }
}