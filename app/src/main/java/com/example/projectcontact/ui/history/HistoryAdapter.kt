package com.example.projectcontact.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontact.databinding.ItemProgressBnsBinding
import com.example.projectcontact.model.History

class HistoryAdapter(private val listener: HistoryAdapter.OnButtonClickListener)
    : ListAdapter<History, HistoryAdapter.ViewHolder>(HistoryCallback()) {

    inner class ViewHolder(val binding: ItemProgressBnsBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.ViewHolder {
        val binding = ItemProgressBnsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {

        val history = getItem(position)
        holder.binding.history = history
        holder.binding.progressMenu.setOnClickListener {
            listener.updateHistory(getItem(position))
        }

    }

    interface OnButtonClickListener{
        fun updateHistory(history: History)
    }

    class HistoryCallback:DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }
    }

}