package com.example.lab8

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.RadioButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var burgerShop = BurgerShop();
    private var priceSelection = 0
    private val REQUEST_CODE = 1

    var burgerMessage:String = ""
    var burgerReview:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //event listener
        storeButton.setOnClickListener{
            priceSelection = spinner.selectedItemPosition
            burgerShop.suggestBurgerStore(priceSelection)

            //intent
            val intent = Intent(this, BurgerActivity::class.java)
            intent.putExtra("burgerShopName", burgerShop.name)
            intent.putExtra("burgerShopURL", burgerShop.url)

            startActivityForResult(intent, REQUEST_CODE)
        }
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
                var with = getString(R.string.with)
                toppingList = with + toppingList
            }
            if(spinner.selectedItem == "Price Preference"){
                val priceSnack = Snackbar.make(rootLayout, "Select Price Range", Snackbar.LENGTH_SHORT)
                priceSnack.show()
            }
            else{
                if(glutenSwitch.isChecked){
                    burgerType = glutenSwitch.text.toString() + " $burgerType"
                }
                var beginning = getString(R.string.beginning)
                burgerMessage = "$beginning $burgerType $toppingList"
            }
        }
        updateUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if((requestCode == REQUEST_CODE) && (resultCode == Activity.RESULT_OK)){
            if (data != null) {
                burgerReview = data.getStringExtra("review")!!
            }

        }
        updateUI()
    }

    fun updateUI(){
        messageText.setText(burgerMessage)
        reviewTextView.setText(burgerReview)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("message",burgerMessage)
        outState.putString("review",burgerReview)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        burgerMessage = savedInstanceState.getString("message","")
        burgerReview = savedInstanceState.getString(("review"),"")
        updateUI()
    }
}