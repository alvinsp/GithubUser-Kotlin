package com.example.githubusers.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.R
import com.example.githubusers.data.response.GithubResponseItem
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.presentation.detail.DetailUserActivity
import com.example.githubusers.presentation.favorite.FavoriteActivity
import com.example.githubusers.presentation.preferences.PreferencesActivity
import com.example.githubusers.presentation.preferences.SettingPreferences
import com.example.githubusers.presentation.preferences.SettingPreferencesModelFactory
import com.example.githubusers.presentation.preferences.SettingPreferencesViewModel
import com.example.githubusers.presentation.preferences.dataStore

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding : ActivityMainBinding ?= null
    private val binding get() = _activityMainBinding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var rvList : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_activityMainBinding?.root)


        supportActionBar?.title = "Github Users"

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        viewModel.listUsers.observe(this) { showListUsers(it) }
        viewModel.isLoading.observe(this) { showLoading(it) }

        viewModel.error.observe(this){
            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
            viewModel.toastError()
        }

        rvList = binding.rvUser
        rvList.setHasFixedSize(true)

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ _, _, _ ->
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    viewModel.findUsers(searchView.text)
                    false
                }
        }

        saveTheme()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_form, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite ->{
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.setting -> {
                val intent = Intent(this@MainActivity, PreferencesActivity::class.java)
                    startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showListUsers(userData: List<GithubResponseItem>) {
        rvList.layoutManager = LinearLayoutManager(this)
        val adapter = ListUsersAdapter(userData)
        rvList.adapter = adapter

        adapter.setOnItemCallback(object : ListUsersAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GithubResponseItem) {
                val intentListDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
                intentListDetail.putExtra(EXTRA_DATA, data.login)
                startActivity(intentListDetail)
            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun saveTheme(){
        val setPref = SettingPreferences.getInstance(dataStore)

        val themeSettingView =
            ViewModelProvider(this, SettingPreferencesModelFactory(setPref))[SettingPreferencesViewModel::class.java]

        themeSettingView.getThemeSetting().observe(this) {isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    companion object{
        const val EXTRA_DATA = "extra_data"
    }

}