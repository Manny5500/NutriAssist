package com.example.projectcontact.ui.add_parent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontact.databinding.ItemSearchParentBinding
import com.example.projectcontact.model.Parent

class SearchParentAdapter(private val listener : OnItemClickListener) : ListAdapter<Parent, SearchParentAdapter.ViewHolder> (
    SearchParentCallback()
) {
    // for searching purpose
    private var originalList: List<Parent> = emptyList()

    var count = 0
    override fun submitList(list: List<Parent>?) {
        super.submitList(list)
        if(count==0){
            originalList = list ?: emptyList()
            count++
        }

    }


    fun filter(query:String){
        val filteredList = if(query.isEmpty()){
            originalList
        }else {
            originalList.filter { parent ->
                parent.parentFirstName.contains(query, true)
            }
        }

        submitList(filteredList)
    }

    interface OnItemClickListener {
        fun onClick(parent: Parent)
    }

    inner class ViewHolder (val binding: ItemSearchParentBinding):
            RecyclerView.ViewHolder(binding.root)

    class SearchParentCallback : DiffUtil.ItemCallback<Parent>(){
        override fun areItemsTheSame(oldItem: Parent, newItem: Parent): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Parent, newItem: Parent): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
       val binding = ItemSearchParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parent = getItem(position)
        holder.binding.parent = parent
        holder.binding.root.setOnClickListener {
            listener.onClick(parent)
        }
    }

}