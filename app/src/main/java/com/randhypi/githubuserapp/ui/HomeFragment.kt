package com.randhypi.githubuserapp.ui


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.randhypi.githubuserapp.R
import com.randhypi.githubuserapp.adapter.ListUserAdapter
import com.randhypi.githubuserapp.databinding.FragmentHomeBinding
import com.randhypi.githubuserapp.model.User
import com.randhypi.githubuserapp.vm.DetailViewModel
import com.randhypi.githubuserapp.vm.MainViewModel


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
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        binding.myToolbar.inflateMenu(R.menu.menu)

        showLoading(true)

        binding.myToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search -> {
                    searchMenu()
                    true
                }
                R.id.favorite -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
                    view.findNavController().navigate(action)
                    true
                }
                R.id.settings -> {
                    val actiion = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
                    view.findNavController().navigate(actiion)
                    true
                }
                else -> false
            }
        }
        showRecyler()

    }


    private fun searchMenu() {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.myToolbar.menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                Log.d(TAG, query)
                Toast.makeText(context, query, Toast.LENGTH_SHORT).show()
                mainViewModel.setSearchUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d(TAG, newText)
                if (newText.isNullOrEmpty()) {
                    showRecyler()
                }
                return false
            }
        })
    }
//

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

        mainViewModel.setUserAll()

        mainViewModel.getUserAll().observe(viewLifecycleOwner, { items ->
            if (items != null) {
                listUserAdapter.setData(items)
                showLoading(false)
            } else {
                Toast.makeText(context, "Not Found", Toast.LENGTH_LONG).show()
                mainViewModel.setUserAll()
            }
        })

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                view?.let { showSelectedUser(data, it) }
            }
        })
    }

    private fun showSelectedUser(user: User, view: View) {
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


