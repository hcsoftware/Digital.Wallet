package com.xr6software.digitalwallet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xr6software.digitalwallet.data.UserRepository
import com.xr6software.digitalwallet.model.User
import com.xr6software.digitalwallet.model.UserSingleton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewCardViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var userRepository : UserRepository
    private var userCards = MutableLiveData<String>()

    fun addCreditCard(cards : String) {

        UserSingleton.getUser().cards =  UserSingleton.getUser().cards  + cards
        CoroutineScope(IO).launch {
            val user : User? = userRepository.getUser(UserSingleton.getUser().userName, UserSingleton.getUser().password)
            user?.cards =  UserSingleton.getUser().cards
            userRepository.updateUser(user)
        }

    }

    fun getUserCreditCards(): LiveData<String>{
        userCards.value = UserSingleton.getUser().cards
        return userCards
    }

}