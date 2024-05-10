package com.example.imagesearch.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.imagesearch.databinding.ActivityMainBinding
import com.example.imagesearch.presentation.bookmark.BookmarkListFragment
import com.example.imagesearch.presentation.search.SearchListFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewPagerAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        onCreatedViewPager()

        onConnectTabLayout()

        binding.btnSearchScrollTop.setOnClickListener {
            when (val currentFragment =
                viewPagerAdapter.getFragment(binding.viewPager.currentItem)) {
                is SearchListFragment -> {
                    currentFragment.smoothScrollToTop()
                }

                is BookmarkListFragment -> {
                    currentFragment.smoothScrollToTop()
                }

                else -> Unit
            }
        }
    }

    private fun onCreatedViewPager() = with(binding) {
        viewPager.adapter = viewPagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        })
    }

    private fun onConnectTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setText(viewPagerAdapter.getTitle(position))
            tab.setIcon(viewPagerAdapter.getIcon(position))
        }.attach()
    }
}