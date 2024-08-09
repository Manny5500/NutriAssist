package com.example.projectcontact.ui.bns

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.projectcontact.App
import com.example.projectcontact.R
import com.example.projectcontact.databinding.ActivityBnsBinding
import com.example.projectcontact.util.ToastUtil.show
import com.example.projectcontact.util.UserPreferences.saveUserId
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BNS : AppCompatActivity() {

    private lateinit var binding: ActivityBnsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBnsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_bns)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_bns_dashboard, R.id.navigation_bns_data, R.id.navigation_bns_account
            )
        )
        navView.setupWithNavController(navController)
    }

}