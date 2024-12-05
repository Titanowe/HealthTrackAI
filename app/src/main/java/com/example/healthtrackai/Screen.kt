package com.example.healthtrackai
// Main Function Activity.kt

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Screen : AppCompatActivity() {

    private lateinit var waterInput: EditText
    private lateinit var exerciseInput: EditText
    private lateinit var sleepInput: EditText
    private lateinit var submitButton: Button
    private lateinit var viewDetailsButton: Button
    private lateinit var clearButton: Button
    private lateinit var weeklyDataText: TextView

    // Arrays to store daily data for each of the 7 days (Sunday to Saturday)
    private val waterIntake = FloatArray(7) { 0f }  // 7 days of water intake (in cups)
    private val exerciseDuration = IntArray(7) { 0 }  // 7 days of exercise (in minutes)
    private val sleepDuration = FloatArray(7) { 0f }  // 7 days of sleep (in hours)
    private var currentDayIndex = 0  // To track which day we are entering data for

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        // Initialize UI components
        waterInput = findViewById(R.id.waterInput)
        exerciseInput = findViewById(R.id.exerciseInput)
        sleepInput = findViewById(R.id.sleepInput)
        submitButton = findViewById(R.id.submitButton)
        viewDetailsButton = findViewById(R.id.viewDetailsButton)
        clearButton = findViewById(R.id.clearButton)
        weeklyDataText = findViewById(R.id.weeklyDataText)

        // Submit button functionality
        submitButton.setOnClickListener {
            try {
                val water = waterInput.text.toString().toFloat()
                val exercise = exerciseInput.text.toString().toInt()
                val sleep = sleepInput.text.toString().toFloat()

                if (water <= 0 || exercise <= 0 || sleep <= 0) {
                    Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                } else {
                    // Store the data for the current day
                    waterIntake[currentDayIndex] = water
                    exerciseDuration[currentDayIndex] = exercise
                    sleepDuration[currentDayIndex] = sleep

                    // Move to the next day (index)
                    currentDayIndex = (currentDayIndex + 1) % 7

                    Toast.makeText(this, "Data Submitted for Day ${currentDayIndex + 1}", Toast.LENGTH_SHORT).show()

                    // Clear the input fields
                    waterInput.text.clear()
                    exerciseInput.text.clear()
                    sleepInput.text.clear()

                    // Update the displayed weekly data
                    updateWeeklyDataDisplay()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Invalid input, please try again.", Toast.LENGTH_SHORT).show()
            }
        }

        // View detailed information button functionality
        viewDetailsButton.setOnClickListener {
            val intent = Intent(this, DetailedViewActivity::class.java)
            startActivity(intent)
        }

        // Clear data button functionality
        clearButton.setOnClickListener {
            // Reset the arrays and current day index
            for (i in 0 until 7) {
                waterIntake[i] = 0f
                exerciseDuration[i] = 0
                sleepDuration[i] = 0f
            }
            currentDayIndex = 0
            Toast.makeText(this, "Data Cleared", Toast.LENGTH_SHORT).show()

            // Update the displayed weekly data
            updateWeeklyDataDisplay()
        }
    }

    // Update the displayed weekly data
    private fun updateWeeklyDataDisplay() {
        val weeklyData = StringBuilder()
        for (i in 0 until 7) {
            weeklyData.append("Day ${i + 1}:\n")
            weeklyData.append("Water Intake: ${waterIntake[i]} cups\n")
            weeklyData.append("Exercise Duration: ${exerciseDuration[i]} minutes\n")
            weeklyData.append("Sleep Duration: ${sleepDuration[i]} hours\n")
            weeklyData.append("\n")
        }
        weeklyDataText.text = weeklyData.toString()
    }
}
