package com.example.githubusers.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Favorite")
data class Favorite(
    @ColumnInfo(name = "login")
    @PrimaryKey
    var login : String,
    @ColumnInfo(name = "type")
    var type : String?,
    @ColumnInfo(name = "avatar_url")
    var avatarUrl : String,

) : Parcelable
