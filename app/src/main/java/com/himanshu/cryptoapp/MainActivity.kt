package com.himanshu.cryptoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.appcompat.app.ActionBar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.himanshu.cryptoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar : ActionBar? = supportActionBar;
        actionBar?.setDisplayHomeAsUpEnabled(true);

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigationContainerView)
        navController = navHostFragment!!.findNavController()


        val popMenu = PopupMenu(this,null)
        popMenu.inflate(R.menu.nav_menu)
        binding.bottomBar.setupWithNavController(popMenu.menu,navController)


        // to hide back button on home fragment
        val appBarConfiguration = AppBarConfiguration( setOf(R.id.homeFragment))

        setupActionBarWithNavController(navController,appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navigationContainerView)

        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}