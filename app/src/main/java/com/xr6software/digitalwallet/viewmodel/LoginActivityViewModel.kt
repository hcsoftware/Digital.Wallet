package com.xr6software.digitalwallet.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xr6software.digitalwallet.data.UserRepository
import com.xr6software.digitalwallet.model.UserSingleton
import com.xr6software.digitalwallet.model.toUserSingletonModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject



/**
@author Hernán Carrera
@version 1.0
This class handles the data from the activity so it's separated from the view.
*/


@HiltViewModel
class LoginActivityViewModel @Inject constructor(@ApplicationContext val context: Context) : ViewModel() {

    @Inject lateinit var userRepository : UserRepository
    private var validUsername = MutableLiveData<Boolean>()
    val getValidUsername : LiveData<Boolean> get() = validUsername
    private var user = MutableLiveData<UserSingleton>()
    val getUser : LiveData<UserSingleton> get() = user

    /**
     * Inserts new user in Db, with default balance and credit card values
     * @param username string format. new user username.
     * @param password string format. new user password.
     */
    fun insertNewUser(username: String, password: String) {
        userRepository.insertUser(username, password)
    }

    /**
     * Checks if user exists in Db, sets the liveData userNameExits boolean
     * @param username string format. username.
     */
    fun validateUsername(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            validUsername.postValue(userRepository.validateUser(username))
        }
    }

    /**
     * Get the user object from DB if credentials are correct
     * @param username string format. username.
     * @param password string format. password.
     * get UserInDb LiveData if user/pass are Ok.
     */
    fun getUserFromDb(username: String, password: String) {
        CoroutineScope(IO).launch {
            user.postValue(userRepository.getUser(username, password)?.toUserSingletonModel())

        }
    }


}