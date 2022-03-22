package com.xr6software.eldarwallet.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xr6software.eldarwallet.model.User
import com.xr6software.eldarwallet.model.UserDatabase
import com.xr6software.eldarwallet.model.UserSingletonModel

class NewCardViewModel : ViewModel() {

    private var userCards = MutableLiveData<String>()
    private lateinit var user : User

    fun setUser(context : Context) {

        LoginActivityViewModel.doAsync {
            user = UserDatabase.getDatabase(context).userDao().findByName(UserSingletonModel.username)
            userCards.postValue(user.cards.toString())
        }.execute()

    }

    fun addCreditCard(context: Context, username: String,cards : String) : Unit {

        var newCards = userCards.value + cards

        LoginActivityViewModel.doAsync {
            UserDatabase.getDatabase(context).userDao().addCreditCardByName(username,newCards)
        }.execute()

    }

    fun getUserCreditCards(): LiveData<String>{
        return userCards
    }

}