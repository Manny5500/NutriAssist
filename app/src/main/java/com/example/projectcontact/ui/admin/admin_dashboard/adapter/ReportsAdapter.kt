package com.example.projectcontact.ui.admin.admin_dashboard.adapter

import android.graphics.Color.parseColor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontact.R
import com.example.projectcontact.databinding.ItemReportsBinding

class ReportsAdapter : RecyclerView.Adapter<ReportsAdapter.ViewHolder>() {
    lateinit var listener: OnItemClickListener
    val list = listOf(
        Report("Prevalance\nReport", parseColor("#6f80dc"), R.drawable.prevalence),
        Report("Summary\nReport", parseColor("#0e5aab"), R.drawable.summary),
        Report("MasterList", parseColor("#50ade6"), R.drawable.masterlist),
    )
    inner class ViewHolder (val binding: ItemReportsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportsAdapter.ViewHolder {
        val binding = ItemReportsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }
    override fun onBindViewHolder(holder: ReportsAdapter.ViewHolder, position: Int) {
        holder.binding.report = list[position]
        holder.binding.root.setOnClickListener{
            listener.onClick(list[position].report)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener{
        fun onClick(str: String)
    }
}
data class Report (
    var report: String = "",
    var labelColor: Int = 0,
    var imgResource: Int = 0
)