package com.everybodv.habibulquran.ui.tadarus.verse.detail

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.remote.response.VersesItem
import com.everybodv.habibulquran.databinding.ActivityDetailTadarusBinding
import com.everybodv.habibulquran.databinding.FragmentResultTadarusDialogBinding
import com.everybodv.habibulquran.ui.tadarus.TadarusViewModel
import com.everybodv.habibulquran.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hernandazevedo.androidmp3recorder.MP3Recorder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*

class DetailTadarusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTadarusBinding
    private lateinit var bindingDialog: FragmentResultTadarusDialogBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var suratVerse: String

    private var audioFile: File? = null

    private var mRecorder: MP3Recorder? = null

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

        binding.tvAyatSurah.text = detail.text.arab

        mediaPlayer = MediaPlayer()

        val btnPlay = binding.btnPlaySound
        val randomRating = (1..5).random()


        tadarusViewModel.isPlaying.observe(this) { isPlaying ->
            if (isPlaying == false) {
                tadarusViewModel.playAudio(btnPlay, mediaPlayer, detail)
            } else {
                tadarusViewModel.stopAudio(btnPlay, mediaPlayer)
            }
        }

        val btnRecord = binding.btnRecordRecite
        val btnPredict = binding.btnSendHidden

        btnPredict.setSafeOnClickListener {
            if (audioFile != null) {
                hideContent(binding.btnPlaySound)
                val file = audioFile as File
                val reqAudio = file.asRequestBody("audio/mpeg".toMediaTypeOrNull())
                val audioMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "file", file.name, reqAudio
                )
                val orginalText = detail.text.arab.toRequestBody("text/plain".toMediaType())
                val dialog = BottomSheetDialog(this@DetailTadarusActivity)
                dialog.setCancelable(false)
                bindingDialog = FragmentResultTadarusDialogBinding.inflate(layoutInflater)
                dialog.setContentView(bindingDialog.root)

                try {
                    tadarusViewModel.isLoading.observe(this@DetailTadarusActivity) { isLoading ->
                        showLoading(binding.loadingForResult, isLoading)
                    }
                    tadarusViewModel.getTadarusPredict(audioMultiPart, orginalText)

                    tadarusViewModel.tadarusPredictResponse.observe(this@DetailTadarusActivity) { response ->
                        if (response?.error == false) {
                            if (response.rating != null) showContent(binding.btnPlaySound)
                            when (response.rating) {
                                1 -> {
                                    bindingDialog.apply {
                                        tvCorrect.text = getString(R.string.one_rating_title)
                                        showContent(animationOneStar)
                                        tvDescCorrect.text = getString(R.string.one_rating_desc)
                                        btnContinue.apply {
                                            text = getString(R.string.retry)
                                            this.setSafeOnClickListener {
                                                dialog.dismiss()
                                                tadarusViewModel.clearTadarusDataPredict()
                                            }

                                        }
                                        hideContent(tvRetry)
                                        dialog.show()
                                    }
                                }
                                2 -> {
                                    bindingDialog.apply {
                                        tvCorrect.text = getString(R.string.two_star_title)
                                        showContent(animationTwoStar)
                                        tvDescCorrect.text = getString(R.string.two_star_desc)
                                        btnContinue.apply {
                                            text = getString(R.string.retry)
                                            this.setSafeOnClickListener {
                                                dialog.dismiss()
                                                tadarusViewModel.clearTadarusDataPredict()
                                            }
                                        }
                                        hideContent(tvRetry)
                                        dialog.show()
                                    }
                                }
                                3 -> {
                                    bindingDialog.apply {
                                        tvCorrect.text = getString(R.string.three_star_title)
                                        showContent(animationThreeStar)
                                        tvDescCorrect.text = getString(R.string.three_star_desc)
                                        btnContinue.setSafeOnClickListener {
                                            dialog.dismiss()
                                            tadarusViewModel.clearTadarusDataPredict()
                                        }
                                        dialog.show()
                                    }
                                }
                                4 -> {
                                    bindingDialog.apply {
                                        tvCorrect.text = getString(R.string.four_star_title)
                                        showContent(animationFourStar)
                                        tvDescCorrect.text = getString(R.string.four_star_desc)
                                        btnContinue.setSafeOnClickListener {
                                            dialog.dismiss()
                                            tadarusViewModel.clearTadarusDataPredict()
                                        }
                                        dialog.show()
                                    }
                                }
                                5 -> {
                                    bindingDialog.apply {
                                        tvCorrect.text = getString(R.string.five_star_title)
                                        showContent(animationFiveStar)
                                        tvDescCorrect.text = getString(R.string.five_star_desc)
                                        btnContinue.setSafeOnClickListener {
                                            dialog.dismiss()
                                            tadarusViewModel.clearTadarusDataPredict()
                                        }
                                        dialog.show()
                                    }
                                }
                                else -> {
                                    dialog.hide()
                                    dialog.dismiss()
                                }
                            }
                            bindingDialog.tvRetry.setSafeOnClickListener {
                                dialog.dismiss()
                                tadarusViewModel.clearTadarusDataPredict()
                            }

                        } else {
                            showToast(this@DetailTadarusActivity, "Gagal mengirimkan ke server")
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        tadarusViewModel.isRecording.observe(this) { isRecording ->
            btnRecord.apply {
                if (!isRecording) {
                    this.setOnClickListener {
                        if (mediaPlayer.isPlaying) tadarusViewModel.forceStopAudio(mediaPlayer)
                        File(cacheDir, "audio.mp3").also {
                            mRecorder = MP3Recorder(it)
//                            recorder.start(it)
                            mRecorder?.start()
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

                        mRecorder?.stop()
                        tadarusViewModel.stopRecording(this)
                        hideContent(binding.loadingRecord)
                        showContent(binding.btnPlaySound)
                        binding.tvClickMic.text = getString(R.string.press_mic_ayat)

                        Handler(Looper.getMainLooper()).postDelayed({
                            btnPredict.performClick()
                        },300)
                    }
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer.release()
        if (mRecorder?.isRecording == true) {
            mRecorder?.stop()
        }
        mRecorder = null
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}