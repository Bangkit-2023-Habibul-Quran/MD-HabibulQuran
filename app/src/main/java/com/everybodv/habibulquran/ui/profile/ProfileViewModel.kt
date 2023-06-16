package com.everybodv.habibulquran.ui.profile

import androidx.lifecycle.LiveData
import com.everybodv.habibulquran.utils.Event
import androidx.lifecycle.ViewModel
import com.everybodv.habibulquran.data.QuranRepository
import com.everybodv.habibulquran.data.remote.response.GeneralResponse
import com.everybodv.habibulquran.data.remote.response.UserData

class ProfileViewModel(private val quranRepository: QuranRepository) : ViewModel() {

    val userData: LiveData<UserData?> = quranRepository.userData
    val isLoading: LiveData<Boolean> = quranRepository.isLoading
    val isEnabled: LiveData<Boolean> = quranRepository.isEnabled
    val logMsg: LiveData<Event<String>> = quranRepository.logMsg
    val editData: LiveData<GeneralResponse> = quranRepository.editData
    fun getDetailUser(userId: String): LiveData<UserData?> = quranRepository.getDetailUser(userId)
    fun clearDetailUserData(): LiveData<UserData?> = quranRepository.clearDetailUserData()


    fun editProfile(
        id: String,
        name: String,
        email: String,
        gender: String,
        birthYear: Int,
        birthMonth: Int,
        birthDay: Int
    ) {
        quranRepository.editProfile(id, name, email, gender, birthYear, birthMonth, birthDay)
    }

    fun updateUserData(
        name: String,
        email: String,
        birthDay: Int,
        gender: String,
        birthYear: Int,
        birthMonth: Int,
        image: String
    ) {
        quranRepository.updateUserData(name, email, birthDay, gender, birthYear, birthMonth, image)
    }
}