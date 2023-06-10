package com.everybodv.habibulquran.ui.quran.surah

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.everybodv.habibulquran.databinding.ActivityDetailSurahBinding
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.data.remote.response.Data
import com.everybodv.habibulquran.data.remote.response.VersesItem
import com.everybodv.habibulquran.ui.quran.QuranViewModel
import com.everybodv.habibulquran.utils.ViewModelFactory
import com.everybodv.habibulquran.utils.showLoading

class DetailSurahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSurahBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSurahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val quranViewModel : QuranViewModel by viewModels { factory }

        val detail = intent.getParcelableExtra<Data>(Const.EXTRA_SURAH) as Data

        quranViewModel.isLoading.observe(this) { isLoading ->
            showLoading(binding.loading2, isLoading)
        }

        quranViewModel.getAyatBySurahId(detail.number)

        mediaPlayer = MediaPlayer()

        quranViewModel.listAyat.observe(this) { listAyat ->
            binding.rvAyat.apply {
                layoutManager = LinearLayoutManager(this@DetailSurahActivity, LinearLayoutManager.VERTICAL, false)
                val surahAdapter = DetailSurahAdapter(listAyat)
                adapter = surahAdapter
                surahAdapter.setOnButtonClickCallback(object : DetailSurahAdapter.OnButtonClickCallback {
                    override fun onButtonPlayClicked(versesItem: VersesItem) {
                        val audioSrc = versesItem.audio.primary
                        quranViewModel.playAudio(mediaPlayer, audioSrc)
                    }
                    override fun onButtonStopClicked(versesItem: VersesItem) {
                        quranViewModel.stopAudio(mediaPlayer)
                    }
                })
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer.release()
    }

}