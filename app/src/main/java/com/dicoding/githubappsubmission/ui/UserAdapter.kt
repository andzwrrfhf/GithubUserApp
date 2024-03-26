package com.dicoding.githubappsubmission.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubappsubmission.data.response.DetailUserResponse
import com.dicoding.githubappsubmission.data.response.ItemsItem
import com.dicoding.githubappsubmission.databinding.UserItemBinding

class UserAdapter : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        val profile = DetailUserResponse()
        holder.bind(user, profile)
    }

    class MyViewHolder(var binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem, profile: DetailUserResponse) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(ivProfile)
                binding.tvUsername.text = user.login
            }

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailUserActivity::class.java).apply {
                    putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
                    putExtra(DetailUserActivity.EXTRA_AVATAR, user.avatarUrl)
                    putExtra(DetailUserActivity.EXTRA_FOLLOWERS_COUNT, profile.followers)
                    putExtra(DetailUserActivity.EXTRA_FOLLOWING_COUNT, profile.following)
                    putExtra(DetailUserActivity.EXTRA_REALNAME, profile.name)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}


