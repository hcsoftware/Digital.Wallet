package com.xr6software.eldarwallet.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class PaymentViewModel : ViewModel() {

    private var requestError = MutableLiveData<Boolean>()
    private var bitmapResponse = MutableLiveData<Bitmap>()
    private var isLoading = MutableLiveData<Boolean>()

    fun loadQrCodeFromAPI(amount: String, size : Int) {

        CoroutineScope(Dispatchers.IO).launch {

            isLoading.postValue(true)

            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://qrcodeutils.p.rapidapi.com/qrcodefree?text=${amount}&validate=true&size=${size}&type=png&level=M")
                .get()
                .addHeader("X-RapidAPI-Host", "qrcodeutils.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "7a7139a7ddmshffc2d74e52712e6p1341bfjsn4e975643f271")  //Here goes https://rapidapi.com/ app Api-Key
                .build()

            try {
                val response = client.newCall(request).execute()

                if (response != null) {
                    var byteArray : ByteArray = response.body!!.bytes()
                    bitmapResponse.postValue(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))
                    requestError.postValue(false)
                    isLoading.postValue(false)
                }

            }
            catch (exception : Exception) {
                requestError.postValue(true)
                isLoading.postValue(false)
            }

        }

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