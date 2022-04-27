package com.xr6software.eldarwallet.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xr6software.eldarwallet.model.User
import com.xr6software.eldarwallet.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/*
Login activity -> ViewModel
 */
class LoginActivityViewModel() : ViewModel() {

    private var userInDatabase = MutableLiveData<User>()
    private var user : User = User("","",10230.23,"")

    fun insertUserInDatabase(context: Context, username: String,password : String) : Unit {

        CoroutineScope(IO).launch {
            UserDatabase.getDatabase(context).userDao().insertUser(User(username = username,pass = password, balance = 10230.23, cards = ""))
        }

    }

    fun checkIfUserExists(context: Context, username : String)  {

        CoroutineScope(IO).launch {
            user = UserDatabase.getDatabase(context).userDao().findByName(username)
            userInDatabase.postValue(user)
        }

    }

    fun getuserInDatabase() : LiveData<User> {
        return userInDatabase
    }


}