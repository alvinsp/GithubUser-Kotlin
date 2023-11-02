package com.example.githubusers.data.retrofit

import android.text.Editable
import com.example.githubusers.BuildConfig
import com.example.githubusers.data.response.GithubResponseItem
import com.example.githubusers.data.response.SearchUsersResponse
import retrofit2.Call
import retrofit2.http.*


const val mySuperSecretKey = BuildConfig.KEY

interface ApiService {
    @GET("users")
    @Headers("Authorization: KEY ${mySuperSecretKey}")
    fun getListUsers(): Call<List<GithubResponseItem>>

    @GET("search/users")
    @Headers("Authorization: KEY ${mySuperSecretKey}")
    fun getSearchUser(
        @Query("q") q: Editable?
    ): Call<SearchUsersResponse>

    @GET("users/{login}")
    @Headers("Authorization: KEY ${mySuperSecretKey}")
    fun getDetail(
        @Path("login") login : String
    ) : Call<GithubResponseItem>

    @GET("users/{username}/followers")
    @Headers("Authorization: KEY ${mySuperSecretKey}")
    fun getFollowers(
        @Path("username") username :String
    ) : Call<List<GithubResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: KEY ${mySuperSecretKey}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<GithubResponseItem>>
}