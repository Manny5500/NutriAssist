package com.example.projectcontact.ui.bns.bns_dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontact.databinding.ItemFeedingListBinding
import com.example.projectcontact.model.Child

class FeedingAdapter() : ListAdapter<Child, FeedingAdapter.ViewHolder>(FeedingCallback()) {


    inner class ViewHolder(val binding: ItemFeedingListBinding): RecyclerView.ViewHolder(binding.root)

    class FeedingCallback : DiffUtil.ItemCallback<Child>() {
        override fun areItemsTheSame(oldItem: Child, newItem: Child): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Child, newItem: Child): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedingAdapter.ViewHolder {
        val binding = ItemFeedingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: FeedingAdapter.ViewHolder, position: Int) {
        holder.binding.child = getItem(position)
    }
}