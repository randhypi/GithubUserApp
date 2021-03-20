package com.example.githubuserapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListFollowingAdapter
import com.example.githubuserapp.databinding.FragmentFollowingInDetailBinding
import com.example.githubuserapp.vm.DetailViewModel

class FollowingInDetailFragment : Fragment() {
    private var _binding: FragmentFollowingInDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by activityViewModels()
    private lateinit var listFollowingAdapter: ListFollowingAdapter

    companion object{
        val TAG = FollowingInDetailFragment::class.java.simpleName
        private const val ARG_USERNAME = "username"
        private const val ARG_STATUS = "status"

        @JvmStatic
        fun newInstance(username: String,status: String) =
            FollowingInDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME,username)
                    putString(ARG_STATUS,status)
                }
            }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingInDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username =  arguments?.getString(ARG_USERNAME)
        val status =  arguments?.getString(ARG_STATUS)
        if (username != null) {
            if (status != null) {
                showRecyler(username,status)
            }
        }

    }

    private fun showRecyler(username: String,status: String) {
        listFollowingAdapter = ListFollowingAdapter()
        listFollowingAdapter.notifyDataSetChanged()
        binding.rvFollowingInDetail.layoutManager = LinearLayoutManager(context)
        binding.rvFollowingInDetail.adapter = listFollowingAdapter

        detailViewModel.setFollowers(username,status)

        detailViewModel.getFollowers().observe(viewLifecycleOwner, { items ->
            if (items != null) {
                listFollowingAdapter.setData(items)
                Log.d(HomeFragment.TAG, items.get(0)?.username!!)
            }
        })

    }

}