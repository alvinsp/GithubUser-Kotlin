package com.example.githubusers.presentation.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.database.Favorite
import com.example.githubusers.data.repository.FavoriteRepository

class FavoriteViewModel (application: Application) : ViewModel(){
    private val mFavoriteRepository : FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorite() : LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()
}