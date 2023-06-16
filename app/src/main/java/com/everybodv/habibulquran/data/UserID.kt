package com.everybodv.habibulquran.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.everybodv.habibulquran.data.local.UserIdPreferences
import kotlinx.coroutines.launch

class UserID(private val userIdPreferences: UserIdPreferences): ViewModel() {
    fun setId(id: String) {
        viewModelScope.launch {
            userIdPreferences.setId(id)
        }
    }

    fun deleteId() {
        viewModelScope.launch {
            userIdPreferences.deleteId()
        }
    }

    fun getId(): LiveData<String> = userIdPreferences.getId().asLiveData()
}