package com.example.activitylifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val strData = intent.getStringExtra("dataFromFirstActivity")
        val editText = findViewById<EditText>(R.id.editText)
        editText.setText(strData)

        val btn_close = findViewById<Button>(R.id.buttonThirdActivity)
        btn_close.setOnClickListener{
            finish()
        }

    }
}