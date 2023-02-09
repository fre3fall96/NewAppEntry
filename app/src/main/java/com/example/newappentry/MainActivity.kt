package com.example.newappentry

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main)  {
    //private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        //this code is to set up the back button in the actionbar(bar at the top of the screen)
        // Retrieve NavController from the NavHostFragment
        // val navHostFragment = supportFragmentManager
        //    .findFragmentById(R.id.overviewFragment) as NavHostFragment
        //navController = navHostFragment.navController
        // Set up the action bar for use with the NavController
        //setupActionBarWithNavController(navController)
        //navController.enableOnBackPressed(true)
    }

}