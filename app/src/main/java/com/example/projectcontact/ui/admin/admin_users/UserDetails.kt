package com.example.projectcontact.ui.admin.admin_users

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.projectcontact.MarvaStructure
import com.example.projectcontact.databinding.ActivityUserDetailsBinding
import com.example.projectcontact.model.User
import com.example.projectcontact.util.DialogUtil.showDeleteAccountDialog
import dagger.hilt.android.AndroidEntryPoint


@Suppress("DEPRECATION")
@AndroidEntryPoint
class UserDetails : AppCompatActivity() , MarvaStructure {
    private lateinit var binding : ActivityUserDetailsBinding
    private lateinit var user: User
    private lateinit var action: String
    private val viewModel : UserDetailsViewModel by viewModels()
    private lateinit var userId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFromIntent()
        hideAllButton()
        showButton()
        observers()
        events()
    }

    private fun getDataFromIntent(){

        user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("user", User::class.java)!!
        } else {
            intent.getParcelableExtra("user")!!
        }

        action = intent.getStringExtra("action") ?: "archive"
        userId = user.id ?: ""
        binding.user = user
    }

    override fun observers() {

    }

    private fun hideAllButton(){
        with(binding){
            buttonArchive.visibility = View.GONE
            buttonDelete.visibility = View.GONE
            buttonUnarchive.visibility = View.GONE
            buttonVerify.visibility = View.GONE
        }
    }

    private fun showButton(){
        when(action){
            "archive" -> binding.buttonArchive.visibility = View.VISIBLE
            "unarchive" -> binding.buttonUnarchive.visibility = View.VISIBLE
            "verify" -> binding.buttonVerify.visibility = View.VISIBLE
            "delete" -> binding.buttonDelete.visibility = View.VISIBLE
        }
    }

    override fun events() {
        with(binding){
            buttonArchive.setOnClickListener {
                viewModel.archiveUser(userId)
            }
            buttonVerify.setOnClickListener {
                viewModel.verifyUser(userId)
            }
            buttonUnarchive.setOnClickListener {
               viewModel.unarchiveUser(userId)
            }
            buttonDelete.setOnClickListener {
                showDeleteAccountDialog(this@UserDetails, viewModel, userId)
            }
        }
    }
}