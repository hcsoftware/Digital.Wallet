package com.xr6software.eldarwallet.view

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xr6software.eldarwallet.R
import com.xr6software.eldarwallet.databinding.PaymentFragmentBinding
import com.xr6software.eldarwallet.viewmodel.PaymentViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PaymentFragment : Fragment() {

    companion object {
        fun newInstance() = PaymentFragment()
    }

    private val DEFAULT_QRCODE_SIZE : Int = 150  //QR Code image size in pixels min = 150 ; Max : 250 //

    private lateinit var viewModel: PaymentViewModel
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
        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)

        setListeners()
        setObserver()

    }

    private fun setObserver() {

        viewModel.getRequestStatus().observe(viewLifecycleOwner, Observer {
            if (it) {
                MessageFactory.showToast(requireContext(), R.string.pf_api_loading_error, Gravity.CENTER)
                viewBinding.pfImageQrcode.setImageResource(R.drawable.ic_error_loading)
            }
        })

        viewModel.isLoading().observe(viewLifecycleOwner, Observer {
            if (it) {
                viewBinding.pfImageQrcode.setImageResource(R.drawable.ic_downloading)
                val downloadAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.donwload_rotation_animation)
                viewBinding.pfImageQrcode.startAnimation(downloadAnimation)
            }
            else {
                viewBinding.pfImageQrcode.clearAnimation()
            }
        })

        viewModel.getBitmapResponse().observe(viewLifecycleOwner, Observer {
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
                MessageFactory.showToast(requireContext(), R.string.pf_invalid_input, Gravity.CENTER)
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
