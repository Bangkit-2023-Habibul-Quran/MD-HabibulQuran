package com.everybodv.habibulquran.ui.auth.register

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.databinding.ActivityRegisterBinding
import com.everybodv.habibulquran.ui.auth.AuthViewModel
import com.everybodv.habibulquran.ui.utility.DatePickerFragment
import com.everybodv.habibulquran.utils.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {
    private lateinit var binding: ActivityRegisterBinding

    private var photoFile: File? = null
    private var date: String? = null
    private var gender: String = Const.MALE_GENDER

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImage: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImage, this)
            photoFile = myFile
            showContent(binding.ivPp)
            binding.ivPp.setImageURI(selectedImage)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val authViewModel : AuthViewModel by viewModels { factory }

        binding.radioGroup2.setOnCheckedChangeListener { group, checkedId ->
            gender = if (R.id.rb_male == checkedId) {
                Const.MALE_GENDER
            } else {
                Const.FEMALE_GENDER
            }
        }

        binding.btnRegister.setSafeOnClickListener {
            authViewModel.isLoading.observe(this) {
                showLoading(binding.progressBarReg, it)
            }

            authViewModel.isEnabled.observe(this) {
                binding.btnRegister.isEnabled = it
            }

            val fName = binding.etUsername.text.toString()
            val fEmail = binding.customEmail.text.toString().trim()
            val fPassword = binding.customPassword.text.toString().trim()

            when {
                fPassword.isBlank() or fEmail.isBlank() or fName.isBlank() or date.isNullOrEmpty() -> {
                    showToast(this, getString(R.string.pls_fill_auth))
                }
                fPassword.length < 8 -> {
                    showToast(this, getString(R.string.pass_not_valid))
                }
                !fEmail.matches(Const.emailPattern) -> {
                    showToast(this, getString(R.string.email_format_wrong))
                }
                else -> {
                    if (photoFile != null) {
                        val file = reduceFileImage(photoFile as File)
                        val reqImage = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

                        val name = fName.toRequestBody("text/plain".toMediaType())
                        val email = fEmail.toRequestBody("text/plain".toMediaType())
                        val password = fPassword.toRequestBody("text/plain".toMediaType())
                        val image: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, reqImage)
                        val genderReq = gender.toRequestBody("text/plain".toMediaType())
                        val birthDate = date as String
                        val year = birthDate.substring(0, 4).toInt()
                        val month = birthDate.substring(5, 7).toInt()
                        val day = birthDate.substring(8, 10).toInt()

                        authViewModel.register(name, email, password, image, genderReq, year, month, day)
                        authViewModel.logMsg.observe(this) {
                            it.getContentIfNotHandled()?.let {
                                showToast(this, "Terjadi Kesalahan")
                            }
                        }
                        authViewModel.registerData.observe(this) { registerData ->
                            if (!registerData.error) {
                                finish()
                                showToast(this, getString(R.string.register_success))
                            } else showToast(this, "Terjadi Kesalahan")
                        }
                    } else showToast(this, getString(R.string.pp_not_valid))
                }
            }
        }

        binding.tvToLogin.setSafeOnClickListener {
            finish()
        }

        binding.btnSelectPhoto.setSafeOnClickListener { gallery() }

        binding.btnDatePicker.setSafeOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(supportFragmentManager, Const.DATE_PICKER_TAG)
        }
    }

    private fun gallery() {
        val galleryIntent = Intent()
        galleryIntent.action = ACTION_GET_CONTENT
        galleryIntent.type = "image/*"
        val chooser = Intent.createChooser(galleryIntent, getString(R.string.select_photo))
        launcherIntentGallery.launch(chooser)
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        binding.tvDate.text = dateFormat.format(calendar.time)
        date = dateFormat.format(calendar.time)
    }
}