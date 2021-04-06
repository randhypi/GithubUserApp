package com.randhypi.githubuserapp.ui


import android.annotation.SuppressLint
import android.content.ContentResolver
import android.graphics.Color
import android.net.Uri
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.randhypi.githubuserapp.R
import com.randhypi.githubuserapp.R.string.*
import com.randhypi.githubuserapp.adapter.SectionsPagerAdapter
import com.randhypi.githubuserapp.data.UserTable.Companion.CONTENT_URI
import com.randhypi.githubuserapp.databinding.DetailFragmentBinding
import com.randhypi.githubuserapp.model.User
import com.randhypi.githubuserapp.repository.UserRepository
import com.randhypi.githubuserapp.vm.DatabaseViewModel
import com.randhypi.githubuserapp.vm.DatabaseViewModelFactory
import com.randhypi.githubuserapp.vm.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var contentResolver: ContentResolver
    private lateinit var viewModelFactory: DatabaseViewModelFactory
    private lateinit var uriWithId: Uri
    private var statusfavorite: Boolean = false

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

        val navController = findNavController()


        activity?.onBackPressedDispatcher?.addCallback(requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (navController.currentDestination?.id == R.id.action_favoriteFragment_to_detailFragment ){
                        navController.popBackStack()
                    }else  {
                        navController.popBackStack()
                    }

                   // view.findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                }
            })
        binding.myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
        binding.myToolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }

        val dataName = DetailFragmentArgs.fromBundle(arguments as Bundle).name
        if (dataName != null) {
            showData(dataName)
            setStatusFavorite(dataName)

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
        detailViewModel = DetailViewModel()
        detailViewModel.setUserDetail(username)
        detailViewModel.getUserDetail().observe(viewLifecycleOwner, { item ->

            if (item != null) {
                Log.d(TAG, item[0].username.toString())
                Glide.with(requireContext())
                    .load(item[0].avatar)
                    .into(binding.imgDetail)

                btnFav(item[0])
                binding.tvNameDetail.text = item[0].name
                binding.tvUsernameDetail.text = item[0].username
                showLoading(false)
            } else {
                Toast.makeText(context, "Data Not Found", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun setStatusFavorite(dataName: String) {
        viewModelFactory = DatabaseViewModelFactory(activity?.application!!)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(DatabaseViewModel::class.java)
        viewModel.getUserFavorite.observe(viewLifecycleOwner, { favorite ->
            val result = favorite.find {
                it.username == dataName
            }
            Log.d(TAG, "Hasil result setstatusfav $result")
            Log.d(TAG, "$dataName = $result")
            if (result != null) {
                detailViewModel.setStateStatusFav(true)
                Log.d(TAG, "set status favorite $statusfavorite")
            } else {
                detailViewModel.setStateStatusFav(false)
                Log.d(TAG, "set status favorite $statusfavorite")
            }
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun btnFav(user: User) {
        viewModelFactory = DatabaseViewModelFactory(activity?.application!!)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(DatabaseViewModel::class.java)
        detailViewModel.stateStatusFav.observe(viewLifecycleOwner, { statusfavorite ->
            viewModel.getUserFavorite.observe(viewLifecycleOwner, { favorite ->
                lifecycleScope.launch {
                    if (statusfavorite == true) {
                        withContext(Dispatchers.Main) {
                            try{
                                binding.btnFav.setImageResource(R.drawable.ic_favorite_24)
                                binding.btnFav.setOnClickListener {
                                    val result = favorite.find {
                                        it.username == user.username
                                    }
                                    if (result != null) {
                                        uriWithId =
                                            Uri.parse(CONTENT_URI.toString() + "/" + result.id)
                                        contentResolver = context?.contentResolver!!
                                        UserRepository.delUserFav(contentResolver, uriWithId)
                                        detailViewModel.setStateStatusFav(false)
                                        Toast.makeText(context,"${result.name} is now deteled",Toast.LENGTH_SHORT).show()
                                        Log.d(
                                            TAG,
                                            "button clicked and $statusfavorite delete in id = $result.id"
                                        )
                                    }
                                }
                                Log.d(TAG, "and now  status is $statusfavorite and id =  ")
                            }catch (e: IndexOutOfBoundsException){
                                    Log.d(TAG,e.toString())

                            }

                        }
                    } else if (statusfavorite == false) {
                        withContext(Dispatchers.Main) {
                            binding.btnFav.setImageResource(com.randhypi.githubuserapp.R.drawable.ic_favorite_border_24)
                            binding.btnFav.drawable.mutate().setTint(R.color.primaryTextColor)
                            binding.btnFav.setOnClickListener {
                                contentResolver = context?.contentResolver!!
                                    UserRepository.insertUserFav(
                                        user,
                                        requireContext(),
                                        contentResolver
                                    )
                                    Toast.makeText(context,"${user.username} is now favorite",Toast.LENGTH_SHORT).show()
                                    detailViewModel.setStateStatusFav(true)
                                Log.d(TAG, "button clicked and $statusfavorite insert ")
                            }
                            Log.d(TAG, "and now  status is $statusfavorite")
                        }
                    }
                }
            })
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