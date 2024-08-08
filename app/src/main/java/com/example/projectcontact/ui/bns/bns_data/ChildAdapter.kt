package com.example.projectcontact.ui.bns.bns_data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontact.databinding.ItemChildDataBinding
import com.example.projectcontact.model.Child

class ChildAdapter(private val listener: OnButtonClickListener) : ListAdapter<Child, ChildAdapter.ViewHolder>(ChildCallback()) {

    private var originalList: List<Child> = emptyList()

    private var count = 0

    override fun submitList(list: List<Child>?) {
        super.submitList(list)
        if(count==0){
            originalList = list ?: emptyList()
            count++
        }
    }

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
        itemEvents(holder.binding, child)
    }

    private fun itemEvents(binding: ItemChildDataBinding, child: Child){
        with(binding){
            btnEdit.setOnClickListener {
                listener.onEditButtonClick(child)
            }
            btnDelete.setOnClickListener {
                listener.onDeleteButtonClick(child)
            }

            btnProgress.setOnClickListener {
                listener.onProgressButtonClick(child.fullestName, child.birthDate)
            }
        }
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalList
        }
        else {
            originalList.filter { child ->
                child.childFirstName.contains(query, ignoreCase = true)
            }
        }
        submitList(filteredList)
    }
    interface OnButtonClickListener {
        fun onDeleteButtonClick(child: Child)
        fun onEditButtonClick(child: Child)
        fun onProgressButtonClick(fullName: String, birthDate : String)
    }

    class ChildCallback :DiffUtil.ItemCallback<Child>(){
        override fun areItemsTheSame(oldItem: Child, newItem: Child): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Child, newItem: Child): Boolean {
            return oldItem == newItem
        }
    }

}

