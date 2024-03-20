package com.example.activitylifecycle

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        Log.i(TAG, "onCreate()")

        val btn = findViewById<Button>(R.id.buttonFirstActivity)
        btn.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        val btn_call3 = findViewById<Button>(R.id.buttonThirdActivity)
        btn_call3.setOnClickListener {
            val edit_text = findViewById<EditText>(R.id.edit_data)
            val strData = edit_text.text.toString()
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra("dataFromFirstActivity",strData)
            startActivity(intent)
        }

    }

    fun doOnBtnClick(view: View) {
        when (view.getId()) {
            R.id.buttonDialActivity -> {                 // 114 전화번호로 다이얼 작업을 수행할 수 있도록 인텐트 설정
                val call_intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:114"))
                startActivity(call_intent)
            }
            R.id.buttonMapActivity -> {                // 주어진 위도,경도 위치로 지도를 보여줄 수 있도록 인텐트 설정
                val map_intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.565350, 127.01445"))
                startActivity(map_intent)
            }
        }
    }


    val TAG = "FirstActivity_Lifecycle"

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy()")
    }
}