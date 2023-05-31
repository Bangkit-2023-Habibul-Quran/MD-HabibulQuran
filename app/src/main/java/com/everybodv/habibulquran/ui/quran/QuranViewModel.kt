package com.everybodv.habibulquran.ui.quran

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuranViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is quran Fragment"
    }
    val text: LiveData<String> = _text
}