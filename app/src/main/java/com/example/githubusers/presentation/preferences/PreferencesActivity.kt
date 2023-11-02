package com.example.githubusers.presentation.preferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubusers.R
import com.example.githubusers.databinding.ActivityPreferencesBinding
import com.google.android.material.switchmaterial.SwitchMaterial


class PreferencesActivity : AppCompatActivity() {

    private var _preferencesActivity : ActivityPreferencesBinding ?= null
    private val binding get() = _preferencesActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       _preferencesActivity = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        actionBar?.title = "Preferences"

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_dark_mode)
        val pref = SettingPreferences.getInstance(application.dataStore)
        val viewModel = ViewModelProvider(this,  SettingPreferencesModelFactory(pref))[SettingPreferencesViewModel::class.java]

        supportActionBar?.setDisplayShowCustomEnabled(true)


        viewModel.getThemeSetting().observe(this){ isDarkMode: Boolean ->
            if (isDarkMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }


        binding?.switchDarkMode?.setOnCheckedChangeListener { _, checked ->
            viewModel.saveThemeSetting(checked)
        }

    }
}