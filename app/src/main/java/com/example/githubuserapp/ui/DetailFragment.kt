package com.example.githubuserapp.ui


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.databinding.DetailFragmentBinding
import com.example.githubuserapp.vm.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by activityViewModels()

    companion object {
        val TAG = DetailFragment::class.java.simpleName
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val dataName = DetailFragmentArgs.fromBundle(arguments as Bundle).name

        if (dataName != null) {
            showData(dataName)
        }


        val sectionsPagerAdapter = dataName?.let {
            SectionsPagerAdapter(
                activity as AppCompatActivity,
                username = it,
            )
        }

        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        viewPager.offscreenPageLimit = 2
        val tabs: TabLayout = view.findViewById(R.id.tabs)
        tabs.setSelectedTabIndicatorColor(Color.WHITE)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString((TAB_TITLES[position]))
        }.attach()

        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
    }



  private  fun showData(username: String){
        detailViewModel.setUserDetail(username)

        detailViewModel.getUserDetail().observe(viewLifecycleOwner, { item ->
            if (item != null) {
                Log.d(TAG,item[0].username)

                Glide.with(requireContext())
                    .load(item[0].avatar)
                    .into(binding.imgDetail)

                binding.tvNameDetail.text = item[0].name
                binding.tvUsernameDetail.text= item[0].username
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}