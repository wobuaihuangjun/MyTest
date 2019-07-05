package com.hzj.mytest.kotlin

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.hzj.mytest.R

class TestKotlinActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        val myMessage = findViewById<TextView>(R.id.myMessage)
        myMessage.setText("Hello Kotlin")

        val myButton = findViewById<Button>(R.id.myButton)
        myButton.setOnClickListener {
            Toast.makeText(this, "On Click", Toast.LENGTH_LONG).show()
        }
    }
}