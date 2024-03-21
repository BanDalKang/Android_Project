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

        val nameEt: EditText = findViewById(R.id.name_ET)
        val idEt: EditText = findViewById(R.id.id_ET)
        val pwdEt: EditText = findViewById(R.id.pwd_ET)
        val signupBtn: Button = findViewById(R.id.signup_btn)

        // 회원가입 버튼
        signupBtn.setOnClickListener {
            val name = nameEt.text.toString()
            val id = idEt.text.toString()
            val pwd = pwdEt.text.toString()

            if (name.isNotEmpty() && id.isNotEmpty() && pwd.isNotEmpty()) {
                Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("pwd", pwd)
                // 이전 액티비티로 돌아가기 전에 현재 액티비티에서 처리한 결과를 담은 인텐트를 설정
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@SignUpActivity, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}