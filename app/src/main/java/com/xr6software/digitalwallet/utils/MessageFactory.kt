package com.xr6software.digitalwallet.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

/**
 * Factory pattern to show messages to user as Toast or Snackbar
 */
@InstallIn(SingletonComponent::class)
@Module
class MessageFactory @Inject constructor(@ApplicationContext val context: Context) {


    fun showToast(msg: Int, gravity: Int) {

        val toast: Toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast.setGravity(gravity, 0, 0)
        toast.show()

    }

    fun showSnackBar(parentView: View, msg: Int, showOk: Boolean) {
        val snackBar: Snackbar = Snackbar.make(
            parentView,
            msg, Snackbar.LENGTH_SHORT
        )
        snackBar.setBackgroundTint(Color.parseColor("#003495"))
        snackBar.setActionTextColor(Color.parseColor("#EB1734"))
        if (showOk) {
            snackBar.setAction("OK") { }
        }
        snackBar.show()
    }

}