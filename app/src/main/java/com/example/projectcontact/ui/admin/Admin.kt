package com.example.projectcontact.ui.admin

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.projectcontact.R
import com.example.projectcontact.databinding.ActivityAdminBinding
import com.example.projectcontact.util.UserPreferences
import com.google.firebase.auth.FirebaseAuth

class Admin : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signIn()
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_admin)
        navView.setupWithNavController(navController)
    }


    fun signIn(){
        val  auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword("mannygendz@gmail.com", "Marva5500$")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    UserPreferences.saveUserId(this, user?.uid!!)
                } else {
                    task.exception?.let {
                    }
                }
            }
    }
}