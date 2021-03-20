package com.example.githubuserapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapter.ListFollowerAdapter
import com.example.githubuserapp.databinding.FragmentFollowerInDetailBinding
import com.example.githubuserapp.vm.DetailViewModel


class FollowerInDetailFragment : Fragment() {

    private var _binding: FragmentFollowerInDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by activityViewModels()
    private lateinit var listFollowerAdapter: ListFollowerAdapter

    companion object{
        val TAG = FollowerInDetailFragment::class.java.simpleName
        private const val ARG_USERNAME = "username"
        private const val ARG_STATUS = "status"
        @JvmStatic
        fun newInstance(username: String,status: String) =
            FollowerInDetailFragment().apply {
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
        _binding = FragmentFollowerInDetailBinding.inflate(inflater, container, false)
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
        listFollowerAdapter = ListFollowerAdapter()
        listFollowerAdapter.notifyDataSetChanged()
        binding.rvFollowerInDetail.layoutManager = LinearLayoutManager(context)
        binding.rvFollowerInDetail.adapter = listFollowerAdapter

        detailViewModel.setFollowers(username,status)

        detailViewModel.getFollowers().observe(viewLifecycleOwner, { items ->
            if (items != null) {
                listFollowerAdapter.setData(items)
                Log.d(HomeFragment.TAG, items.get(0)?.username!!)
            }
        })

    }


}