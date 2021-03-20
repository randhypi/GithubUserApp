package com.example.githubuserapp.ui


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapter.ListUserAdapter
import com.example.githubuserapp.databinding.FragmentHomeBinding
import com.example.githubuserapp.model.User
import com.example.githubuserapp.vm.DetailViewModel
import com.example.githubuserapp.vm.MainViewModel


class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var detailViewModel: DetailViewModel
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


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSearchUser.setOnClickListener {
            val user = binding.editUser.text.toString()
            if (user.isEmpty()) return@setOnClickListener
            showLoading(true)
            mainViewModel.setUserAll(user)
        }


        showRecyler()
    }


    //    Menampilkan loading
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    private fun showRecyler() {
        mainViewModel = MainViewModel()

        listUserAdapter = ListUserAdapter()
        listUserAdapter.notifyDataSetChanged()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = listUserAdapter

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.setUser()

        binding.editUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    mainViewModel.setUser()
                } else {

                }
            }
            override fun afterTextChanged(p0: Editable?) {}

        })

        mainViewModel.getUserAll().observe(viewLifecycleOwner, { weatherItems ->
            if (weatherItems != null) {
                listUserAdapter.setData(weatherItems)
                Log.d(TAG, weatherItems.get(0).name)
                showLoading(false)
            }
        })

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                view?.let { showSelectedUser(data, it) }
            }
        })
    }

    private fun showSelectedUser(user: User,view: View) {
        Toast.makeText(context, "Kamu memilih ${user.name}", Toast.LENGTH_SHORT).show()

        detailViewModel = DetailViewModel()

        val toDetail = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
        toDetail.name = user.name

        view.findNavController().navigate(toDetail)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


