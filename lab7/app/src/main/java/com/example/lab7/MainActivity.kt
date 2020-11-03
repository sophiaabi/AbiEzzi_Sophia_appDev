package com.example.lab7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun sayHello(view: View) {
        val responseText = findViewById<TextView>(R.id.textMessage)
        val editName = findViewById<EditText>(R.id.editTextName)
        val name = editName.text
        responseText.setText("Hello " + name + "!")

        val imageSmile = findViewById<ImageView>(R.id.imageView)
        imageSmile.setImageResource(R.drawable.smiley)
    }
}