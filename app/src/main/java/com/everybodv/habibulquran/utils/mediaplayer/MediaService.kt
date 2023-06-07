package com.everybodv.habibulquran.utils.mediaplayer

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import com.everybodv.habibulquran.R
import java.io.IOException
import java.lang.ref.WeakReference

class MediaService : Service(), MediaPlayerCallback {
    private var isReady: Boolean = false
    private var mMediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        if (action != null) {
            when (action) {
                MP_ACTION_CREATE -> if (mMediaPlayer == null) {
                    init()
                }
                MP_ACTION_DESTROY -> if (mMediaPlayer?.isPlaying as Boolean) {
                    stopSelf()
                }
                else -> {
                    init()
                }
            }
        }
        Log.d(MP_TAG, "onStartCommand: ")
        return flags
    }

    private fun init() {
        mMediaPlayer = MediaPlayer()
        val attribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        mMediaPlayer?.setAudioAttributes(attribute)

//        val afd = applicationContext.resources.openRawResourceFd(R.raw.canon_rock)
        try {
            mMediaPlayer?.run { setDataSource("https://cdn.islamic.network/quran/audio/64/ar.alafasy/9.mp3") }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mMediaPlayer?.setOnPreparedListener{
            isReady = true
            mMediaPlayer?.start()
        }
        mMediaPlayer?.setOnErrorListener { _, _, _ ->
            false
        }
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(MP_TAG, "onBind: ")
        return mMessenger.binder
    }

    override fun onPlay() {
        if (!isReady) {
            mMediaPlayer?.prepareAsync()
        } else {
            if (mMediaPlayer?.isPlaying as Boolean) {
                mMediaPlayer?.pause()
            } else {
                mMediaPlayer?.start()
            }
        }
    }

    override fun onStop() {
        if (mMediaPlayer?.isPlaying as Boolean || isReady) {
            mMediaPlayer?.stop()
            isReady = false
        }
    }

    private val mMessenger = Messenger(IncomingHandler(this))

    internal class IncomingHandler(playerCallback: MediaPlayerCallback) :
        Handler(Looper.getMainLooper()) {

        private val mediaPlayerCallbackWeakReference: WeakReference<MediaPlayerCallback> = WeakReference(playerCallback)

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MP_PLAY -> mediaPlayerCallbackWeakReference.get()?.onPlay()
                MP_STOP -> mediaPlayerCallbackWeakReference.get()?.onStop()
                else -> super.handleMessage(msg)
            }
        }

    }

    companion object {
        const val MP_ACTION_CREATE = "com.everybodv.habibulquran.utils.mediaplayer.mediaservice.create"
        const val MP_ACTION_DESTROY = "com.everybodv.habibulquran.utils.mediaplayer.mediaservice.destroy"
        const val MP_TAG = "MediaService"
        const val MP_PLAY = 0
        const val MP_STOP = 1
    }
}