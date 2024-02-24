package com.example.mybmi_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mybmi_calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            if(binding.height.text.isEmpty()||binding.weight.text.isEmpty()){
                Toast.makeText(this,"모두 입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                val height : Int = binding.height.text.toString().toInt()
                val weight : Int = binding.weight.text.toString().toInt()
                val intent = Intent(this,ResultActivity::class.java)
                intent.putExtra("height", height)
                intent.putExtra("weight", weight)
                startActivity(intent)
            }
        }
    }
}
