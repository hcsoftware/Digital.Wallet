package com.xr6software.digitalwallet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xr6software.digitalwallet.model.UserSingleton

class BalanceViewModel : ViewModel() {

    private var username = MutableLiveData<String>()
    private var balance = MutableLiveData<Double>()
    private var cards = MutableLiveData<String>()

    fun getBalance() : LiveData<Double> {
        balance.value = UserSingleton.getUser().balance
        return  balance
    }

    fun getUsername() : LiveData<String> {
        username.value = UserSingleton.getUser().userName
        return username
    }

    fun getCards() : LiveData<String?> {
        cards.value = UserSingleton.getUser().cards
        return cards
    }


}