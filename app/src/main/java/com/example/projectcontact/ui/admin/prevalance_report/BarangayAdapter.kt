package com.example.projectcontact.ui.admin.prevalance_report

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontact.databinding.ItemBarangayBinding
import com.example.projectcontact.model.Barangay

class BarangayAdapter: ListAdapter<Barangay, BarangayAdapter.ViewHolder>(ChildCallback()) {

    inner class ViewHolder(val binding: ItemBarangayBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangayAdapter.ViewHolder {
        val binding = ItemBarangayBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(holder: BarangayAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            barangayTitle.text = String.format("%d ${item.barangay}", item.position)
            optCoverage.text = String.format("OPT Coverage: %.2f%%", item.optCoverage)
            malCases.text = String.format("${item.caseLabel} :  %d", item.totalCase)
            malPercent.text = String.format("(%%) : %.2f%%", item.totalRate)
            NormalCases.text = String.format("Normal : %d", item.normalCase)
            NormalPercent.text = String.format("(%%) : %.2f%%", item.normalRate)
        }

    }

    class ChildCallback : DiffUtil.ItemCallback<Barangay>(){
        override fun areItemsTheSame(oldItem: Barangay, newItem: Barangay): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Barangay, newItem: Barangay): Boolean {
            return oldItem == newItem
        }
    }

}