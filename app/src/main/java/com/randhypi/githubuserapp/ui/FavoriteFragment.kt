package com.randhypi.githubuserapp.ui


import android.database.ContentObserver
import android.media.tv.TvContract.Channels.CONTENT_URI
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.randhypi.githubuserapp.R
import com.randhypi.githubuserapp.adapter.FavoriteAdapter
import com.randhypi.githubuserapp.databinding.FragmentFavoriteBinding
import com.randhypi.githubuserapp.provider.UserProvider


class FavoriteFragment : Fragment() {


    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteAdapter
    private lateinit var userProvider: UserProvider


    companion object {
        val TAG = FavoriteFragment::class.java.simpleName
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(),
            object : OnBackPressedCallback(
                true
            ) {
                override fun handleOnBackPressed() {
                    view.findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
                }
            })
        binding.myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
        binding.myToolbar.setNavigationOnClickListener {
            view.findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        adapter = FavoriteAdapter()
        binding.recyclerView.adapter = adapter



        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {

            }
        }

        context?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)


    }



    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.recyclerView, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}