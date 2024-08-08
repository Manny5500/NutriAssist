package com.example.projectcontact.ui.bns.bns_dashboard

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectcontact.databinding.ActivityFamilyListBinding
import com.example.projectcontact.model.Parent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FamilyList : AppCompatActivity() {

    private lateinit var binding: ActivityFamilyListBinding
    private lateinit var parentList: List<Parent>
    private val adapter by lazy { FamilyAdapter() }
    private val viewModel:FamilyListViewModel by viewModels()
    private var isFilterHidden = true

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFamilyListBinding.inflate(LayoutInflater.from(this))
        binding.viewModel = viewModel
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        getDataFromIntent()
        setupRecyclerView()
        if(!isFilterHidden) binding.userPickerLayout.visibility = View.VISIBLE
        event()
        observer()
        viewModel.filter()

    }


    private fun getDataFromIntent(){
        parentList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra("parents", Parent::class.java) ?: arrayListOf()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra("parents") ?: arrayListOf()
        }

        isFilterHidden = intent.getBooleanExtra("isFilterHidden", true)

    }

    private fun setupRecyclerView(){
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)
        adapter.submitList(parentList)
    }

    private fun event(){
        binding.userPicker.setOnItemClickListener { _, _, _, _ ->
            viewModel.filter()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let{viewModel.setSearchQuery(newText)}
                return true
            }
        })
    }

    private fun observer(){
        viewModel.setParentList(parentList)
        viewModel.filteredList.observe(this){
            adapter.submitList(it)
        }
        viewModel.isEmptyList.observe(this){
            if(it){
                binding.emptyState.visibility = View.VISIBLE
            }else{
                binding.emptyState.visibility = View.INVISIBLE
            }
        }
    }

}