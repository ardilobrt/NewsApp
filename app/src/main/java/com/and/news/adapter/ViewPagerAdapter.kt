package com.and.news.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.and.news.ui.onboarding.fragment.FirstOnBoardingFragment
import com.and.news.ui.onboarding.fragment.SecondOnBoardingFragment
import com.and.news.ui.onboarding.fragment.ThirdOnBoardingFragment

class ViewPagerAdapter(fm: FragmentManager, lf: Lifecycle) : FragmentStateAdapter(fm, lf) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FirstOnBoardingFragment()
            1 -> fragment = SecondOnBoardingFragment()
            2 -> fragment = ThirdOnBoardingFragment()
        }
        return fragment as Fragment
    }
}
