package com.example.githubusers.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favorite: Favorite)

    @Query("SELECT * from favorite ORDER BY login ASC")
    fun getFavorites() : LiveData<List<Favorite>>

    @Query("DELETE FROM favorite WHERE login = :username")
    fun removeFavorite(username: String)
}