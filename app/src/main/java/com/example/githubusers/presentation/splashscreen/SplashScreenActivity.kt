package com.example.githubusers.presentation.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubusers.databinding.ActivitySplashScreenBinding
import com.example.githubusers.presentation.main.MainActivity
import com.example.githubusers.presentation.preferences.SettingPreferences
import com.example.githubusers.presentation.preferences.SettingPreferencesModelFactory
import com.example.githubusers.presentation.preferences.SettingPreferencesViewModel
import com.example.githubusers.presentation.preferences.dataStore

class SplashScreenActivity : AppCompatActivity() {

    private var _splashScreenActivityBinding : ActivitySplashScreenBinding ?= null
    private val binding get() = _splashScreenActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _splashScreenActivityBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val viewModel = ViewModelProvider(this, SettingPreferencesModelFactory(pref)).get(
            SettingPreferencesViewModel::class.java
        )

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        } ,3000)


        viewModel.getThemeSetting().observe(this) { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}