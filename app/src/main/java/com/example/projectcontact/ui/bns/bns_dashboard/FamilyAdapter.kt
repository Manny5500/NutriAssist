package com.example.projectcontact.ui.bns.bns_dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontact.databinding.ItemFamilyListBinding
import com.example.projectcontact.model.Parent


class FamilyAdapter: ListAdapter<Parent, FamilyAdapter.ViewHolder>(ParentCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyAdapter.ViewHolder {
        val binding = ItemFamilyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding:ItemFamilyListBinding):
        RecyclerView.ViewHolder(binding.root)

    class ParentCallback: DiffUtil.ItemCallback<Parent>(){
        override fun areItemsTheSame(oldItem: Parent, newItem: Parent): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Parent, newItem: Parent): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: FamilyAdapter.ViewHolder, position: Int) {
        holder.binding.parent = getItem(position)
    }
}