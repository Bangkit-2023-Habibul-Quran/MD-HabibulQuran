package com.everybodv.habibulquran.ui.quran

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everybodv.habibulquran.data.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuranViewModel @Inject constructor(
    private val quranRepository: QuranRepository
) : ViewModel() {

    val listSurah = quranRepository.listSurah
    val listHijaiyah = quranRepository.listHijaiyah
    val isLoading = quranRepository.isLoading

    fun getAllSurah() = quranRepository.getAllSurah()
    fun getAllHijaiyah() = quranRepository.getAllHijaiyah()
}