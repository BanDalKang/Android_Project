package com.example.introduction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val idEditText: EditText = findViewById(R.id.id_ET)
        val pwdEditText: EditText = findViewById(R.id.pwd_ET)
        val signInButton: Button = findViewById(R.id.signin_btn)
        val signUpButton: Button = findViewById(R.id.signup_btn)

        // 로그인 버튼
        signInButton.setOnClickListener {
            val id = idEditText.text.toString()
            val pwd = pwdEditText.text.toString()

            if (id.isNotEmpty() && pwd.isNotEmpty()) {
                Toast.makeText(this@SignInActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            } else {
                Toast.makeText(this@SignInActivity, "아이디/비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        // 회원가입 버튼
        signUpButton.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
