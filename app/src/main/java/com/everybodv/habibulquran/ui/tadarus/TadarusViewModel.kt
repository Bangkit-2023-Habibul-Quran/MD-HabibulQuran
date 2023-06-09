package com.everybodv.habibulquran.ui.tadarus

import android.media.AudioManager
import android.media.MediaPlayer
import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.QuranRepository
import com.everybodv.habibulquran.data.remote.response.Data
import com.everybodv.habibulquran.data.remote.response.DataItem
import com.everybodv.habibulquran.data.remote.response.VersesItem

class TadarusViewModel(private val quranRepository: QuranRepository) : ViewModel() {

    val listSurahTest: LiveData<List<Data>> = quranRepository.listSurahTest
    val isLoading: LiveData<Boolean> = quranRepository.isLoading
    private val _isPlaying = MutableLiveData<Boolean>(false)
    val isPlaying: LiveData<Boolean> = _isPlaying

    fun getTadarusTest(): LiveData<List<Data>> = quranRepository.getTadarusTest()

    fun playAudio(button: ImageButton, player: MediaPlayer, audioSrc: VersesItem) : LiveData<Boolean> {
        button.setImageResource(R.drawable.ic_play_arrow_40)
        button.setOnClickListener {
            button.setImageResource(R.drawable.ic_stop_40)
            val audioUrl = audioSrc.audio.primary
            player.reset()
            player.setAudioStreamType(AudioManager.STREAM_MUSIC)
            try {
                player.setDataSource(audioUrl)
                player.prepare()
                player.start()
            } catch (e: Exception) {
                e.printStackTrace()
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