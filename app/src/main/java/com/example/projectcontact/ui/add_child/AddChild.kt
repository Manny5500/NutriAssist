package com.example.projectcontact.ui.add_child

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.projectcontact.databinding.ActivityAddChildBinding
import com.example.projectcontact.model.Parent
import com.example.projectcontact.ui.history.HistoryViewModel
import com.example.projectcontact.util.DialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddChild : AppCompatActivity() {
    private lateinit var binding:ActivityAddChildBinding
    private val viewModel: AddChildViewModel by viewModels()
    private val historyViewModel: HistoryViewModel by viewModels()
    private lateinit var parent : Parent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddChildBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)
        getDataFromIntent()
        event()
        observers()
        binding.lifecycleOwner = this

    }
    fun getDataFromIntent(){
        parent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("parent", Parent::class.java)!!
        } else {
            intent.getParcelableExtra<Parent>("parent")!!
        }
    }

    private fun event(){
        binding.textBdate.setOnClickListener {
            DialogUtil.showDatePickerDialog(binding.textBdate,this)
        }
        binding.textExpectedDate.setOnClickListener {
            DialogUtil.showDatePickerDialog(binding.textExpectedDate, this)
        }

        binding.btnSubmit.setOnClickListener {
            viewModel.addChild()
            historyViewModel.saveHistory(viewModel.child.value!!)
        }
    }

    private fun observers(){
        viewModel.apply {
            setParent(parent)
            showDialog.observe(this@AddChild){
                if(it)
                    DialogUtil.showStatusDialog(this@AddChild,
                        viewModel.child.value!! )
            }

        }
    }

}