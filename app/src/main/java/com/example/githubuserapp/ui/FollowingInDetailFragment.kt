package com.example.githubuserapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
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


        @JvmStatic
        fun newInstance(username: String) =
            FollowingInDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME,username)
                }
            }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingInDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username =  arguments?.getString(ARG_USERNAME)
            if (username != null) {
                showRecyler(username)
            }
    }

    private fun showRecyler(username: String) {
        listFollowingAdapter = ListFollowingAdapter()
        listFollowingAdapter.notifyDataSetChanged()

        binding.rvFollowingInDetail.layoutManager = LinearLayoutManager(context)
        binding.rvFollowingInDetail.adapter = listFollowingAdapter

        detailViewModel.setFollowing(username)

        detailViewModel.getFollowing().observe(viewLifecycleOwner, { items ->
            if (items != null) {
                binding.tvNotFollowing.visibility = View.GONE
                listFollowingAdapter.setData(items)
                Log.d(HomeFragment.TAG, items[0].name)
            }else {
                Toast.makeText(context, "Not Found", Toast.LENGTH_LONG).show()
                detailViewModel.setFollowing(username)
                binding.tvNotFollowing.visibility = View.VISIBLE
            }
        })
    }
}