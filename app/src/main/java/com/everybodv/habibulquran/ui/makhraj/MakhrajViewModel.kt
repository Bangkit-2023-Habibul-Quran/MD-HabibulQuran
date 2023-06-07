package com.everybodv.habibulquran.ui.makhraj

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.everybodv.habibulquran.data.QuranRepository
import com.everybodv.habibulquran.data.remote.response.DataItem

class MakhrajViewModel(private val quranRepository: QuranRepository) : ViewModel() {

    val listHijaiyah: LiveData<List<DataItem>> = quranRepository.listHijaiyah
    val isLoading: LiveData<Boolean> = quranRepository.isLoading

    fun getAllHijaiyah(): LiveData<List<DataItem>> = quranRepository.getAllHijaiyah()
}