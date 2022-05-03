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

    //This LiveData Object returns The user once its loged on.
    private var userInDatabase = MutableLiveData<User>()
    //This LiveData Object returns the username if its found in the Db.
    private var usernameExists = MutableLiveData<Boolean>()
    //This LiveData Object retuns if the credentials are invalid.
    private var validCredentials = MutableLiveData<Boolean>()
    //This object holds the user
    private var user: User = User("", "", 10230.23, "")

    fun insertUserInDatabase(context: Context, username: String, password: String): Unit {

        CoroutineScope(IO).launch {
            UserDatabase.getDatabase(context).userDao().insertUser(
                User(
                    username = username,
                    pass = password,
                    balance = 5230.22,
                    cards = "4580067664664646312345678978542"
                )
            )
        }

    }

    //This methods search for the given username in the DB. If it exists it's loaded in the usernameExists livedata
    fun checkIfUsernameExists(context: Context, username: String) {

        CoroutineScope(IO).launch {
            var getUser  = UserDatabase.getDatabase(context).userDao().findByName(username)
            usernameExists.postValue(getUser != null)
        }

    }
    //LiveData usernameExists Observable method.
    fun getUserNameExists(): LiveData<Boolean> {
        return usernameExists
    }

    //This method check for usr and pass and retrieve the user object if correct (into the userIndatabase livedata Object.).
    fun getUserFromDb(context: Context, username: String, password: String) {

        CoroutineScope(IO).launch {
            var getUser = UserDatabase.getDatabase(context).userDao().getUserFromDatabase(username, password)
            validCredentials.postValue(getUser != null)
            userInDatabase.postValue(getUser)
        }

    }
    //LiveData User object Observable method.
    fun getuserInDatabase(): LiveData<User> {
        return userInDatabase
    }

    //LiveData usernameExists Observable method.
    fun getValidCredentials(): LiveData<Boolean> {
        return validCredentials
    }

}