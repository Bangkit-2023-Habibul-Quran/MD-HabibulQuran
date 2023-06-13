package com.everybodv.habibulquran.ui.tadarus

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.QuranRepository
import com.everybodv.habibulquran.data.remote.response.Data
import com.everybodv.habibulquran.data.remote.response.QuranPredictResponse
import com.everybodv.habibulquran.data.remote.response.VersesItem
import okhttp3.MultipartBody
import okhttp3.RequestBody

class TadarusViewModel(private val quranRepository: QuranRepository) : ViewModel() {

    val listSurahTest: LiveData<List<Data>> = quranRepository.listSurahTest
    val isLoading: LiveData<Boolean> = quranRepository.isLoading
    val tadarusPredictResponse: LiveData<QuranPredictResponse?> = quranRepository.tadarusPredictData

    private val _isRecording = MutableLiveData<Boolean>(false)
    val isRecording: LiveData<Boolean> = _isRecording

    private val _isPlaying = MutableLiveData<Boolean>(false)
    val isPlaying: LiveData<Boolean> = _isPlaying

    fun getTadarusTest(): LiveData<List<Data>> = quranRepository.getTadarusTest()

    fun getTadarusPredict(audioFile: MultipartBody.Part, originalText: RequestBody) {
        quranRepository.getTadarusPredict(audioFile, originalText)
    }

    fun clearTadarusDataPredict() = quranRepository.clearTadarusDataPredict()

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

    fun playAudio(button: ImageButton, player: MediaPlayer, audioSrc: VersesItem) : LiveData<Boolean> {
        button.setImageResource(R.drawable.ic_play_arrow_40)
        button.setOnClickListener {
            button.setImageResource(R.drawable.ic_stop_40)
            val audioUrl = audioSrc.audio.primary
            player.reset()
            player.setAudioAttributes(
                AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            )
            try {
                player.setDataSource(audioUrl)
                player.prepare()
                player.start()
            } catch (e: Exception) {
                e.printStackTrace()
                player.stop()
                _isPlaying.postValue(false)
            }
            _isPlaying.postValue(true)
        }
        player.setOnCompletionListener {
            _isPlaying.postValue(false)
        }
        return _isPlaying
    }

    fun stopAudio(button: ImageButton, player: MediaPlayer): LiveData<Boolean> {
        button.setImageResource(R.drawable.ic_stop_40)
        button.setOnClickListener {
            button.setImageResource(R.drawable.ic_play_arrow_40)
            if (player.isPlaying) player.stop()
            _isPlaying.postValue(false)
        }
        return _isPlaying
    }

    fun forceStopAudio(player: MediaPlayer): LiveData<Boolean> {
        _isPlaying.postValue(false)
        player.stop()
        return _isPlaying
    }

}