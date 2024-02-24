package com.example.mybmi_calculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mybmi_calculator.databinding.ActivityResultBinding
import java.lang.Math.round
import kotlin.math.pow

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val height = intent.getIntExtra("height", 0)
        val weight = intent.getIntExtra("weight", 0)

        var value = weight / (height / 100.0).pow(2.0)
        value = (round(value*10) / 10).toDouble()

        var resultText = ""
        var resImage = 0
        var resColor = 0

        if (value < 18.5) {
            resultText = "저체중"
            resImage = R.drawable.iv1
            resColor = Color.YELLOW
        } else if (value >= 18.5 && value < 23.0) {
            resultText = "정상제충"
            resImage = R.drawable.iv2
            resColor = Color.GREEN
        } else if (value >= 23.0 && value < 25.0) {
            resultText = "과체중"
            resImage = R.drawable.iv1
            resColor = Color.BLACK
        } else if (value >= 25.0 && value < 30.0) {
            resultText = "경도 비만"
            resImage = R.drawable.iv3
            resColor = Color.CYAN
        } else if (value >= 30.0 && value < 35.0) {
            resultText = "중정도 비만"
            resImage = R.drawable.iv4
            resColor = Color.MAGENTA
        } else {
            resultText = "고도비만"
            resImage = R.drawable.iv5
            resColor = Color.RED
        }

        binding.value.text = value.toString()
        binding.text.text = resultText
        binding.text.setTextColor(resColor)
        binding.imageView.setImageResource(resImage)

        binding.button.setOnClickListener{
            finish()
        }

    }
}