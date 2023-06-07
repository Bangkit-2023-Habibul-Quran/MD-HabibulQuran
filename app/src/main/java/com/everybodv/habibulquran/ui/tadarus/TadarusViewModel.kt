package com.everybodv.habibulquran.ui.tadarus

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.everybodv.habibulquran.data.QuranRepository
import com.everybodv.habibulquran.data.remote.response.Data
import com.everybodv.habibulquran.data.remote.response.DataItem

class TadarusViewModel(private val quranRepository: QuranRepository) : ViewModel() {

    val listSurahTest: LiveData<List<Data>> = quranRepository.listSurahTest
    val isLoading: LiveData<Boolean> = quranRepository.isLoading

    fun getTadarusTest(): LiveData<List<Data>> = quranRepository.getTadarusTest()
}