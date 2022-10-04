package com.xr6software.eldarwallet.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.xr6software.eldarwallet.R
import com.xr6software.eldarwallet.databinding.ActivityLoginBinding
import com.xr6software.eldarwallet.model.UserSingleton
import com.xr6software.eldarwallet.viewmodel.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
@author Hernán Carrera
@version 1.0
This Activity manages the App login.
*/

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityLoginBinding
    private val viewModel : LoginActivityViewModel by viewModels()
    @Inject
    lateinit var messageFactory : MessageFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    /**
     * Listener for login button. On click validates editText inputs and if they are ok trigger the viewModel to validate Username in db.
     */
    private fun setOnClickListeners() {

        viewBinding.maButtonLogin.setOnClickListener {

            if (validateUserInputEditText(viewBinding.maEdittxtUser) && validateUserInputEditText(viewBinding.maEdittxtPass)) {
                viewModel.validateUsername(viewBinding.maEdittxtUser.text.toString())
            }
            else {
                messageFactory.showSnackBar(viewBinding.constraintLayoutLoginact, R.string.ma_snackbar_inputerror, false)
            }
        }

    }

    private fun setObservables() {

        /**
         * If the viewModel sets valid userName checks for user in DB. With password.
         */
        viewModel.getValidUsername.observe(this, Observer {
            if(it) {
                viewModel.getUserFromDb(viewBinding.maEdittxtUser.text.toString(), viewBinding.maEdittxtPass.text.toString())
            }
            else {
                showCreateUserDialog()
            }
        })
        /**
         * Observer for the user Obj in the viewModel, if user credentials are Ok the
         * starts Wallet Act. If wrong because password is wrong, shows error msg.
         */
        viewModel.getUser.observe(this) {
            if (it == null) {
                messageFactory.showSnackBar(
                    viewBinding.constraintLayoutLoginact,
                    R.string.ma_snackbar_badcredentials,
                    false
                )
            }
            else {
                UserSingleton.setUser(it)
                startActivity(Intent(this, WalletActivity::class.java))
                finish()
            }
        }

    }

    private fun showCreateUserDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_custom)
        dialog.findViewById<TextView>(R.id.dialog_custom_title).text = getString(R.string.ma_dialog_new_user_title).toString().uppercase()
        dialog.findViewById<TextView>(R.id.dialog_custom_msg).text = getString(R.string.ma_dialog_new_user_message)
        val okButton : Button = dialog.findViewById(R.id.dialog_custom_ok_button)
        val cancelButton : Button = dialog.findViewById(R.id.dialog_custom_cancel_button)
        okButton.setOnClickListener {
            viewModel.insertNewUserInDatabase(viewBinding.maEdittxtUser.text.toString(), viewBinding.maEdittxtPass.text.toString())
            messageFactory.showSnackBar(viewBinding.constraintLayoutLoginact, R.string.la_usercreated_msg, true)
            dialog.dismiss()
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun validateUserInputEditText(edittext : EditText) : Boolean{
        return (edittext.text.toString().length >= MAX_CHARS) && (edittext.text.toString().length <= MIN_CHARS)
    }

    companion object {
        private const val MAX_CHARS : Int = 8
        private const val MIN_CHARS : Int = 4
    }

}