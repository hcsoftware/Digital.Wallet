package com.xr6software.eldarwallet.network

interface Callback<T> {

    fun onSucces(result: T?)

    fun onFailure(exception: Exception)

}