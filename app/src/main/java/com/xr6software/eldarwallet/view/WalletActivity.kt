package com.xr6software.eldarwallet.view

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
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

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_custom)
        dialog.findViewById<TextView>(R.id.dialog_custom_title).text = getString(R.string.quit_dialog_title).toString().uppercase()
        dialog.findViewById<TextView>(R.id.dialog_custom_msg).text = getString(R.string.quit_dialog_msg).toString().uppercase()
        val okButton : Button = dialog.findViewById(R.id.dialog_custom_ok_button)
        val cancelButton : Button = dialog.findViewById(R.id.dialog_custom_cancel_button)
        okButton.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

}