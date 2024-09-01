package com.example.projectcontact.ui.admin.admin_users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontact.databinding.ItemUserBinding
import com.example.projectcontact.model.User


class UserAdapter (val listener: OnItemClickListener):  ListAdapter<User, UserAdapter.ViewHolder>(UserCallback()) {

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    class UserCallback:DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return newItem == oldItem
        }
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return newItem == oldItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.user = getItem(position)
        holder.binding.root.setOnClickListener{
            listener.onClick(getItem(position))
        }
    }

    interface OnItemClickListener{
        fun onClick(user: User)
    }
}