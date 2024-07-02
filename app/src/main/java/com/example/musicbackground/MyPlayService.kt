package com.example.musicbackground

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast

class MyPlayService: Service(),MediaPlayer.OnCompletionListener,
    MediaPlayer.OnSeekCompleteListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,
    MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener{

    private lateinit var mediaPlayer : MediaPlayer
    private lateinit var link : String

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()

        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnErrorListener(this)
        mediaPlayer.setOnSeekCompleteListener(this)
        mediaPlayer.setOnInfoListener(this)
        mediaPlayer.setOnBufferingUpdateListener(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        link = intent?.getStringExtra("audiolink").toString()
        mediaPlayer.reset()

        if (mediaPlayer.isPlaying){
            try {
                mediaPlayer.setDataSource(link)
                mediaPlayer.prepareAsync()
            }
            catch (e : Exception){
                Toast.makeText(this, "Error " + e.message, Toast.LENGTH_SHORT).show()
            }
            return START_STICKY
        }


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSeekComplete(mp: MediaPlayer?) {
        TODO("Not yet implemented")
    }

    override fun onPrepared(mp: MediaPlayer?) {
        TODO("Not yet implemented")
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {

        when(what){
            MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK ->
                Toast.makeText(this, "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK",
                    Toast.LENGTH_SHORT).show()

            MediaPlayer.MEDIA_ERROR_SERVER_DIED -> Toast.makeText(this,"MEDIA_ERROR_SERVER_DIED",
                Toast.LENGTH_SHORT).show()


            MediaPlayer.MEDIA_ERROR_UNKNOWN -> Toast.makeText(this, "MEDIA_ERROR_UNKNOWN",
                Toast.LENGTH_SHORT).show()
        }
        return false
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        TODO("Not yet implemented")
    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCompletion(mp: MediaPlayer?) {

        if (mp?.isPlaying!!){
            mp.stop()
        }
        stopSelf()
    }
}