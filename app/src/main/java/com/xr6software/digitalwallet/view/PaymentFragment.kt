package com.xr6software.digitalwallet.view

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.xr6software.digitalwallet.R
import com.xr6software.digitalwallet.databinding.PaymentFragmentBinding
import com.xr6software.digitalwallet.utils.MessageFactory
import com.xr6software.digitalwallet.viewmodel.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
@author Hernán Carrera
@version 1.0
This Fragment shows QrCode Image given an amount.
 */
@AndroidEntryPoint
class PaymentFragment : Fragment() {

    companion object {
        fun newInstance() = PaymentFragment()
    }

    private val DEFAULT_QRCODE_SIZE : Int = 150  //QR Code image size in pixels min = 150 ; Max : 250 //

    @Inject
    lateinit var messageFactory : MessageFactory
    private val viewModel: PaymentViewModel by viewModels()
    private lateinit var viewBinding: PaymentFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = PaymentFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListeners()
        setObserver()

    }

    private fun setObserver() {

        viewModel.getRequestStatus().observe(viewLifecycleOwner, Observer {
            if (it) {
                messageFactory.showSnackBar(viewBinding.root,R.string.pf_api_loading_error, false)
                viewBinding.pfImageQrcode.visibility = View.VISIBLE
                viewBinding.pfImageQrcode.setImageResource(R.drawable.ic_error_loading)
            }
        })

        viewModel.isLoading().observe(viewLifecycleOwner, Observer {
            if (it) {
                viewBinding.pfLoadingBar.visibility = View.VISIBLE
                viewBinding.pfImageQrcode.visibility = View.GONE
            }
            else {
                viewBinding.pfLoadingBar.visibility = View.GONE
            }
        })

        viewModel.getBitmapResponse().observe(viewLifecycleOwner, Observer {
            viewBinding.pfImageQrcode.visibility = View.VISIBLE
            loadImageInView(it)
        })

    }

    private fun setListeners() {
        viewBinding.pfButtonGenerateCode.setOnClickListener {

            if (viewBinding.pfEdittextInput.text.isNotEmpty()) {

                viewModel.loadQrCodeApiService(viewBinding.pfEdittextInput.text.toString(), DEFAULT_QRCODE_SIZE)
                view?.let { activity?.hideKeyboard(it) }

            }
            else {
                messageFactory.showSnackBar(viewBinding.root,R.string.pf_invalid_input, false)
            }

        }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun loadImageInView(bmp : Bitmap) {

        GlobalScope.launch(Main) {
            viewBinding.pfImageQrcode.setImageBitmap(bmp);
        }

    }
}
