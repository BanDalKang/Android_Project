package com.example.mymbtitest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// ViewPagerAdapter 클래스: ViewPager에 프래그먼트를 제공
class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    // ViewPager에 표시할 프래그먼트 개수를 반환
    override fun getItemCount(): Int {
        return 4 // 총 4개의 질문이 있으므로 반환 값은 4
    }

    // position에 해당하는 프래그먼트를 생성하여 반환
    override fun createFragment(position: Int): Fragment {
        // QuestionFragment.newInstance() 메서드를 사용하여 position에 해당하는 질문을 가진 QuestionFragment 인스턴스를 생성
        return QuestionFragment.newInstance(position)
    }
}