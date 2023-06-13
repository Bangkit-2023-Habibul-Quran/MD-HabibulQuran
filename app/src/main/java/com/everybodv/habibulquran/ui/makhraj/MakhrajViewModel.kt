package com.everybodv.habibulquran.ui.makhraj

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.everybodv.habibulquran.data.QuranRepository
import com.everybodv.habibulquran.data.remote.response.DataItem
import com.everybodv.habibulquran.data.remote.response.HijaiyahPredictResponse
import okhttp3.MultipartBody

class MakhrajViewModel(private val quranRepository: QuranRepository) : ViewModel() {

    val listHijaiyah: LiveData<List<DataItem>> = quranRepository.listHijaiyah
    val isLoading: LiveData<Boolean> = quranRepository.isLoading
    val hijaiyahPredictData: LiveData<HijaiyahPredictResponse> = quranRepository.hijaiyahPredictData

    fun getAllHijaiyah(): LiveData<List<DataItem>> = quranRepository.getAllHijaiyah()
    fun getMakhrajPredict(audioFile: MultipartBody.Part): LiveData<HijaiyahPredictResponse> =  quranRepository.getMakhrajPredict(audioFile)
}