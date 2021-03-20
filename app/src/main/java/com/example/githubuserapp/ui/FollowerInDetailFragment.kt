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
import com.example.githubuserapp.adapter.ListFollowerAdapter
import com.example.githubuserapp.databinding.FragmentFollowerInDetailBinding
import com.example.githubuserapp.vm.DetailViewModel


class FollowerInDetailFragment : Fragment() {

    private var _binding: FragmentFollowerInDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by activityViewModels()
    private lateinit var listFollowerAdapter: ListFollowerAdapter

    companion object {
        val TAG = FollowerInDetailFragment::class.java.simpleName
        private const val ARG_USERNAME = "username"

        @JvmStatic
        fun newInstance(username: String) =
            FollowerInDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerInDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME)
        if (username != null) {
            showRecyler(username)
        }
    }

    private fun showRecyler(username: String) {
        listFollowerAdapter = ListFollowerAdapter()
        listFollowerAdapter.notifyDataSetChanged()
        binding.rvFollowerInDetail.layoutManager = LinearLayoutManager(context)
        binding.rvFollowerInDetail.adapter = listFollowerAdapter
        detailViewModel.setFollowers(username)
        detailViewModel.getFollowers().observe(viewLifecycleOwner, { items ->
            if (items != null) {
                listFollowerAdapter.setData(items)
            } else {
                Toast.makeText(context, "Not Found", Toast.LENGTH_LONG).show()
                binding.tvNotFollower.visibility = View.VISIBLE
            }
        })
    }
}
