package com.example.projectcontact.ui.history

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectcontact.databinding.ActivityHistoryBinding
import com.example.projectcontact.model.History
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryActivity : AppCompatActivity(), HistoryAdapter.OnButtonClickListener {

    private lateinit var binding: ActivityHistoryBinding
    private val viewModel:HistoryViewModel by viewModels()
    private val adapter by lazy { HistoryAdapter(this) }

    private lateinit var fullName : String
    private lateinit var birthDate : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)




        fullName = intent.getStringExtra("fullName").toString()
        birthDate = intent.getStringExtra("birthDate").toString()

        viewModel.fetchData(fullName)

        val recyclerView = binding.recycler
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.historyList.observe(this){
            adapter.submitList(it)
        }


    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData(fullName)
    }
    override fun updateHistory(history: History) {
        val intent = Intent(this, EditHistory::class.java)
        intent.putExtra("history", history)
        intent.putExtra("fullName", fullName)
        intent.putExtra("birthDate", birthDate)
        startActivity(intent)
    }
}