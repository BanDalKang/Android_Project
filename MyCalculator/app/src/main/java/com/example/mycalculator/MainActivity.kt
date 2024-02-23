package com.example.mycalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var inputEditText: EditText? = null
    private var additionButton: Button? = null
    private var subtractionButton: Button? = null
    private var multiplicationButton: Button? = null
    private var divisionButton: Button? = null
    private var resultTextView: TextView? = null
    private var calculator: Calculator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Calculator 클래스 인스턴스 생성
        calculator = Calculator()

        // 뷰 찾기
        inputEditText = findViewById(R.id.inputEt)
        additionButton = findViewById(R.id.additionBtn)
        subtractionButton = findViewById(R.id.subtractionBtn)
        multiplicationButton = findViewById(R.id.multiplicationBtn)
        divisionButton = findViewById(R.id.divisionBtn)
        resultTextView = findViewById(R.id.resultNumTv)
        resultTextView?.text = "0.0"

        // 사칙연산 버튼 클릭 리스너 설정
        additionButton?.setOnClickListener { performCalculation('+') }
        subtractionButton?.setOnClickListener { performCalculation('-') }
        multiplicationButton?.setOnClickListener { performCalculation('*') }
        divisionButton?.setOnClickListener { performCalculation('/') }
    }

    // 계산하는 메서드
    private fun performCalculation(operator: Char) {
        val num1 = resultTextView?.text.toString().toDoubleOrNull() ?: return
        val num2 = inputEditText?.text.toString().toDoubleOrNull() ?: return
        val result = when (operator) {
            '+' -> calculator?.add(num1, num2)
            '-' -> calculator?.subtract(num1, num2)
            '*' -> calculator?.multiply(num1, num2)
            '/' -> {
                if (num2 == 0.0) {
                    resultTextView?.text = "Error! Cannot divide by zero."
                    return
                }
                calculator?.divide(num1, num2)
            }
            else -> return
        }
        // 결과 TextView에 결과 표시
        resultTextView?.text = result.toString()
    }
}