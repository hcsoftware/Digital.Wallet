package com.xr6software.eldarwallet.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xr6software.eldarwallet.network.APIService
import okhttp3.Response

class PaymentViewModel : ViewModel() {

    private var requestError = MutableLiveData<Boolean>()
    private var bitmapResponse = MutableLiveData<Bitmap>()
    private var isLoading = MutableLiveData<Boolean>()
    private var apiService = APIService()

    fun loadQrCodeApiService(amount: String, size: Int){

        isLoading.value = true
        apiService.loadQRCodeFromApi(amount, size, object : com.xr6software.eldarwallet.network.Callback<Response> {
            override fun onSucces(result: Response?) {

                if (result != null) {
                    var byteArray : ByteArray = result.body!!.bytes()
                    bitmapResponse.postValue(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))
                    requestError.postValue(false)
                    isLoading.postValue(false)
                }
                else {
                    requestError.postValue(true)
                    isLoading.postValue(false)
                }
            }

            override fun onFailure(exception: java.lang.Exception) {
                requestError.postValue(true)
                isLoading.postValue(false)
            }

        })

    }

    fun getRequestStatus() : LiveData<Boolean> {
        return requestError
    }

    fun getBitmapResponse() : LiveData<Bitmap> {
        return bitmapResponse
    }

    fun isLoading() : LiveData<Boolean> {
        return isLoading
    }

}