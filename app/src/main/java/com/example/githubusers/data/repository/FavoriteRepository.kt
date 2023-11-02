package com.example.githubusers.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubusers.data.database.Favorite
import com.example.githubusers.data.database.FavoriteDao
import com.example.githubusers.data.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application){
    private val mFavoriteDao : FavoriteDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite() : LiveData<List<Favorite>> = mFavoriteDao.getFavorites()

    fun insert(favorite: Favorite){
        executorService.execute{ mFavoriteDao.addFavorite(favorite) }
    }

    fun delete(username: String){
        executorService.execute{ mFavoriteDao.removeFavorite(username) }
    }

}
