package com.xr6software.eldarwallet.view

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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

    override fun onBackPressed() {
        showQuitDialog()
    }

    fun showQuitDialog(){
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(R.string.quit_dialog_msg)
            .setCancelable(false)
            .setPositiveButton(R.string.quit_dialog_positive, DialogInterface.OnClickListener {
                    dialog, id -> finish()
            })
            .setNegativeButton(R.string.quit_dialog_negative, DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle(R.string.quit_dialog_title)
        alert.show()
    }

}