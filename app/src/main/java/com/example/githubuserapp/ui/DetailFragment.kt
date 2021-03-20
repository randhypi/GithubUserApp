package com.example.githubuserapp.ui


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.R.string.*
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.databinding.DetailFragmentBinding
import com.example.githubuserapp.vm.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.Exception

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by activityViewModels()

    companion object {
        val TAG = DetailFragment::class.java.simpleName
        @StringRes
        private val TAB_TITLES = intArrayOf(
            tab_text_1,
            tab_text_2
        )
    }
    
    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)
        val dataName = DetailFragmentArgs.fromBundle(arguments as Bundle).name
        if (dataName != null) {
            showData(dataName)
        }
        val sectionsPagerAdapter = dataName?.let {
            SectionsPagerAdapter(
                context as FragmentActivity,
                username = it
            )
        }

        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
//        viewPager.offscreenPageLimit = 1

        val tabs: TabLayout = view.findViewById(R.id.tabs)
        tabs.setSelectedTabIndicatorColor(Color.WHITE)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString((TAB_TITLES[position]))
        }.attach()
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        activity?.actionBar?.title = getString(detail)

        (activity as AppCompatActivity).supportActionBar?.setTitle(detail)
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
    }



  private  fun showData(username: String){
        detailViewModel.setUserDetail(username)
        detailViewModel.getUserDetail().observe(viewLifecycleOwner, { item ->

                if (item != null) {
                    Log.d(TAG,item[0].username.toString())
                    Glide.with(requireContext())
                        .load(item[0].avatar)
                        .into(binding.imgDetail)

                    binding.tvNameDetail.text = item[0].name
                    binding.tvUsernameDetail.text= item[0].username
                    showLoading(false)
                }else{
                    Toast.makeText(context,"Data Not Found",Toast.LENGTH_LONG).show()
            }

        })
    }

    //    Menampilkan loading
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}