package com.everybodv.habibulquran.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.everybodv.habibulquran.data.QuranRepository
import com.everybodv.habibulquran.data.remote.response.GeneralResponse
import com.everybodv.habibulquran.data.remote.response.User
import com.everybodv.habibulquran.utils.Event
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AuthViewModel(private val quranRepository: QuranRepository): ViewModel() {

    val isEnabled: LiveData<Boolean> = quranRepository.isEnabled
    val isLoading: LiveData<Boolean> = quranRepository.isLoading
    val logMsg: LiveData<Event<String>> = quranRepository.logMsg
    val registerData: LiveData<GeneralResponse> = quranRepository.registerData
    val forgotData: LiveData<GeneralResponse> = quranRepository.forgotData
    val loginData: LiveData<User> = quranRepository.loginData
    val verifyData: LiveData<GeneralResponse> = quranRepository.verifyResponse

    fun register(
        name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        image: MultipartBody.Part,
        gender: RequestBody,
        birthYear: Int,
        birthMonth: Int,
        birthDay: Int
    ) {
        quranRepository.register(name, email, password, image, gender, birthYear, birthMonth, birthDay)
    }
    fun login(email: String, password: String) = quranRepository.login(email, password)
    fun forgotResetPass(email: String) = quranRepository.forgotResetPass(email)
    fun verifyEmail(email: String) = quranRepository.verifyEmail(email)

}