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

class BalanceViewModel : ViewModel() {

    private var username = MutableLiveData<String>()
    private var balance = MutableLiveData<Double>()
    private var cards = MutableLiveData<String>()
    private lateinit var user : User

    fun setUser(context : Context) {

        CoroutineScope(IO).launch {
            user = UserDatabase.getDatabase(context).userDao().findByName(UserSingletonModel.username)
            username.postValue(user.username.toString())
            balance.postValue(((user.balance?: "0.00") as Double?))
            cards.postValue(user.cards.toString())
        }

    }

    fun getBalance() : LiveData<Double> {
        return balance
    }

    fun getUsername() : LiveData<String> {
        return username
    }

    fun getCards() : LiveData<String> {
        return cards
    }


}