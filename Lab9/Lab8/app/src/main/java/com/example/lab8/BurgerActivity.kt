package com.example.lab8

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_burger.*

class BurgerActivity : AppCompatActivity() {

    private var shopName: String?=null
    private var shopURL: String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_burger)
        setSupportActionBar(findViewById(R.id.toolbar))
        var message = getString(R.string.messageToUser)
        shopName = intent.getStringExtra("burgerShopName")
        shopURL = intent.getStringExtra("burgerShopURL")
        shopURL?.let {Log.i("url",it)};
        shopName?.let {burgerShopTextView.text = "$message $shopName"}

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            Log.i("clicked fab","clicked")
            loadWebPage()
        }
    }

    //implicit intent to load webpage
    fun loadWebPage(){
        var intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = shopURL?.let {Uri.parse(shopURL)}
        startActivity(intent)

    }
    override fun onBackPressed(){
        val data = Intent()
        data.putExtra("review", reviewText.text.toString())
        setResult(Activity.RESULT_OK, data)
        super.onBackPressed()
        finish()
    }


}