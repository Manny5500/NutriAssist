package com.example.projectcontact.ui.admin.admin_dashboard.adapter
import android.graphics.Color.parseColor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontact.R
import com.example.projectcontact.databinding.ItemProgramBinding

class ProgramAdapter: RecyclerView.Adapter<ProgramAdapter.ViewHolder>() {

    val list = listOf(
        Program("Feeding    Program", parseColor("#c8a67e"), R.drawable.feedingpaste),
        Program("Gulayan sa Bakuran", parseColor("#4aa166"), R.drawable.gulayan),
        Program("Deworming  Program", parseColor("#e99796"), R.drawable.deworm)
    )

    lateinit var listener:OnItemClickListener
    inner class ViewHolder (val binding: ItemProgramBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramAdapter.ViewHolder {
        val binding = ItemProgramBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramAdapter.ViewHolder, position: Int) {
        holder.binding.program = list[position]
        holder.binding.root.setOnClickListener{
            listener.onClick(list[position].program)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener{
        fun onClick(str: String)
    }

}

data class Program (
    var program: String = "",
    var labelColor: Int = 0,
    var imgResource: Int = 0
)