package com.example.githubuserapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapter.ListUserAdapter
import com.example.githubuserapp.databinding.FragmentHomeBinding
import com.example.githubuserapp.model.User
import com.example.githubuserapp.vm.MainViewModel


class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
   private lateinit var listUserAdapter: ListUserAdapter



    companion object {
        val TAG = HomeFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        mainViewModel = MainViewModel()
        mainViewModel.setUserAll()
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        mainViewModel.getUserAll().observe(viewLifecycleOwner, { userItem ->

            if (userItem != null) {
                Log.d(TAG,userItem.toString())
                listUserAdapter = ListUserAdapter(userItem)
                binding.recyclerView.adapter = listUserAdapter
                Log.d(TAG,userItem.toString())
                showLoading(false)
            }else{
                Log.d(TAG, "Null")
            }

            listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    showSelectedUser(data)
                }
            })
        })


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearchUser.setOnClickListener {
            Log.d(TAG,"KLIKED")
//            mainViewModel.setUserAll()
        }
    }


//    Menampilkan loading
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    private fun showSelectedUser(user: User) {
        val move = Intent(context, DetailFragment::class.java)
        move.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(move)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}