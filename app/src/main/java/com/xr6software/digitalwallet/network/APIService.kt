package com.xr6software.digitalwallet.network

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * This class interacts with the remote ApiServer and the PaymentViewModel.
 */
@InstallIn(SingletonComponent::class)
@Module
class APIService {

    /** Gets the ByteArray in response from the Api, according to params.
     * @param amount String
     * @param size Int
     * @param callback Callback with onSuccess an onFailure Imp
     */
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