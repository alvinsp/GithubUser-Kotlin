package com.example.githubusers.presentation.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.data.factory.ViewModelFactory
import com.example.githubusers.data.database.Favorite
import com.example.githubusers.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var favoriteViewModel: FavoriteViewModel

    private var _activityFavoriteBinding : ActivityFavoriteBinding ?= null
    private val binding get() = _activityFavoriteBinding!!

    private lateinit var adapter : FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)

        adapter = FavoriteAdapter()
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(false)
        binding.rvFavorite.adapter = adapter

        favoriteViewModel.getAllFavorite().observe(this){ favorite ->
            if (favorite != null){
                adapter.setListFavorite(favorite)
            }
            listIsEmpty(favorite)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }

    private fun listIsEmpty(list: List<Favorite>) {
        if (list.isEmpty()) {
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.tvEmpty.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }
}
