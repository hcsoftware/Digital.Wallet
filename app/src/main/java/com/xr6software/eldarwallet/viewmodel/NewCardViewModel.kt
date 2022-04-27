package com.xr6software.eldarwallet.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xr6software.eldarwallet.model.User
import com.xr6software.eldarwallet.model.UserDatabase
import com.xr6software.eldarwallet.model.UserSingletonModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class NewCardViewModel : ViewModel() {

    private var userCards = MutableLiveData<String>()
    private lateinit var user : User

    fun setUser(context : Context) {

        CoroutineScope(IO).launch {
            user = UserDatabase.getDatabase(context).userDao().findByName(UserSingletonModel.username)
            userCards.postValue(user.cards.toString())
        }

    }

    fun addCreditCard(context: Context, username: String,cards : String) {

        var newCards = userCards.value + cards

        CoroutineScope(IO).launch {
            UserDatabase.getDatabase(context).userDao().addCreditCardByName(username,newCards)
        }

    }

    fun getUserCreditCards(): LiveData<String>{
        return userCards
    }

}