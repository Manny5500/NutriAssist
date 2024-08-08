package com.example.projectcontact.ui.update_child

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.example.projectcontact.MarvaStructure
import com.example.projectcontact.databinding.ActivityEditChildBinding
import com.example.projectcontact.model.Child
import com.example.projectcontact.ui.history.HistoryViewModel
import com.example.projectcontact.util.DialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditChild : AppCompatActivity(), MarvaStructure {
    private val viewModel: EditChildViewModel by viewModels()
    private val historyViewModel: HistoryViewModel by viewModels()

    private lateinit var binding: ActivityEditChildBinding

    private lateinit var child: Child

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditChildBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        getDataFromIntent()
        setViewModelData()
        observers()
        events()
        binding.lifecycleOwner = this
    }

    fun getDataFromIntent(){
        child = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("child", Child::class.java)!!
        } else {
            intent.getParcelableExtra<Child>("child")!!
        }

    }

    fun setViewModelData(){
        viewModel.setChild(child)
    }
    override fun observers(){
        viewModel.age.observe(this){
        }

        viewModel.showDialog.observe(this){
            if(it){
                DialogUtil.showStatusDialog(this, viewModel.child.value!!)
            }
        }

    }

    override fun events(){
        binding.btnEdit.setOnClickListener{
            viewModel.updateChild(child)
            historyViewModel.saveHistory(viewModel.child.value!!)
        }

        binding.textExpectedDate.setOnClickListener{
            DialogUtil.showDatePickerDialog(binding.textExpectedDate, this)
        }

        binding.textBdate.setOnClickListener {
            DialogUtil.showDatePickerDialog(binding.textBdate, this)
        }
    }

}