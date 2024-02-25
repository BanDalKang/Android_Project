package com.example.mylottto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    // 각 버튼과 넘버 피커에 대한 참조를 지연 초기화로 선언
    private val clearButton by lazy { findViewById<Button>(R.id.btn_clear) }
    private val addButton by lazy { findViewById<Button>(R.id.btn_add) }
    private val runButton by lazy { findViewById<Button>(R.id.btn_run) }
    private val numPick by lazy { findViewById<NumberPicker>(R.id.np_num) }

    // 선택된 숫자를 나타내는 텍스트 뷰 리스트
    private val numTextViewList : List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.tv_num1)
            ,findViewById(R.id.tv_num2)
            ,findViewById(R.id.tv_num3)
            ,findViewById(R.id.tv_num4)
            ,findViewById(R.id.tv_num5)
            ,findViewById(R.id.tv_num6))
    }

    // 룰렛을 돌린 여부 변수
    private var didRun = false

    // 선택된 숫자들의 집합
    private val pickNumSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 넘버 피커의 범위를 설정
        numPick.minValue = 1
        numPick.maxValue = 45

        // 각 버튼에 대한 초기화
        initRunButton()
        initAddButton()
        initClearButton()
    }

    // 넘버 피커로 번호를 추가하는 버튼 클릭리스너
    private fun initAddButton() {
        addButton.setOnClickListener {
            when {
                didRun -> showToast("초기화 후에 시도해주세요.")
                pickNumSet.size >= 6 -> showToast("최대 6개까지 선택할 수 있습니다.")
                pickNumSet.contains(numPick.value) -> showToast("이미 선택한 숫자입니다.")
                else -> {
                    // 선택된 숫자를 텍스트 뷰에 표시하고 배경색을 설정
                    val textView = numTextViewList[pickNumSet.size]
                    textView.isVisible = true
                    textView.text = numPick.value.toString()
                    setNumBack(numPick.value, textView)
                    pickNumSet.add(numPick.value)
                }
            }
        }
    }

    // 텍스트 뷰의 배경색을 설정
    private fun setNumBack(number: Int, textView: TextView) {
        val background = when (number) {
            in 1..10 -> R.drawable.circle_yellow
            in 11..20 -> R.drawable.circle_blue
            in 21..30 -> R.drawable.circle_red
            in 31..40 -> R.drawable.circle_gray
            else -> R.drawable.circle_green
        }
        textView.background = ContextCompat.getDrawable(this, background)
    }

    // 초기화 버튼 클릭리스너
    private fun initClearButton() {
        clearButton.setOnClickListener {
            // 선택된 숫자들과 텍스트 뷰들을 초기화
            pickNumSet.clear()
            numTextViewList.forEach { it.isVisible = false }
            didRun = false
            numPick.value = 1
        }
    }

    // 자동생성 버튼 클릭리스너
    private fun initRunButton() {
        runButton.setOnClickListener {
            // 랜덤한 숫자들을 선택하여 텍스트 뷰에 표시
            val list = getRandom()
            didRun = true
            list.forEachIndexed { index, number ->
                val textView = numTextViewList[index]
                textView.text = number.toString()
                textView.isVisible = true
                setNumBack(number, textView)
            }
        }
    }

    // 중복되지 않은 랜덤한 숫자 리스트를 생성
    private fun getRandom(): List<Int> {
        val numbers = (1..45).filter { it !in pickNumSet }
        return (pickNumSet + numbers.shuffled().take(6 - pickNumSet.size)).sorted()
    }

    // 토스트 메시지
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}