package com.wa82bj.findgitjobs.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.wa82bj.findgitjobs.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/*
** Created by Wael Baaj 17/10/2019
*/

class MainActivity : DaggerAppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        setBottomNavMenu(navController)
        setUpActionBar(navController)

    }

    private fun setBottomNavMenu (navController: NavController){

        bottom_nav?.let {
            NavigationUI.setupWithNavController(it,navController)
        }
    }

    private fun setUpActionBar (navController: NavController){

        NavigationUI.setupActionBarWithNavController(this,navController,null)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        val navigated = NavigationUI.onNavDestinationSelected(item!!,navController)
        return navigated || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.nav_host_fragment),null)
    }


}
