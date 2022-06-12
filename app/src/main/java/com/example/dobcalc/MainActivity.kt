package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    // init variables for app fields
    private var textSelectedDate : TextView? = null
    private var textMinutesDate : TextView? = null
    private var textHoursDate : TextView? = null
    private var textDaysDate : TextView? = null
    private var textYearDate : TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // assign fields to layout ID - after onCreate
        val dateButton : Button = findViewById(R.id.selectDate)
        textSelectedDate = findViewById(R.id.textSelectedDate)
        textMinutesDate = findViewById(R.id.textMinutesDate)
        textHoursDate = findViewById(R.id.textSecondsDate)
        textDaysDate = findViewById(R.id.textDaysDate)
        textYearDate = findViewById(R.id.textYearDate)

        dateButton.setOnClickListener{
            // calls clickDateButton
            clickDateButton()
        }
    }

    private fun clickDateButton(){
        // set variables for generated fields
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        // setup date picker widget and routing fields
        val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            // store user selected date
            val selectedText = "${selectedDayOfMonth}/${selectedMonth+1}/${selectedYear}"
            // assign to date textview widget
            textSelectedDate?.text = selectedText

            // setup date pattern dd/MM/yyyy
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val finalDate = sdf.parse(selectedText)

            finalDate?.let {
                // days
                val selectedDaysDate = finalDate.time / 86400000
                // hours
                val selectedHoursDate = finalDate.time / 3600000
                // minutes
                val selectedDateInMinutes = finalDate.time / 60000

                val dob = Calendar.getInstance()
                dob.set(selectedYear,selectedMonth,selectedDayOfMonth)

                // actual time
                val currentMinutes = sdf.parse(sdf.format(System.currentTimeMillis()))
            //    val currentDays = sdf.parse(sdf.format(System.currentTimeMillis()))
                currentMinutes?.let {
                    // DAYS
                    val currentDateInDays = currentMinutes.time / 86400000
                    val differenceBetweenDatesInDays = currentDateInDays - selectedDaysDate
                    // HOURS
                    val currentDateInHours = currentMinutes.time / 3600000
                    val differenceBetweenDatesInHours = currentDateInHours - selectedHoursDate
                    // MINUTES
                    val currentDateInMinutes = currentMinutes.time / 60000
                    val differenceBetweenDates = currentDateInMinutes - selectedDateInMinutes

                    var age =myCalendar.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
                    if(myCalendar.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))
                    {
                        age--
                    }
                    // assign to date textview widget
                    textDaysDate?.text = differenceBetweenDatesInDays.toString()
                    textMinutesDate?.text = differenceBetweenDates.toString()
                    textHoursDate?.text = differenceBetweenDatesInHours.toString()
                    textYearDate?.text = "$age"

                    // Toast.makeText(this, "Your age is $age", Toast.LENGTH_SHORT).show()
                }
            }
        }, year, month, day)
        // setup max date for today
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}
