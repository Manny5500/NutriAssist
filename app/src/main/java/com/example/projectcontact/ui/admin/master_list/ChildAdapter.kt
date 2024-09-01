package com.example.projectcontact.ui.admin.master_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontact.databinding.ItemChildDataBinding
import com.example.projectcontact.model.Child

class ChildAdapter: ListAdapter<Child, ChildAdapter.ViewHolder>(ChildCallback()) {

    inner class ViewHolder(val binding: ItemChildDataBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildAdapter.ViewHolder {
        val binding = ItemChildDataBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ChildAdapter.ViewHolder, position: Int) {
        val child = getItem(position)
        holder.binding.child = child
        holder.binding.btnEdit.visibility = View.INVISIBLE
        with(holder.binding){
            hideViews(btnDelete,btnProgress,btnEdit)
        }
    }

    fun hideViews(vararg view:View){
        for(v in view){
            v.visibility = View.INVISIBLE
        }
    }

    class ChildCallback : DiffUtil.ItemCallback<Child>(){
        override fun areItemsTheSame(oldItem: Child, newItem: Child): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Child, newItem: Child): Boolean {
            return oldItem == newItem
        }
    }

}

