package com.example.githubusers.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusers.R
import com.example.githubusers.data.database.Favorite
import com.example.githubusers.data.response.GithubResponseItem
import com.example.githubusers.databinding.ActivityDetailUserBinding
import com.example.githubusers.data.factory.ViewModelFactory
import com.example.githubusers.presentation.main.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("NAME_SHADOWING")
class DetailUserActivity : AppCompatActivity() {

    private var _activityDetailUserBinding: ActivityDetailUserBinding? = null
    private val binding get() = _activityDetailUserBinding!!
    private lateinit var viewModel: DetailViewModel
    var username : String ?= null

    private var isFavorite: Boolean = false
    private var favorite: Favorite ?= null
    private var items = GithubResponseItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.title = "Detail's User"

        viewModel = obtainViewModel(this@DetailUserActivity)

        username = intent.getStringExtra(MainActivity.EXTRA_DATA)

        if(username != null ){
            username.let { viewModel.getDetailUser(username!!) }
        }

        viewModel.listUsers.observe(this) { listDetail ->

            items = listDetail

            if (listDetail != null) {
                binding.let {
                    Glide.with(applicationContext)
                        .load(listDetail.avatarUrl)
                        .circleCrop()
                        .into(it.imgDetailPhoto)
                }
            }

            binding.apply {
                tvUsername.text = listDetail.login
                tvName.text = listDetail.name
                tvFollowers.text = resources.getString(R.string.valueFollowers, listDetail.followers)
                tvFollowing.text = resources.getString(R.string.valueFollowing, listDetail.following)
            }

            favorite = Favorite(
                listDetail.login.toString(),
                listDetail.type,
                listDetail.avatarUrl.toString()
            )

            getFavoritesDB()
            setupTabLayout()
        }

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            viewModel.toastError()
        }
    }

    private fun addFavorite(listFavorite: GithubResponseItem) {
        favorite.let { toFavorite ->
            toFavorite?.login = listFavorite.login.toString()
            toFavorite?.type = listFavorite.type
            toFavorite?.avatarUrl = listFavorite.avatarUrl.toString()
            viewModel.addFavorites(toFavorite as Favorite)
            Toast.makeText(this, "Success adding user to favorite", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailUserBinding = null
    }

    private fun setupTabLayout() {
        val sectionPagerAdapter = username?.let { SectionPagerAdapter(this, it) }
        val viewPage: ViewPager2 = findViewById(R.id.view_pager)
        viewPage.adapter = sectionPagerAdapter

        val tab: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tab, viewPage) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun getFavoritesDB(){
        viewModel.getAllFavorites().observe(this) { userFavorite ->
            if (userFavorite != null) {
                for (data in userFavorite) {
                    if (items.login == data.login) {
                        isFavorite = true
                        binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
                    }
                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            if (!isFavorite) {
                isFavorite = true
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
                addFavorite(items)
                Toast.makeText(this, "Favorite has been added", Toast.LENGTH_SHORT).show()
            } else {
                isFavorite = false
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
                viewModel.delete(items.login.toString())
                Toast.makeText(this, "Favorite has been remove", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    companion object {
        const val EXTRA_FAVORITE = "extra_favorite"
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }

}