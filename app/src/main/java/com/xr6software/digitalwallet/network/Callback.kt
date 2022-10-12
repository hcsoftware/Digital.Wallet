package com.xr6software.digitalwallet.network

interface Callback<T> {

    fun onSucces(result: T?)

    fun onFailure(exception: Exception)

}