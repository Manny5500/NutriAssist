package com.example.projectcontact.ui.bns.bns_dashboard

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectcontact.databinding.ActivityFeedingListBinding
import com.example.projectcontact.model.Child
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeedingList:AppCompatActivity() {
    private lateinit var binding:ActivityFeedingListBinding
    private val adapter by lazy { FeedingAdapter()}
    private lateinit var childList: List<Child>


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFeedingListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        getDataFromIntent()
        adapter.submitList(childList)
        if(childList.isEmpty()){
            binding.emptyState.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView(){
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)
    }

    private fun getDataFromIntent(){
        childList = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableArrayListExtra("childList", Child::class.java) ?: arrayListOf()
        }else{
            intent.getParcelableArrayListExtra("childList")?: arrayListOf()
        }
    }



}