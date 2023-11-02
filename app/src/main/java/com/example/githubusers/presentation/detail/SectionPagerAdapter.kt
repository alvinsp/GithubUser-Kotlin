package com.example.githubusers.presentation.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubusers.presentation.follow.FollowFragment

class SectionPagerAdapter(activity: AppCompatActivity, val data : String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()

        val getTabs : String = when(position){
            0 -> FollowFragment.GET_FOLLOWERS
            1 -> FollowFragment.GET_FOLLOWING
            else -> ""
        }

        fragment.arguments = Bundle().apply {
            putString(FollowFragment.TAB_TITLES, getTabs)
            putString(FollowFragment.DATA, data)
        }
        return fragment
    }

}