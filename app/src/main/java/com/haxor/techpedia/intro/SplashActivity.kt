package com.haxor.techpedia.intro

import android.content.Intent
import android.os.Handler
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

import com.haxor.techpedia.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)
        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(applicationContext, WelcomeActivity::class.java))
            finish()
        }, 3000L) //3000 L = 3 detik
    }
}
