package com.xr6software.eldarwallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xr6software.eldarwallet.databinding.PaymentFragmentBinding
import com.xr6software.eldarwallet.viewmodel.PaymentViewModel


class PaymentFragment : Fragment() {

    companion object {
        fun newInstance() = PaymentFragment()
    }

    private lateinit var viewModel: PaymentViewModel
    private lateinit var viewBinding: PaymentFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.payment_fragment, container, false)

        viewBinding = PaymentFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)

        setListeners()
        setObserver()

    }

    private fun setObserver() {
        viewModel.getResponse().observe(viewLifecycleOwner, Observer {
            //viewBinding.pfImageQrcode.setImageBitmap(it)
        })
    }

    private fun setListeners() {

        viewBinding.pfButtonGenerateCode.setOnClickListener {


            if (viewBinding.pfEdittextInput.text.length > 0) {

                /*
                LoginActivityViewModel.doAsync {

                    var amount: String = viewBinding.pfEdittextInput.text.toString()

                    val client = OkHttpClient()

                    val mediaType = "application/x-www-form-urlencoded".toMediaTypeOrNull()
                    val body = RequestBody.create(
                        mediaType,
                        "content={$amount}&width=128&height=128&fg-color=%23000000&bg-color=%23ffffff"
                    )
                    val request = Request.Builder()
                        .url("https://neutrinoapi-qr-code.p.rapidapi.com/qr-code")
                        .post(body)
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .addHeader("X-RapidAPI-Host", "neutrinoapi-qr-code.p.rapidapi.com")
                        .addHeader(
                            "X-RapidAPI-Key",
                            "valid api key"
                        )
                        .build()

                    var response = client.newCall(request).execute()
                    if (response.body != null) {
                        val bitmap = BitmapFactory.decodeStream(response.body!!.byteStream())
                        viewBinding.pfImageQrcode.setImageBitmap(bitmap)
                    }

                }.execute()
                   */
            }

        }

    }
}
