package com.randhypi.githubuserapp.ui


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.randhypi.githubuserapp.R
import com.randhypi.githubuserapp.R.string.*
import com.randhypi.githubuserapp.adapter.SectionsPagerAdapter
import com.randhypi.githubuserapp.databinding.DetailFragmentBinding
import com.randhypi.githubuserapp.vm.DetailViewModel

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
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(),object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                view.findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
            }
        })
        binding.myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
        binding.myToolbar.setNavigationOnClickListener {
            view.findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
        }

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

        val tabs: TabLayout = view.findViewById(R.id.tabs)
        tabs.setSelectedTabIndicatorColor(Color.WHITE)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString((TAB_TITLES[position]))
        }.attach()

        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        activity?.actionBar?.title = getString(detail)


    }




    private fun showData(username: String) {
        detailViewModel.setUserDetail(username)
        detailViewModel.getUserDetail().observe(viewLifecycleOwner, { item ->

            if (item != null) {
                Log.d(TAG, item[0].username.toString())
                Glide.with(requireContext())
                    .load(item[0].avatar)
                    .into(binding.imgDetail)

                binding.tvNameDetail.text = item[0].name
                binding.tvUsernameDetail.text = item[0].username
                showLoading(false)
            } else {
                Toast.makeText(context, "Data Not Found", Toast.LENGTH_LONG).show()
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