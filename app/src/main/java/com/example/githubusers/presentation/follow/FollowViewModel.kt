package com.example.githubusers.presentation.follow


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.response.GithubResponseItem
import com.example.githubusers.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    private val _listFollow = MutableLiveData<List<GithubResponseItem>>()

    val loading : LiveData<Boolean> = _isLoading
    val listFollow : LiveData<List<GithubResponseItem>> = _listFollow

    fun getUserFollower(username: String) {

        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<GithubResponseItem>>{
            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>,
            ) {
                _isLoading.value = false
                _listFollow.value = response.body()

            }

            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                _isLoading.value = false

            }

        })
    }

    fun getUserFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<GithubResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>,
            ) {
                _isLoading.value = false
                _listFollow.value = response.body()
            }
            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}