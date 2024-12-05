package com.example.healthtrackai

// MainActivity.kt
// John Doe - 123456

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Navigate to Main Screen after 3 seconds
        Handler().postDelayed({
            val intent = Intent(this,Screen::class.java)
            startActivity(intent)
            finish()
        }, 3000) // Delay for 3 seconds
    }
}
