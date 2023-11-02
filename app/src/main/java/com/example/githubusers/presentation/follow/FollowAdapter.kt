package com.example.githubusers.presentation.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusers.data.response.GithubResponseItem
import com.example.githubusers.databinding.ItemReviewBinding

class FollowAdapter(private val listUser: List<GithubResponseItem>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gitUser = listUser[position]
        Glide.with(holder.itemView.context)
            .load(gitUser.avatarUrl)
            .centerCrop()
            .circleCrop()
            .into(holder.binding.ivPhoto)
        holder.apply {
            binding.apply {
                tvUsername.text = gitUser.login
                tvCompany.text = gitUser.type
            }
        }
    }

    class ViewHolder(val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root)
}