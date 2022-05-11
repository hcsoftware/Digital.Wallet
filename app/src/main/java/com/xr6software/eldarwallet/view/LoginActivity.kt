package com.xr6software.eldarwallet.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    private val MIN_USERPASS_CHARS : Int = 4
    private val MAX_USERPASS_CHARS : Int = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view : View = viewBinding.root
        setContentView(view)

        customizeActionBar()
        setOnClickListeners()
        setObservables()

    }

    private fun customizeActionBar() {
        supportActionBar?.hide()
    }

    private fun setOnClickListeners() {

        viewBinding.maButtonLogin.setOnClickListener {

            if (validateUserInputEditText(viewBinding.maEdittxtUser) && validateUserInputEditText(viewBinding.maEdittxtPass)) {
                //Checks for username in db. Modify the getUsernameExists observable.
                viewModel.checkIfUsernameExists(applicationContext, viewBinding.maEdittxtUser.text.toString())
            }
            else {
                MessageFactory.showSnackBar(viewBinding.constraintLayoutLoginact, R.string.ma_snackbar_inputerror, false)
            }
        }

    }

    fun setObservables() {
        //This observable checks for valid username
        viewModel.getUserNameExists().observe(this, Observer {

            if (!it) { showCreateUserDialog() } //User doesnt exists.
            else { viewModel.getUserFromDb(applicationContext, viewBinding.maEdittxtUser.text.toString(),viewBinding.maEdittxtPass.text.toString()) }

        })
        //This observable checks for valid credentials (user/pass)
        viewModel.getValidCredentials().observe(this, Observer {
            if (!it) {
                MessageFactory.showSnackBar(viewBinding.constraintLayoutLoginact, R.string.ma_snackbar_badcredentials, false)
            }

        })
        //This observable checks user callback from database
        viewModel.getuserInDatabase().observe(this, Observer {
            if (it != null) {
                //Call Wallet Activity and init singleton
                UserSingletonModel.setUser(it)
                var intent : Intent = Intent(this, WalletActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    fun showCreateUserDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_custom)
        dialog.findViewById<TextView>(R.id.dialog_custom_title).text = getString(R.string.ma_dialog_new_user_title).toString().uppercase()
        dialog.findViewById<TextView>(R.id.dialog_custom_msg).text = getString(R.string.ma_dialog_new_user_message)
        val okButton : Button = dialog.findViewById(R.id.dialog_custom_ok_button)
        val cancelButton : Button = dialog.findViewById(R.id.dialog_custom_cancel_button)
        okButton.setOnClickListener {
            viewModel.insertUserInDatabase(applicationContext, viewBinding.maEdittxtUser.text.toString(), viewBinding.maEdittxtPass.text.toString())
            MessageFactory.showSnackBar(viewBinding.constraintLayoutLoginact, R.string.la_usercreated_msg, true)
            dialog.dismiss()
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    fun validateUserInputEditText(edittext : EditText) : Boolean{
        return (edittext.text.toString().length >= MIN_USERPASS_CHARS ) && (edittext.text.toString().length <= MAX_USERPASS_CHARS )
    }

}