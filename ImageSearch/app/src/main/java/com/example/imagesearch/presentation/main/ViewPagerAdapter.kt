package com.example.imagesearch.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.imagesearch.R
import com.example.imagesearch.presentation.bookmark.BookmarkListFragment
import com.example.imagesearch.presentation.search.SearchListFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private val fragments = listOf(
        MainTab(
            fragment = SearchListFragment.newInstance(),
            title = R.string.fragment_search,
            icon = R.drawable.selector_tab_search
        ),
        MainTab(
            fragment = BookmarkListFragment.newInstance(),
            title = R.string.fragment_favorite,
            icon = R.drawable.selector_tab_storage
        )
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position].fragment
    }

    fun getFragment(position: Int): Fragment {
        return fragments[position].fragment
    }

    fun getTitle(position: Int): Int {
        return fragments[position].title
    }

    fun getIcon(position: Int): Int {
        return fragments[position].icon
    }

}