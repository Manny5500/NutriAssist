package com.example.projectcontact.ui.admin.master_list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectcontact.R
import com.example.projectcontact.databinding.ActivityMasterListBinding
import com.example.projectcontact.model.Child
import com.example.projectcontact.util.DateUtil.monthNow
import com.example.projectcontact.util.DialogUtil.showPdfDialog
import com.example.projectcontact.util.pdf.malnourished_list.MLPdf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MasterList : AppCompatActivity(){
    private val adapter by lazy{ChildAdapter()}
    private val viewModel: MasterListViewModel by viewModels()
    private lateinit var binding: ActivityMasterListBinding
    private lateinit var childList: List<Child>
    var monthAdded = monthNow()
    var barangay = "Alipit"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMasterListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preSelected()
        events()
        observers()
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        binding.recycler.apply{
            layoutManager = LinearLayoutManager(this@MasterList)
            adapter = this@MasterList.adapter
        }
    }


    @SuppressLint("SetTextI18n")
    private fun preSelected(){
        binding.textDate.setText(monthAdded)
        binding.textBarangay.setText(barangay)
    }
    private fun events(){
        binding.textDate.setOnItemClickListener { _, _, _, _ ->
            monthAdded = binding.textDate.text.toString()
            viewModel.setMonthAdded(monthAdded)
        }

        binding.textBarangay.setOnItemClickListener {_, _, _, _ ->
            barangay = binding.textBarangay.text.toString()
            viewModel.setBarangay(barangay)
        }

        binding.pdfMaker.setOnClickListener{
            val pdfBytes = MLPdf(
                ContextCompat.getDrawable(this, R.drawable.optlogojp)!!,
                barangay,
                childList
            ).pdfSetter()
            showPdfDialog(pdfBytes, this, "MalnourishedList")
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let{viewModel.setSearchQuery(newText)}
                return true
            }

        })
    }

    private fun observers(){
        viewModel.childrenList.observe(this){
            childList = it
        }
        viewModel.filteredList.observe(this){
            adapter.submitList(it)
        }
    }

}