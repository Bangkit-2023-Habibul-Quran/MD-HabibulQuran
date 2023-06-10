package com.everybodv.habibulquran.ui.quran

import android.media.AudioManager
import android.media.MediaPlayer
import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.QuranRepository
import com.everybodv.habibulquran.data.remote.response.Data
import com.everybodv.habibulquran.data.remote.response.VersesItem

class QuranViewModel(private val quranRepository: QuranRepository) : ViewModel() {

//    val listSurah: LiveData<List<Data>> = quranRepository.listSurah
    val isLoading: LiveData<Boolean> = quranRepository.isLoading

    val surahList: LiveData<List<Data>> = quranRepository.surahList
    val listAyat: LiveData<List<VersesItem>> = quranRepository.listAyat
    val listSurahTest: LiveData<List<Data>> = quranRepository.listSurahTest

    private val _isPlaying = MutableLiveData<Boolean>(false)
    val isPlaying: LiveData<Boolean> = _isPlaying


//    fun getAllSurah(): LiveData<List<Data>> = quranRepository.getAllSurah()
    fun getListSurah(): LiveData<List<Data>> = quranRepository.getListSurah()
    fun getAyatBySurahId(verseNumber: Int): LiveData<List<VersesItem>> = quranRepository.getAyatBySurahId(verseNumber)
    fun getTadarusTest(): LiveData<List<Data>> = quranRepository.getTadarusTest()

    fun playAudio(player: MediaPlayer, audioSrc: String) : LiveData<Boolean> {
        player.reset()
        player.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            player.setDataSource(audioSrc)
            player.prepare()
            player.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        _isPlaying.postValue(true)
        player.setOnCompletionListener {
            _isPlaying.postValue(false)
        }

        return _isPlaying
    }

    fun stopAudio(player: MediaPlayer): LiveData<Boolean> {
        if (player.isPlaying) player.stop()
        _isPlaying.postValue(false)

        return _isPlaying
    }

    fun forceStopAudio(player: MediaPlayer): LiveData<Boolean> {
        _isPlaying.postValue(false)
        player.stop()
        return _isPlaying
    }
}