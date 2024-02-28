package com.example.mymbtitest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2

class TestActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    val questionnaireResults = QuestionnaireResults()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // ViewPager2 설정
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(this)
        viewPager.isUserInputEnabled = false
    }

    // 다음 질문으로 이동하는 메서드
    fun moveToNextQuestion() {
        Log.d("jblee","viewPager.currentItem = ${viewPager.currentItem}")

        if (viewPager.currentItem==3) {
            // 마지막 질문일 경우 결과 화면으로 이동
            Log.d("jblee","result = ${ArrayList(questionnaireResults.results)}")
            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("results", ArrayList(questionnaireResults.results))
            startActivity(intent)
        } else {
            // 다음 질문으로 이동
            val nextItem = viewPager.currentItem + 1
            if (nextItem < viewPager.adapter?.itemCount ?: 0) {
                viewPager.setCurrentItem(nextItem, true)
            }
        }
    }
}

// 질문에 대한 결과를 저장하는 클래스
class QuestionnaireResults {
    val results = mutableListOf<Int>()

    // 사용자 응답을 결과 목록에 추가하는 메서드
    fun addResponses(responses: List<Int>) {
        val mostFrequent = responses.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        mostFrequent?.let { results.add(it) }
    }
}