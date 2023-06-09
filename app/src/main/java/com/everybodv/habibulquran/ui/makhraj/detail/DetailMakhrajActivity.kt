package com.everybodv.habibulquran.ui.makhraj.detail

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.model.Hijaiyah
import com.everybodv.habibulquran.data.remote.response.DataItem
import com.everybodv.habibulquran.databinding.ActivityDetailMakhrajBinding
import com.everybodv.habibulquran.ui.utility.ReciteCorrectDialogFragment
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.setSafeOnClickListener

class DetailMakhrajActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMakhrajBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMakhrajBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.makhraj)
            setDisplayHomeAsUpEnabled(true)
        }

        val detail = intent.getParcelableExtra<DataItem>(Const.MAKHRAJ) as DataItem

        binding.tvHijaiyahLetter.text = detail.arabic
        binding.tvPronounce.text = detail.pronounciation

        mediaPlayer = MediaPlayer()

        binding.btnRecordMakhraj.setSafeOnClickListener {
            if (mediaPlayer.isPlaying) mediaPlayer.stop()
            val dialog = ReciteCorrectDialogFragment()
            dialog.show(supportFragmentManager, Const.CORRECT_DIALOG)
        }

        binding.btnPlayMakhraj.setOnClickListener {
            val audioUrl = detail.audio
            mediaPlayer.reset()
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            try {
                mediaPlayer.setDataSource(audioUrl)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: Exception) {
                e.printStackTrace()
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