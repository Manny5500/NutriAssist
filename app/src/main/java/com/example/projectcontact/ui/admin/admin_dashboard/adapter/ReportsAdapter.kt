package com.example.projectcontact.ui.admin.admin_dashboard.adapter

import android.graphics.Color.parseColor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontact.R
import com.example.projectcontact.databinding.ItemReportsBinding
import com.example.projectcontact.ui.admin.Admin
import com.example.projectcontact.ui.admin.master_list.MasterList
import com.example.projectcontact.ui.admin.prevalance_report.PrevalanceReport
import com.example.projectcontact.ui.admin.summary_report.SummaryReport
import com.example.projectcontact.ui.bns.BNS

class ReportsAdapter : RecyclerView.Adapter<ReportsAdapter.ViewHolder>() {
    lateinit var listener: OnItemClickListener
    val list = listOf(
        Report("Prevalance\nReport", parseColor("#6f80dc"),
            R.drawable.prevalence, PrevalanceReport::class.java),
        Report("Summary\nReport", parseColor("#0e5aab"),
            R.drawable.summary, SummaryReport::class.java),
        Report("MasterList", parseColor("#50ade6"),
            R.drawable.masterlist, MasterList::class.java),
    )
    inner class ViewHolder (val binding: ItemReportsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportsAdapter.ViewHolder {
        val binding = ItemReportsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }
    override fun onBindViewHolder(holder: ReportsAdapter.ViewHolder, position: Int) {
        holder.binding.report = list[position]
        holder.binding.root.setOnClickListener{
            listener.onClick(list[position].destination)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener{
        fun onClick(destination : Class<*>)
    }
}
data class Report (
    var report: String = "",
    var labelColor: Int = 0,
    var imgResource: Int = 0,
    var destination: Class<*> = Admin::class.java
)