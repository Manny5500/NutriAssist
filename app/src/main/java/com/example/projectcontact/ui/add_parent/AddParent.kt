package com.example.projectcontact.ui.add_parent

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.projectcontact.databinding.ActivityAddParentBinding
import com.example.projectcontact.model.Parent
import com.example.projectcontact.ui.add_child.AddChild
import com.example.projectcontact.util.DialogUtil.showSearchParentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddParent : AppCompatActivity(), SearchParentAdapter.OnItemClickListener {
    private lateinit var binding: ActivityAddParentBinding
    private val viewModel: AddParentViewModel by viewModels()
    private val adapter by lazy { SearchParentAdapter(this) }
    var parents:List<Parent> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddParentBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)

        binding.lifecycleOwner = this
        observer()
        event()
    }

    fun event(){

        binding.btnNext.setOnClickListener{
            viewModel.saveParent()
        }
        binding.pDExist.setOnClickListener {
            showSearchParentDialog(this,adapter,parents)
        }
    }

    private fun observer(){
        viewModel.proceed.observe(this){
            if(it){
                val intent = Intent(this, AddChild::class.java)
                intent.putExtra("parent", viewModel.parent.value)
                startActivity(intent)
            }
        }

        viewModel.getParents()
        viewModel.parentList.observe(this){
            parents = it
        }

    }

    override fun onClick(parent: Parent) {
        val intent = Intent(this, AddChild::class.java)
        intent.putExtra("parent", parent )
        startActivity(intent)
    }

}