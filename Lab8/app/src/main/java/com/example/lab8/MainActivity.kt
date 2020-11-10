package com.example.lab8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.RadioButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun createBurger(view: View) {
        var burgerType:CharSequence = ""
        var toppingList = ""
        var burgerId = radioGroup.checkedRadioButtonId

        if(burgerId == -1) {
            val typeSnack = Snackbar.make(rootLayout, "Select Type of Burger", Snackbar.LENGTH_SHORT)
            typeSnack.show()
        }
        else {
            burgerType = findViewById<RadioButton>(burgerId).text
            if(checkBox.isChecked){
                toppingList += " " + checkBox.text
            }
            if(checkBox2.isChecked){
                toppingList += " " + checkBox2.text
            }
            if(checkBox3.isChecked){
                toppingList += " " + checkBox3.text
            }
            if(checkBox4.isChecked){
                toppingList += " " + checkBox4.text
            }
            if(toppingList.isNotEmpty()){
                toppingList = "with" + toppingList
            }
            if(spinner.selectedItem == "Size of Fries"){
                val friesSnack = Snackbar.make(rootLayout, "Select Size of Fries", Snackbar.LENGTH_SHORT)
                friesSnack.show()
            }
            else{
                if(glutenSwitch.isChecked){
                    burgerType = glutenSwitch.text.toString() + " $burgerType"
                }
                val frySize = "and " + spinner.selectedItem + " fries"
                messageText.text = "You ordered a $burgerType $toppingList $frySize"
            }
        }
    }
}