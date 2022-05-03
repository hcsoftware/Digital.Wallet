package com.xr6software.eldarwallet.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xr6software.eldarwallet.R
import com.xr6software.eldarwallet.model.UserSingletonModel

class WalletActivity : AppCompatActivity() {

    private lateinit var bottomToolbar: BottomNavigationView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        bottomToolbar = findViewById(R.id.wa_bottom_menu)
        customizeToolbar()
        configNav()

    }

    private fun customizeToolbar() {
        supportActionBar?.setIcon(R.drawable.toolbar_icon)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Logged as: " + UserSingletonModel.getUser().username
        supportActionBar?.hide()
    }

    private fun configNav() : Unit {
        val navController = Navigation.findNavController(this, R.id.wa_fragment_container)
        NavigationUI.setupWithNavController(bottomToolbar, navController)
    }


}