import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthtrackai.DetailedViewActivity
import com.example.healthtrackai.R
import java.util.*

class Screen : AppCompatActivity() {

    private lateinit var waterInput: EditText
    private lateinit var exerciseInput: EditText
    private lateinit var sleepInput: EditText
    private lateinit var submitBtn: Button
    private lateinit var viewDetailsBtn: Button
    private lateinit var clearBtn: Button
    private lateinit var weeklyDataText: TextView
    private lateinit var datePickerBtn: Button
    private lateinit var selectedDateText: TextView

    private val waterIntake = FloatArray(7) { 0f }
    private val exerciseDuration = IntArray(7) { 0 }
    private val sleepDuration = FloatArray(7) { 0f }
    private var currentDayIndex = 0
    private var selectedDate: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        // Initialize UI components
        waterInput = findViewById(R.id.waterInput)
        exerciseInput = findViewById(R.id.exerciseInput)
        sleepInput = findViewById(R.id.sleepInput)
        submitBtn = findViewById(R.id.submitBtn)
        viewDetailsBtn = findViewById(R.id.viewDetailsBtn)
        clearBtn = findViewById(R.id.clearBtn)
        weeklyDataText = findViewById(R.id.weeklyDataText)
        datePickerBtn = findViewById(R.id.datePickerBtn)
        selectedDateText = findViewById(R.id.selectedDateText)

        // Set up DatePicker dialog
        datePickerBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDayOfMonth"
                    selectedDate = formattedDate
                    selectedDateText.text = "Selected Date: $formattedDate"
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        // Submit button functionality
        submitBtn.setOnClickListener {
            try {
                val water = waterInput.text.toString().toFloat()
                val exercise = exerciseInput.text.toString().toInt()
                val sleep = sleepInput.text.toString().toFloat()

                if (water <= 0 || exercise <= 0 || sleep <= 0) {
                    Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                } else {
                    // Store the data for the selected day
                    if (selectedDate.isNotEmpty()) {
                        // Store the data based on the selected date (optional: you can map this to the day index if needed)
                        // Here we'll use the currentDayIndex, but you could store data in a map using the selectedDate if preferred
                        waterIntake[currentDayIndex] = water
                        exerciseDuration[currentDayIndex] = exercise
                        sleepDuration[currentDayIndex] = sleep

                        currentDayIndex = (currentDayIndex + 1) % 7

                        Toast.makeText(this, "Data Submitted for $selectedDate", Toast.LENGTH_SHORT).show()

                        // Clear input fields
                        waterInput.text.clear()
                        exerciseInput.text.clear()
                        sleepInput.text.clear()

                        // Update weekly data display
                        updateWeeklyDataDisplay()
                    } else {
                        Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Invalid input, please try again.", Toast.LENGTH_SHORT).show()
            }
        }

        // View details button functionality
        viewDetailsBtn.setOnClickListener {
            val intent = Intent(this, DetailedViewActivity::class.java)
            startActivity(intent)
        }

        // Clear button functionality
        clearBtn.setOnClickListener {
            // Reset data
            for (i in 0 until 7) {
                waterIntake[i] = 0f
                exerciseDuration[i] = 0
                sleepDuration[i] = 0f
            }
            currentDayIndex = 0
            selectedDate = ""
            selectedDateText.text = "Selected Date: None"
            Toast.makeText(this, "Data Cleared", Toast.LENGTH_SHORT).show()

            // Update weekly data display
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
