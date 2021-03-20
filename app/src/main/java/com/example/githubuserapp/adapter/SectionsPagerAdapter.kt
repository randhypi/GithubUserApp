package com.example.githubuserapp.adapter

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserapp.ui.FollowerInDetailFragment
import com.example.githubuserapp.ui.FollowingInDetailFragment


class SectionsPagerAdapter(activity: AppCompatActivity,var username: String) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when(position){
            0 ->{
                fragment = FollowerInDetailFragment.newInstance(username = username,"followers")
                Log.d(SectionsPagerAdapter::class.java.simpleName,username)
            }
            1 -> {
                fragment = FollowingInDetailFragment.newInstance(username = username,"following")
                Log.d(SectionsPagerAdapter::class.java.simpleName,username)
            }
        }
        return fragment as Fragment
    }

}