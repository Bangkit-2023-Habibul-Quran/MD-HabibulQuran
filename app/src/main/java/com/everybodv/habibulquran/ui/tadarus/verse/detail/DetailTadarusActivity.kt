package com.everybodv.habibulquran.ui.tadarus.verse.detail

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.model.SurahAyat
import com.everybodv.habibulquran.data.model.SurahFakeDataSource
import com.everybodv.habibulquran.data.remote.response.VersesItem
import com.everybodv.habibulquran.databinding.ActivityDetailTadarusBinding
import com.everybodv.habibulquran.ui.tadarus.TadarusViewModel
import com.everybodv.habibulquran.ui.utility.ReciteIncorrectDialogFragment
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.ViewModelFactory
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class DetailTadarusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTadarusBinding
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTadarusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.tes_tadarus)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val tadarusViewModel : TadarusViewModel by viewModels { factory }

        val detail = intent.getParcelableExtra<VersesItem>(Const.EXTRA_VERSE_DETAIL) as VersesItem

        binding.tvAyatSurah.text = detail.text.arab

        mediaPlayer = MediaPlayer()

        val btnPlay = binding.btnPlaySound

        binding.btnRecordRecite.setSafeOnClickListener {
            if (mediaPlayer.isPlaying) {
                tadarusViewModel.forceStopAudio(mediaPlayer)
            }
            val dialog = ReciteIncorrectDialogFragment()
            dialog.show(supportFragmentManager, Const.INCORRECT_DIALOG)
        }

        tadarusViewModel.isPlaying.observe(this) { isPlaying ->
            if (isPlaying == false) {
                tadarusViewModel.playAudio(btnPlay, mediaPlayer, detail)
            } else {
                tadarusViewModel.stopAudio(btnPlay, mediaPlayer)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}