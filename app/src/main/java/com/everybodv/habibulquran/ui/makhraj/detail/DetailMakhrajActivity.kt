package com.everybodv.habibulquran.ui.makhraj.detail

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.everybodv.habibulquran.R
import com.everybodv.habibulquran.data.remote.response.DataItem
import com.everybodv.habibulquran.databinding.ActivityDetailMakhrajBinding
import com.everybodv.habibulquran.databinding.FragmentResultMakhrajDialogBinding
import com.everybodv.habibulquran.ui.makhraj.MakhrajViewModel
import com.everybodv.habibulquran.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hernandazevedo.androidmp3recorder.MP3Recorder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class DetailMakhrajActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMakhrajBinding
    private lateinit var bindingDialog: FragmentResultMakhrajDialogBinding
    private lateinit var mediaPlayer: MediaPlayer

    private var mRecorder: MP3Recorder? = null
    private var audioFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMakhrajBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.makhraj)
            setDisplayHomeAsUpEnabled(true)
        }

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val makhrajViewModel : MakhrajViewModel by viewModels { factory }

        val detail = intent.getParcelableExtra<DataItem>(Const.MAKHRAJ) as DataItem

        val predict = detail.prediction

        binding.tvHijaiyahLetter.text = detail.arabic
        binding.tvPronounce.text = detail.pronounciation

        mediaPlayer = MediaPlayer()

        val btnRecord = binding.btnRecordMakhraj

        if (!detail.available) {
            hideContent(btnRecord)
            hideContent(binding.tvClickMic)
        } else {
            makhrajViewModel.isRecording.observe(this) { isRecording ->
                btnRecord.apply {
                    if (!isRecording) {
                        this.setOnClickListener {
                            if (mediaPlayer.isPlaying) mediaPlayer.stop()
                            File(cacheDir, "audio.mp3").also {
                                mRecorder = MP3Recorder(it)
                                mRecorder?.start()
                                audioFile = it
                            }
                            makhrajViewModel.startRecording(this)
                            showContent(binding.loadingRecord)
                            binding.tvClickMic.text = getString(R.string.press_to_stop)
                        }

                    } else {
                        this.setOnClickListener {
                            if (mediaPlayer.isPlaying) mediaPlayer.stop()

                            mRecorder?.stop()
                            makhrajViewModel.stopRecording(this)
                            hideContent(binding.loadingRecord)
                            binding.tvClickMic.text = getString(R.string.press_mic_ayat)

                            Handler(Looper.getMainLooper()).postDelayed({
                                if (audioFile != null) {
                                    val file = audioFile as File
                                    val reqAudio = file.asRequestBody("audio/mpeg".toMediaTypeOrNull())
                                    val audioMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                                        "", file.name, reqAudio
                                    )
                                    val dialog = BottomSheetDialog(this@DetailMakhrajActivity)
                                    dialog.setCancelable(false)
                                    bindingDialog = FragmentResultMakhrajDialogBinding.inflate(layoutInflater)
                                    dialog.setContentView(bindingDialog.root)

                                    try {
                                        makhrajViewModel.isLoading.observe(this@DetailMakhrajActivity) { isLoading ->
                                            showLoading(binding.loadingForResult, isLoading)
                                        }
                                        makhrajViewModel.getMakhrajPredict(audioMultiPart)
                                        makhrajViewModel.hijaiyahPredictData.observe(this@DetailMakhrajActivity) { response ->
                                            if (response?.error == false) {
                                                if (response.predictedLabel == predict.lowercase()) {
                                                    bindingDialog.apply {
                                                        showContent(tvCorrect)
                                                        showContent(animationViewCorrect)
                                                        showContent(tvDescCorrect)
                                                        showContent(btnContinue)
                                                        showContent(tvRetry)
                                                        tvRetry.setSafeOnClickListener {
                                                            dialog.dismiss()
                                                            makhrajViewModel.clearMakhrajDataPredict()
                                                        }
                                                        btnContinue.setSafeOnClickListener {
                                                            dialog.dismiss()
                                                            makhrajViewModel.clearMakhrajDataPredict()
                                                        }
                                                        dialog.show()
                                                    }
                                                } else {
                                                    bindingDialog.apply {
                                                        showContent(tvIncorrect)
                                                        showContent(animationViewIncorrect)
                                                        showContent(tvDescIncorrect)
                                                        showContent(btnIncorrectRetry)
                                                        btnIncorrectRetry.setSafeOnClickListener {
                                                            dialog.dismiss()
                                                            makhrajViewModel.clearMakhrajDataPredict()
                                                        }
                                                        dialog.show()
                                                    }
                                                }
                                            } else {
                                                dialog.hide()
                                                dialog.dismiss()
                                            }
                                        }

                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            },300)
                        }
                    }
                }
            }
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
        if (mRecorder?.isRecording == true) mRecorder?.stop()
        mRecorder = null
    }

    override fun onSupportNavigateUp(): Boolean {
        @Suppress("DEPRECATION")
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}