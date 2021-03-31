package com.randhypi.githubuserapp.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.randhypi.githubuserapp.ui.FollowerInDetailFragment
import com.randhypi.githubuserapp.ui.FollowingInDetailFragment


class SectionsPagerAdapter(activity: FragmentActivity, var username: String) : FragmentStateAdapter(
    activity
) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowerInDetailFragment.newInstance(username = username)
                Log.d(SectionsPagerAdapter::class.java.simpleName, username)
            }
            1 -> {
                fragment = FollowingInDetailFragment.newInstance(username = username)
                Log.d(SectionsPagerAdapter::class.java.simpleName, username)
            }
        }
        return fragment as Fragment
    }


}