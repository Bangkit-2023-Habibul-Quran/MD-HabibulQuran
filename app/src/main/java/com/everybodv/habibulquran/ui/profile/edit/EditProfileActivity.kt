package com.everybodv.habibulquran.ui.profile.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.UserID
import com.everybodv.habibulquran.data.local.UserIdPreferences
import com.everybodv.habibulquran.data.remote.response.UserData
import com.everybodv.habibulquran.databinding.ActivityEditProfileBinding
import com.everybodv.habibulquran.ui.profile.ProfileViewModel
import com.everybodv.habibulquran.ui.utility.DatePickerFragment
import com.everybodv.habibulquran.utils.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditProfileActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var userID: UserID
    private lateinit var userIdPreferences: UserIdPreferences
    private var gender = Const.MALE_GENDER
    private var date: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Edit Profil"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val profileViewModel: ProfileViewModel by viewModels { factory }

        val data = intent.getParcelableExtra<UserData>(Const.EXTRA_PROFILE) as UserData

        val genderDefault = data.jenisKelamin

        if (genderDefault == "Laki-laki") {
            binding.Lakilaki.isChecked = true
        } else if (genderDefault == "Perempuan") {
            binding.Perempuan.isChecked = true
        }

        userIdPreferences = UserIdPreferences(this)
        userID = UserID(userIdPreferences)

        val bDate = data.birthdate?.substring(0, 10)
        date = bDate

        binding.radioGroup2.setOnCheckedChangeListener { group, checkedId ->
            gender = if (R.id.Lakilaki == checkedId) {
                Const.MALE_GENDER
            } else {
                Const.FEMALE_GENDER
            }
        }
        Glide.with(this)
            .load(data.image)
            .into(binding.imageView2)
        binding.etName.setText(data.name)
        binding.etEmail.setText(data.email)
        binding.tvDate.text = bDate
        binding.btnDatePicker.setSafeOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(supportFragmentManager, Const.DATE_PICKER_TAG)
        }

        binding.btnSave.setSafeOnClickListener {
            profileViewModel.isLoading.observe(this) {
                showLoading(binding.progressBar4, it)
            }

            profileViewModel.isEnabled.observe(this) {
                binding.btnSave.isEnabled = it
            }

            val fName = binding.etName.text.toString()
            val fEmail = binding.etEmail.text.toString().trim()


            when {
                fName.isBlank() or fEmail.isBlank() or date.isNullOrBlank() -> {
                    showToast(this, getString(R.string.pls_fill_auth))
                }
                !fEmail.matches(Const.emailPattern) -> {
                    showToast(this, getString(R.string.email_format_wrong))
                }
                else -> {
                    val birthDate = date as String
                    val year = birthDate.substring(0, 4).toInt()
                    val month = birthDate.substring(5, 7).toInt()
                    val day = birthDate.substring(8, 10).toInt()

                    userID.getId().observe(this@EditProfileActivity) { id ->
                        profileViewModel.editProfile(id, fName, fEmail, gender, year, month, day)
                    }

                    profileViewModel.logMsg.observe(this) {
                        it.getContentIfNotHandled()?.let {
                            showToast(this, getString(R.string.success_edit))
                        }
                    }

                    profileViewModel.editData.observe(this) { editData ->
                        if (editData != null) {
                            if (!editData.error) {
                                val image = data.image as String
                                finish()
                                profileViewModel.updateUserData(fName, fEmail, day, gender, year, month, image)
                            } else showToast(this, editData.message)
                        }
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

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        binding.tvDate.text = dateFormat.format(calendar.time)
        date = dateFormat.format(calendar.time)
    }
}