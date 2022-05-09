package com.xr6software.eldarwallet.view

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.xr6software.eldarwallet.R

class MessageFactory {

    companion object {

        fun showToast(context: Context, msg: Int, gravity : Int) {

            val toast : Toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                toast.setGravity(gravity, 0, 0)
                toast.show()
        }

        fun showSnackBar(parentView: View, msg: Int) {

            Snackbar.make(parentView, msg, Snackbar.LENGTH_SHORT)

        }

    }


}