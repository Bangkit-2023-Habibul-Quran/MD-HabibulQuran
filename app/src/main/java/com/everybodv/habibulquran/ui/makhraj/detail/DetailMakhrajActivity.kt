package com.everybodv.habibulquran.ui.makhraj.detail

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.remote.response.DataItem
import com.everybodv.habibulquran.databinding.ActivityDetailMakhrajBinding
import com.everybodv.habibulquran.databinding.FragmentResultMakhrajDialogBinding
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.setSafeOnClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog

class DetailMakhrajActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMakhrajBinding
    private lateinit var bindingDialog: FragmentResultMakhrajDialogBinding
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

        val isCorrect = true

        binding.btnRecordMakhraj.setSafeOnClickListener {
            if (mediaPlayer.isPlaying) mediaPlayer.stop()
//            val dialog = ReciteCorrectDialogFragment()
//            dialog.show(supportFragmentManager, Const.CORRECT_DIALOG)

            val dialog = BottomSheetDialog(this)
            bindingDialog = FragmentResultMakhrajDialogBinding.inflate(layoutInflater)

            if (isCorrect) {
                bindingDialog.apply {
                    tvCorrect.visibility = View.VISIBLE
                    animationViewCorrect.visibility = View.VISIBLE
                    tvDescCorrect.visibility = View.VISIBLE
                    btnContinue.visibility = View.VISIBLE
                    tvRetry.visibility = View.VISIBLE

                    btnContinue.setSafeOnClickListener {
                        //
                    }

                    tvRetry.setSafeOnClickListener {
                        dialog.dismiss()
                    }
                }
            } else {
                bindingDialog.apply {
                    tvIncorrect.visibility = View.VISIBLE
                    animationViewIncorrect.visibility = View.VISIBLE
                    tvDescIncorrect.visibility = View.VISIBLE
                    btnIncorrectRetry.visibility = View.VISIBLE

                    btnIncorrectRetry.setSafeOnClickListener {
                        dialog.dismiss()
                    }
                }
            }

            dialog.setCancelable(false)
            dialog.setContentView(bindingDialog.root)
            dialog.show()

        }

        binding.btnPlayMakhraj.setOnClickListener {
            val audioUrl = detail.audio
            mediaPlayer.reset()
            mediaPlayer.setAudioAttributes(AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            )
            try {
                mediaPlayer.setDataSource(audioUrl)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: Exception) {
                e.printStackTrace()
                mediaPlayer.stop()
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