package com.everybodv.habibulquran.ui.quran

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.everybodv.habibulquran.data.QuranRepository
import com.everybodv.habibulquran.data.remote.response.Data
import com.everybodv.habibulquran.data.remote.response.VersesItem

class QuranViewModel(private val quranRepository: QuranRepository) : ViewModel() {

//    val listSurah: LiveData<List<Data>> = quranRepository.listSurah
    val isLoading: LiveData<Boolean> = quranRepository.isLoading

    val surahList: LiveData<List<Data>> = quranRepository.surahList
    val listAyat: LiveData<List<VersesItem>> = quranRepository.listAyat
    val listSurahTest: LiveData<List<Data>> = quranRepository.listSurahTest

//    fun getAllSurah(): LiveData<List<Data>> = quranRepository.getAllSurah()
    fun getListSurah(): LiveData<List<Data>> = quranRepository.getListSurah()
    fun getAyatBySurahId(verseNumber: Int): LiveData<List<VersesItem>> = quranRepository.getAyatBySurahId(verseNumber)
    fun getTadarusTest(): LiveData<List<Data>> = quranRepository.getTadarusTest()
}