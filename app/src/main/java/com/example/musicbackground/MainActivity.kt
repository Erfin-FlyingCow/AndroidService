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


    val audiolink: String = "https://drive.usercontent.google.com/download?id=1rsoJHQ0CWZgtc5rZw0XurkZI37Mz5VmE&export=download&authuser=1&confirm=t&uuid=ad0415aa-8b5e-44ee-a680-4fc4fbde884a&at=APZUnTV-iu2jJ3CEO4oE1gCWeb6x:1719922638406"
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