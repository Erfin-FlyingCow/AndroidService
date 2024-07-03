package com.example.musicbackground

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    val audiolink: String = "https://tunes.stocktune.com/public/a/b/5/ab5b1b62-acf0-4fa4-87ec-3551879338ce/galactic-banjo-cosmos-stocktune.mp3"
    var musicPlaying : Boolean = false
    lateinit var serviceIntent : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button: ImageView = findViewById(R.id.tombol)
        button.setImageResource(R.drawable.play)
        serviceIntent = Intent(this, MyPlayService::class.java)

        button.setOnClickListener(){
            if (!musicPlaying) {
                playAudio()
                button.setImageResource(R.drawable.stop)
                musicPlaying=true
            }
            else{
                stopPlayService()
                button.setImageResource(R.drawable.play)
                musicPlaying=false
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
        serviceIntent.putExtra("audiolink", audiolink);

        try {
            startService(serviceIntent)
        }
        catch (e : SecurityException) {
            Toast.makeText(this, "Erroor : " + e.message,Toast.LENGTH_SHORT).show()
        }
    }
}