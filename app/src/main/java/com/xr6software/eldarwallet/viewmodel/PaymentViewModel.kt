package com.xr6software.eldarwallet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Response

class PaymentViewModel : ViewModel() {

    private var response = MutableLiveData<Response>()

    fun loadQrCodeFromAPI(amount: String) {


    }

    fun getResponse() : LiveData<Response> {
        return response
    }

}