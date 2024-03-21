package com.example.introduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val nameEditText: EditText = findViewById(R.id.name_ET)
        val idEditText: EditText = findViewById(R.id.id_ET)
        val pwdEditText: EditText = findViewById(R.id.pwd_ET)
        val signUpButton: Button = findViewById(R.id.signup_btn)

        // 회원가입 버튼
        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()
            val pwd = pwdEditText.text.toString()

            if (name.isNotEmpty() && id.isNotEmpty() && pwd.isNotEmpty()) {
                Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@SignUpActivity, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}