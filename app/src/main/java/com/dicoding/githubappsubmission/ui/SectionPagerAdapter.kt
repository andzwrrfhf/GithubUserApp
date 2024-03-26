package com.dicoding.githubappsubmission.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(position, username)
    }

    override fun getItemCount(): Int {
        return 2
    }
}
