package com.everybodv.habibulquran.ui.makhraj

import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.QuranRepository
import com.everybodv.habibulquran.data.remote.response.DataItem
import com.everybodv.habibulquran.data.remote.response.HijaiyahPredictResponse
import okhttp3.MultipartBody

class MakhrajViewModel(private val quranRepository: QuranRepository) : ViewModel() {

    val listHijaiyah: LiveData<List<DataItem>> = quranRepository.listHijaiyah
    val isLoading: LiveData<Boolean> = quranRepository.isLoading
    val hijaiyahPredictData: LiveData<HijaiyahPredictResponse> = quranRepository.hijaiyahPredictData
    private val _isRecording = MutableLiveData<Boolean>(false)
    val isRecording: LiveData<Boolean> = _isRecording
    fun startRecording(button: ImageButton): LiveData<Boolean> {
        button.setImageResource(R.drawable.ic_stop_40)
        _isRecording.value = true

        return _isRecording
    }

    fun stopRecording(button: ImageButton): LiveData<Boolean> {
        button.setImageResource(R.drawable.ic_mic_24)
        _isRecording.value = false

        return _isRecording
    }

    fun clearMakhrajDataPredict(): LiveData<HijaiyahPredictResponse> = quranRepository.clearMakhrajDataPredict()

    fun getAllHijaiyah(): LiveData<List<DataItem>> = quranRepository.getAllHijaiyah()
    fun getMakhrajPredict(audioFile: MultipartBody.Part): LiveData<HijaiyahPredictResponse> =  quranRepository.getMakhrajPredict(audioFile)
}