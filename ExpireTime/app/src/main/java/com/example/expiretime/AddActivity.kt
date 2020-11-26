package com.example.expiretime

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*


class AddActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1
    //create variables to hold class attributes that will be sent back to main activity
    private var foodWriting:String = ""
    private var timeLeft:Int = -1
    private var foodColor:Int = -1


    val expireD = Calendar.getInstance()
    var todaysDate :Date = Calendar.getInstance().time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


        //date picker in edit text tutorial: https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                expireD.set(Calendar.YEAR, year)
                expireD.set(Calendar.MONTH, monthOfYear)
                expireD.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDate()

            }

        expireDateInput.setOnClickListener{
            val dateDialog = DatePickerDialog(
                this, date, expireD
                    .get(Calendar.YEAR), expireD.get(Calendar.MONTH),
                expireD.get(Calendar.DAY_OF_MONTH)
            )
            dateDialog.datePicker.minDate = todaysDate.time
            dateDialog.show()
            //clear focus from first edit text since we are not in it anymore
            foodNameInput.clearFocus()
        }

        //when user selects add button, need to pass data back to main activity
        addButton.setOnClickListener{
            if(TextUtils.isEmpty(foodNameInput.text.toString()) || TextUtils.isEmpty(expireDateInput.text.toString())){
                if(TextUtils.isEmpty(foodNameInput.text.toString()) && TextUtils.isEmpty(expireDateInput.text.toString())){
                    Snackbar.make(addButton, "Must input food name and expiration date.", Snackbar.LENGTH_SHORT).show()
                }
                else if(TextUtils.isEmpty(foodNameInput.text.toString())){
                    Snackbar.make(addButton, "Must input food name.", Snackbar.LENGTH_SHORT).show()
                }
                else{
                    Snackbar.make(addButton, "Must input expiration date.", Snackbar.LENGTH_SHORT).show()
                }
            }
            else{
                calculateTextColor()
                var foodName = foodNameInput.text
                var dash = getString(R.string.dash)
                var daysLeft = getString(R.string.daysLeft)
                var dayLeft = getString(R.string.dayLeft)
                if(timeLeft==1){
                    foodWriting = "$foodName $dash $timeLeft $dayLeft"
                }
                else{
                    foodWriting = "$foodName $dash $timeLeft $daysLeft"
                }

                //start intent to go back to main activity and pass data

                val intent = Intent()
                intent.putExtra("foodString", foodWriting)
                intent.putExtra("color", foodColor)
                intent.putExtra("daysLeft", timeLeft)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }


    }

    //updates the string in the edit text to reflect the date, and calculates difference between days to be passed back to main activity
    fun updateDate(){
        val format = "MM/dd/yyyy"
        val dateFormat = SimpleDateFormat(format, Locale.US)
        expireDateInput.setText(dateFormat.format(expireD.time))
        var expDate : Date = expireD.time
        var diff: Long = expDate.time - todaysDate.time
        var seconds = diff / 1000
        var minutes = seconds / 60
        var hours = minutes / 60
        //add 1 to account for time making current day less than a day
        var days = (hours / 24)
        timeLeft = days.toInt()
    }

    fun calculateTextColor(){
        if(timeLeft<3){
            foodColor = Color.parseColor("#e52626")
        }
        else if(timeLeft <5){
            foodColor = Color.parseColor("#ffd609")
        }
        else{
            foodColor = Color.parseColor("#43a047")
        }
    }

}