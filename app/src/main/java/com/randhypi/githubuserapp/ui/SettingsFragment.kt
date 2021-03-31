package com.randhypi.githubuserapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.randhypi.githubuserapp.R
import com.randhypi.githubuserapp.databinding.FragmentSettingsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    companion object{
         val TAG = SettingsFragment::class.java
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(),object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                view.findNavController().navigate(R.id.action_settingsFragment_to_homeFragment)
            }
        })
        binding.myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
        binding.myToolbar.setNavigationOnClickListener {
            view.findNavController().navigate(R.id.action_settingsFragment_to_homeFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}