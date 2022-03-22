package com.xr6software.eldarwallet.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.xr6software.eldarwallet.R
import com.xr6software.eldarwallet.databinding.ActivityLoginBinding
import com.xr6software.eldarwallet.model.UserSingletonModel
import com.xr6software.eldarwallet.viewmodel.LoginActivityViewModel

/*
This activity handles login service
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityLoginBinding
    private lateinit var viewModel : LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view : View = viewBinding.root
        setContentView(view)

        viewBinding.maButtonLogin.setOnClickListener {
            if ((viewBinding.maEdittxtUser.text.toString().length > 0 ) && (viewBinding.maEdittxtUser.text.toString().length <= 8 )) {
                viewModel.checkIfUserExists(applicationContext , viewBinding.maEdittxtUser.text.toString())
            }
            else {
                Snackbar.make(viewBinding.constraintLayoutMa, R.string.ma_snackbar_message, Snackbar.LENGTH_SHORT).show()
            }
        }
        setObservables()

    }

    fun setObservables() {

        viewModel.getuserInDatabase().observe(this, Observer {
            if (it != null) {
                //Call Activity and init singleton
                UserSingletonModel(it)
                var intent : Intent = Intent(this, WalletActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                showCreateUserDialog()
            }
        })

    }

    fun showCreateUserDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.ma_dialog_new_user_title)
        builder.setMessage(R.string.ma_dialog_new_user_message)
        builder.setCancelable(false)
        builder.setPositiveButton(android.R.string.yes) {dialogInterface, which ->
            viewModel.insertUserInDatabase(applicationContext, viewBinding.maEdittxtUser.text.toString(), "")
        }
        builder.setNegativeButton(android.R.string.no) { dialogInterface, which ->
            //close dialog
        }
        builder.show()

    }
}