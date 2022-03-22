package com.xr6software.eldarwallet.viewmodel

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xr6software.eldarwallet.model.User
import com.xr6software.eldarwallet.model.UserDatabase
/*
Login activity -> ViewModel
 */
class LoginActivityViewModel() : ViewModel() {

    private var userInDatabase = MutableLiveData<User>()
    private var user : User = User("","",10230.23,"")

    fun insertUserInDatabase(context: Context, username: String,password : String) : Unit {

        doAsync {
            UserDatabase.getDatabase(context).userDao().insertUser(User(username = username,pass = password, balance = 10230.23, cards = ""))
        }.execute()

    }

    fun checkIfUserExists(context: Context, username : String)  {

        doAsync {
            user = UserDatabase.getDatabase(context).userDao().findByName(username)
            userInDatabase.postValue(user)
        }.execute()

    }

    fun test(context: Context){
        doAsync {
            var data = UserDatabase.getDatabase(context).userDao().getAll()
            data.forEach {
                Log.d("user: " , it.username.toString())
            }
        }.execute()
    }

    fun getuserInDatabase() : LiveData<User> {
        return userInDatabase
    }

    class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            handler()
            return null
        }
    }

}