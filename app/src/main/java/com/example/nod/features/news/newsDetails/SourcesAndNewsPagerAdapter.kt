package com.example.nod.features.news.newsDetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.nod.features.news.newsList.NewsListFragment
import com.example.nod.features.news.sources.SourcesFragment

class SourcesAndNewsPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SourcesFragment.newInstance()
            1 -> NewsListFragment.newInstance()
            else -> throw RuntimeException("Invalid position")
        }
    }

    override fun getCount(): Int {
        return PAGER_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "SOURCES"
            1 -> "ALL NEWS"
            else -> throw RuntimeException("Invalid position")
        }
    }

    companion object {
        const val PAGER_COUNT = 2
    }
}