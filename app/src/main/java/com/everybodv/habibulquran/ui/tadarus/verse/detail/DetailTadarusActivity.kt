package com.everybodv.habibulquran.ui.tadarus.verse.detail

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.activity.viewModels
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.remote.response.VersesItem
import com.everybodv.habibulquran.databinding.ActivityDetailTadarusBinding
import com.everybodv.habibulquran.databinding.FragmentResultTadarusDialogBinding
import com.everybodv.habibulquran.ui.tadarus.TadarusViewModel
import com.everybodv.habibulquran.ui.utility.ReciteIncorrectDialogFragment
import com.everybodv.habibulquran.utils.Const
import com.everybodv.habibulquran.utils.ViewModelFactory
import com.everybodv.habibulquran.utils.setSafeOnClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog

class DetailTadarusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTadarusBinding
    private lateinit var bindingDialog: FragmentResultTadarusDialogBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var suratVerse: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTadarusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.tes_tadarus)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val tadarusViewModel : TadarusViewModel by viewModels { factory }

        val detail = intent.getParcelableExtra<VersesItem>(Const.EXTRA_VERSE_DETAIL) as VersesItem

        setSurahVerseString(detail)

        binding.tvAyatSurah.text = detail.text.arab

        mediaPlayer = MediaPlayer()

        val btnPlay = binding.btnPlaySound
        val randomRating = (1..5).random()

        binding.btnRecordRecite.setSafeOnClickListener {
            if (mediaPlayer.isPlaying) {
                tadarusViewModel.forceStopAudio(mediaPlayer)
            }
            val dialog = BottomSheetDialog(this)
            bindingDialog = FragmentResultTadarusDialogBinding.inflate(layoutInflater)

            when (randomRating) {
                1 -> {
                    bindingDialog.apply {
                        tvCorrect.text = getString(R.string.one_rating_title)
                        animationOneStar.visibility = View.VISIBLE
                        tvDescCorrect.text = getString(R.string.one_rating_desc)
                        btnContinue.apply {
                            text = getString(R.string.retry)
                            this.setSafeOnClickListener { dialog.dismiss() }
                        }
                        tvRetry.visibility = View.GONE
                    }
                }
                2 -> {
                    bindingDialog.apply {
                        tvCorrect.text = getString(R.string.two_star_title)
                        animationTwoStar.visibility = View.VISIBLE
                        tvDescCorrect.text = getString(R.string.two_star_desc)
                        btnContinue.apply {
                            text = getString(R.string.retry)
                            this.setSafeOnClickListener { dialog.dismiss() }
                        }
                        tvRetry.visibility = View.GONE
                    }
                }
                3 -> {
                    bindingDialog.apply {
                        tvCorrect.text = getString(R.string.three_star_title)
                        animationThreeStar.visibility = View.VISIBLE
                        tvDescCorrect.text = getString(R.string.three_star_desc)
                    }
                }
                4 -> {
                    bindingDialog.apply {
                        tvCorrect.text = getString(R.string.four_star_title)
                        animationFourStar.visibility = View.VISIBLE
                        tvDescCorrect.text = getString(R.string.four_star_desc)
                    }
                }
                5 -> {
                    bindingDialog.apply {
                        tvCorrect.text = getString(R.string.five_star_title)
                        animationFiveStar.visibility = View.VISIBLE
                        tvDescCorrect.text = getString(R.string.five_star_desc)
                    }
                }
            }

            bindingDialog.tvRetry.setSafeOnClickListener {
                dialog.dismiss()
            }

            dialog.setCancelable(false)
            dialog.setContentView(bindingDialog.root)
            dialog.show()
        }

        tadarusViewModel.isPlaying.observe(this) { isPlaying ->
            if (isPlaying == false) {
                tadarusViewModel.playAudio(btnPlay, mediaPlayer, detail)
            } else {
                tadarusViewModel.stopAudio(btnPlay, mediaPlayer)
            }
        }
    }

    private fun setSurahVerseString(detail: VersesItem) {
        val verse = detail.number.inSurah
        val verseStr = if (verse < 10) "00$verse" else if (verse < 100) "0$verse" else verse.toString()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val surahNum = prefs.getInt(Const.AYAT_NUMBER, 1)

        val surahNumStr = if (surahNum < 100) "0$surahNum" else surahNum.toString()

        suratVerse = surahNumStr + verseStr
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