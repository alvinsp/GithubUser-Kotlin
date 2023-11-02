package com.example.githubusers.presentation.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.database.Favorite
import com.example.githubusers.data.repository.FavoriteRepository
import com.example.githubusers.data.response.GithubResponseItem
import com.example.githubusers.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val _listUsers = MutableLiveData<GithubResponseItem>()
    val listUsers: LiveData<GithubResponseItem> = _listUsers
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()

    fun addFavorites(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(username: String) = mFavoriteRepository.delete(username)

    fun getDetailUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetail(login)
        client.enqueue(object : Callback<GithubResponseItem> {
            override fun onResponse(
                call: Call<GithubResponseItem>,
                response: Response<GithubResponseItem>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _listUsers.value = response.body()
                }
            }

            override fun onFailure(call: Call<GithubResponseItem>, t: Throwable) {
                _isLoading.value = false
                _error.value = true
            }

        })

    }

    fun toastError() {
        _error.value = false
    }


}
