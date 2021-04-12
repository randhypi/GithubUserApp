package com.randhypi.consumerapp.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.randhypi.consumerapp.adapter.FavoriteAdapter
import com.randhypi.consumerapp.databinding.FragmentFavoriteBinding
import com.randhypi.consumerapp.model.User
import com.randhypi.consumerapp.vm.DatabaseViewModel
import com.randhypi.consumerapp.vm.DatabaseViewModelFactory


class FavoriteFragment : Fragment() {


    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModelFactory: DatabaseViewModelFactory

    companion object {
        val TAG = FavoriteFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
      return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



       loadData()


    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData(){

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    System.exit(0)
                }
            })

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()
        binding.recyclerView.adapter = adapter

        viewModelFactory = DatabaseViewModelFactory(activity?.application!!)


        val viewModel = ViewModelProvider(this, viewModelFactory).get(DatabaseViewModel::class.java)
        viewModel.userFavorite.observe(viewLifecycleOwner, {
            Log.d(TAG, "ada $it")
            adapter.setData(it)
        })

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                view?.let { showSelectedUser(data, it) }
            }
        })

    }

    private fun showSelectedUser(user: User, view: View) {
        Toast.makeText(context, "Kamu memilih ${user.name}", Toast.LENGTH_SHORT).show()

        val toDetail = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment()
        toDetail.name = user.username

        view.findNavController().navigate(toDetail)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
        val hide: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hide.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}


