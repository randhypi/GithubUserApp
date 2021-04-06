package com.randhypi.githubuserapp.ui

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.randhypi.githubuserapp.R
import com.randhypi.githubuserapp.service.AlarmReceiver


/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var ALARM: String

    private lateinit var alarmPreference: SwitchPreference

    private lateinit var alarmReceiver: AlarmReceiver

    companion object{
         val TAG = SettingsFragment::class.java
        private const val DEFAULT_VALUE = "Tidak Ada"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        (activity as AppCompatActivity?)!!.supportActionBar?.show()
        (activity as AppCompatActivity?)!!.supportActionBar?.title = getString(R.string.setting)
        val navController = findNavController()

        var text = SpannableString( (activity as AppCompatActivity?)!!.supportActionBar?.title)
        text.setSpan(ForegroundColorSpan(Color.BLACK), 0, text.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        (activity as AppCompatActivity?)!!.supportActionBar?.title = text
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(),
            object : OnBackPressedCallback(
                true
            ) {
                override fun handleOnBackPressed() {
                    navController.navigateUp()
                }
            })

        addPreferencesFromResource(R.xml.preferences)
        init()
    }

    override fun onResume() {
        super.onResume()
        alarmReceiver = AlarmReceiver()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun init() {
        ALARM = resources.getString(R.string.key_alarm)
        alarmPreference = findPreference<SwitchPreference>(ALARM) as SwitchPreference

    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == getString(R.string.key_alarm)){
           alarmPreference.isChecked = sharedPreferences.getBoolean(key,false)
            Toast.makeText(context,alarmPreference.isChecked.toString(),Toast.LENGTH_SHORT).show()

            if (alarmPreference.isChecked){
                context?.let { alarmReceiver.setRepeatingAlarm(it, AlarmReceiver.TYPE_REPEATING, "Time Is Open This Application") }
            }else{
                context?.let { alarmReceiver.cancelAlarm(it) }
            }
        }
    }


}