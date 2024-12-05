package com.example.healthtrackai

// DetailedViewActivity.kt

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailedViewActivity : AppCompatActivity() {

    private lateinit var detailedDataText: TextView
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        detailedDataText = findViewById(R.id.detailedDataText)
        backButton = findViewById(R.id.backButton)

        // Display the detailed data
        detailedDataText.text = "Water Intake, Exercise, and Sleep details."

        backButton.setOnClickListener {
            finish() // Navigate back to the Main Screen
        }
    }
}
