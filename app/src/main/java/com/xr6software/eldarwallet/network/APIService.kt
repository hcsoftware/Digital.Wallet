package com.xr6software.eldarwallet.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class APIService {

    fun loadQRCodeFromApi(amount: String, size: Int, callback: Callback<Response>)  {

        CoroutineScope(Dispatchers.IO).launch {

            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://qrcodeutils.p.rapidapi.com/qrcodefree?text=${amount}&validate=true&size=${size}}&type=png&level=M")
                .get()
                .addHeader("X-RapidAPI-Host", "qrcodeutils.p.rapidapi.com")
                .addHeader(
                    "X-RapidAPI-Key",
                    "7a7139a7ddmshffc2d74e52712e6p1341bfjsn4e975643f271"        //API KEY FROM www.rapidapi.com
                )
                .build()

            try {
                callback.onSucces(client.newCall(request).execute())

            } catch (exception: Exception) {
                callback.onFailure(exception)
            }

        }

    }
}