package com.xr6software.eldarwallet.view

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MessageFactory {

    companion object {

        fun showToast(context: Context, msg: Int, gravity : Int) {

            val toast : Toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                toast.setGravity(gravity, 0, 0)
                toast.show()
        }

        fun showSnackBar(parentView: View, msg: Int, showOk : Boolean) {
            var snackbar : Snackbar = Snackbar.make(parentView,
                msg, Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.parseColor("#003495"))
            snackbar.setActionTextColor(Color.parseColor("#EB1734"))
            if (showOk) {  snackbar.setAction("OK"){ } }
            snackbar.show()
        }

    }

}