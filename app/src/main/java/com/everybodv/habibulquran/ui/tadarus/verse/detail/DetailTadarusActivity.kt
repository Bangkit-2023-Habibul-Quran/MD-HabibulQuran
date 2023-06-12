package com.everybodv.habibulquran.ui.tadarus.verse.detail

import android.app.ProgressDialog
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.remote.response.VersesItem
import com.everybodv.habibulquran.databinding.ActivityDetailTadarusBinding
import com.everybodv.habibulquran.databinding.FragmentResultTadarusDialogBinding
import com.everybodv.habibulquran.ui.tadarus.TadarusViewModel
import com.everybodv.habibulquran.ui.utility.ReciteIncorrectDialogFragment
import com.everybodv.habibulquran.utils.*
import com.everybodv.habibulquran.utils.audiorecorder.AndroidAudioPlayer
import com.everybodv.habibulquran.utils.audiorecorder.AndroidAudioRecorder
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class DetailTadarusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTadarusBinding
    private lateinit var bindingDialog: FragmentResultTadarusDialogBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var suratVerse: String

    private val recorder = AndroidAudioRecorder(this)
    private val player = AndroidAudioPlayer(this)
    private var audioFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTadarusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), 0)

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

        btnPlay.setOnClickListener {
            player.playFile(audioFile ?: return@setOnClickListener)
        }


        val btnRecord = binding.btnRecordRecite

        tadarusViewModel.isRecording.observe(this) { isRecording ->
            btnRecord.apply {
                if (!isRecording) {
                    this.setOnClickListener {
                        if (mediaPlayer.isPlaying) tadarusViewModel.forceStopAudio(mediaPlayer)
                        player.stop()
                        File(cacheDir, "audio.mp3").also {
                            recorder.start(it)
                            audioFile = it
                        }
                        tadarusViewModel.startRecording(this)
                        showContent(binding.loadingRecord)
                        hideContent(binding.btnPlaySound)
                        binding.tvClickMic.text = getString(R.string.press_to_stop)
                    }

                } else {
                    this.setOnClickListener {
                        if (mediaPlayer.isPlaying) tadarusViewModel.forceStopAudio(mediaPlayer)

                        recorder.stop()
                        tadarusViewModel.stopRecording(this)
                        hideContent(binding.loadingRecord)
                        showContent(binding.btnPlaySound)
                        binding.tvClickMic.text = getString(R.string.press_mic_ayat)

                        if (audioFile != null) {
                            val file = audioFile as File
                            val reqAudio = file.asRequestBody("audio/mpeg".toMediaTypeOrNull())
                            val audioMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                                "file", file.name, reqAudio
                            )
                            val orginalText = detail.text.arab.toRequestBody("text/plain".toMediaType())
                            val dialog = BottomSheetDialog(this@DetailTadarusActivity)
                            bindingDialog = FragmentResultTadarusDialogBinding.inflate(layoutInflater)

                            try {
                                tadarusViewModel.isLoading.observe(this@DetailTadarusActivity) { isLoading ->
                                    showLoading(binding.loadingForResult, isLoading)
//                                val progressDialog = ProgressDialog(this@DetailTadarusActivity)
//                                if (isLoading == true) {
//                                    progressDialog.show()
//                                } else {
//                                    progressDialog.dismiss()
//                                }
                                }
                                tadarusViewModel.getTadarusPredict(audioMultiPart, orginalText)

                                tadarusViewModel.tadarusPredictResponse.observe(this@DetailTadarusActivity) { response ->
                                    if (!response.error) {
                                        when (response.rating) {
                                            1 -> {
                                                bindingDialog.apply {
                                                    tvCorrect.text = getString(R.string.one_rating_title)
                                                    showContent(animationOneStar)
                                                    tvDescCorrect.text = getString(R.string.one_rating_desc)
                                                    btnContinue.apply {
                                                        text = getString(R.string.retry)
                                                        this.setSafeOnClickListener { dialog.dismiss() }
                                                    }
                                                    hideContent(tvRetry)
                                                }
                                            }
                                            2 -> {
                                                bindingDialog.apply {
                                                    tvCorrect.text = getString(R.string.two_star_title)
                                                    showContent(animationTwoStar)
                                                    tvDescCorrect.text = getString(R.string.two_star_desc)
                                                    btnContinue.apply {
                                                        text = getString(R.string.retry)
                                                        this.setSafeOnClickListener { dialog.dismiss() }
                                                    }
                                                    hideContent(tvRetry)
                                                }
                                            }
                                            3 -> {
                                                bindingDialog.apply {
                                                    tvCorrect.text = getString(R.string.three_star_title)
                                                    showContent(animationThreeStar)
                                                    tvDescCorrect.text = getString(R.string.three_star_desc)
                                                }
                                            }
                                            4 -> {
                                                bindingDialog.apply {
                                                    tvCorrect.text = getString(R.string.four_star_title)
                                                    showContent(animationFourStar)
                                                    tvDescCorrect.text = getString(R.string.four_star_desc)
                                                }
                                            }
                                            5 -> {
                                                bindingDialog.apply {
                                                    tvCorrect.text = getString(R.string.five_star_title)
                                                    showContent(animationFiveStar)
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
                                    } else {
                                        showToast(this@DetailTadarusActivity, "Gagal mengirimkan ke server")
                                    }
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }

        }


//        tadarusViewModel.isPlaying.observe(this) { isPlaying ->
//            if (isPlaying == false) {
//                tadarusViewModel.playAudio(btnPlay, mediaPlayer, detail)
//            } else {
//                tadarusViewModel.stopAudio(btnPlay, mediaPlayer)
//            }
//        }
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