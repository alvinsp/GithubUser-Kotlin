package com.example.githubusers.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusers.data.response.GithubResponseItem
import com.example.githubusers.databinding.ItemReviewBinding

class ListUsersAdapter(private val listReview: List<GithubResponseItem>) :
    RecyclerView.Adapter<ListUsersAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : ViewHolder {
            val binding = ItemReviewBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ViewHolder(binding)

        }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val gitUser = listReview[position]
        viewHolder.apply {
            binding.apply {
                tvUsername.text = gitUser.login
                tvCompany.text = gitUser.type
            }
        }

        Glide.with(viewHolder.itemView.context)
            .load(gitUser.avatarUrl)
            .circleCrop()
            .into(viewHolder.binding.ivPhoto)
        viewHolder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listReview[viewHolder.adapterPosition]) }
    }

    override fun getItemCount() = listReview.size

    class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)
    interface OnItemClickCallback {
        fun onItemClicked(data: GithubResponseItem)
    }

    fun setOnItemCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

}