package com.example.projectcontact.ui.history

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.projectcontact.databinding.ActivityEditHistoryBinding
import com.example.projectcontact.model.History
import com.example.projectcontact.util.DialogUtil.showStatusDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditHistory : AppCompatActivity() {
    private lateinit var binding : ActivityEditHistoryBinding
    private val viewModel : HistoryViewModel by viewModels()

    private lateinit var history:History
    private lateinit var fullName : String
    private lateinit var birthDate : String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditHistoryBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        binding.lifecycleOwner = this

        getDataFromIntent()
        setDataOfViewModel()
    }

    fun getDataFromIntent(){

        history = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("history", History::class.java)!!
        } else {
            intent.getParcelableExtra<History>("history")!!
        }

        birthDate = intent.getStringExtra("birthDate").toString()
        fullName = intent.getStringExtra("fullName").toString()

    }

    fun setDataOfViewModel(){
        viewModel.fullName = fullName
        viewModel.setHistory(history)
        viewModel.setAge(birthDate, history.id)
    }

}