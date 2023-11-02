package com.example.githubusers.presentation.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusers.data.database.Favorite
import com.example.githubusers.databinding.ItemReviewBinding
import com.example.githubusers.presentation.detail.DetailUserActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val listFavorite = ArrayList<Favorite>()

    fun setListFavorite(list: List<Favorite>){
        val diffCallback = FavoriteDiffCallback(listFavorite, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    inner class FavoriteViewHolder(private val binding : ItemReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(favorite: Favorite){
            with(binding){
                tvUsername.text = favorite?.login
                tvCompany.text = favorite?.type
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context, FavoriteDetailActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_FAVORITE, favorite.login)
                    itemView.context.startActivity(intent)
                }
            }
            Glide.with(itemView.context)
                .load(favorite.avatarUrl)
                .centerCrop()
                .circleCrop()
                .into(binding.ivPhoto)
        }
    }


}
