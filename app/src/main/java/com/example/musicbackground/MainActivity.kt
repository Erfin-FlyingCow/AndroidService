package com.example.musicbackground

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    private val audiolink: String = "https://tunes.stocktune.com/public/a/b/5/ab5b1b62-acf0-4fa4-87ec-3551879338ce/galactic-banjo-cosmos-stocktune.mp3"
    private var musicPlaying : Boolean = true
    private lateinit var serviceIntent : Intent
    lateinit var button: ImageView

    private lateinit var sharedPreferences: SharedPreferences
    private val buttonReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent!!.getStringExtra("update")
            if (action=="changePlay"){
                button.setImageResource(R.drawable.play)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(buttonReceiver, IntentFilter("button.update"), RECEIVER_EXPORTED)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        button = findViewById(R.id.tombol)
        button.setImageResource(R.drawable.stop)
        serviceIntent = Intent(this, MyPlayService::class.java)
        playAudio()

        button.setOnClickListener {
            musicPlaying = if (!musicPlaying) {

                button.setImageResource(R.drawable.stop)
                true
            } else{
                stopPlayService()
                button.setImageResource(R.drawable.play)
                false
            }


        }
    }

    private fun stopPlayService() {
        try {
            stopService(serviceIntent)
        }
        catch (e : SecurityException){
            Toast.makeText(this,"Error : " + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun playAudio() {
        serviceIntent.putExtra("audiolink", audiolink)

        try {
            startService(serviceIntent)
        }
        catch (e : SecurityException) {
            Toast.makeText(this, "Erroor : " + e.message,Toast.LENGTH_SHORT).show()
        }
    }
}