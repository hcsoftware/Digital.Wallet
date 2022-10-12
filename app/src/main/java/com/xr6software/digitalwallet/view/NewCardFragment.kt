package com.xr6software.digitalwallet.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xr6software.digitalwallet.R
import com.xr6software.digitalwallet.databinding.NewCardFragmentBinding
import com.xr6software.digitalwallet.utils.MessageFactory
import com.xr6software.digitalwallet.viewmodel.NewCardViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.xr6software.digitalwallet.utils.checkIfCardIsValid

/**
@author Hernán Carrera
@version 1.0
This Fragment is to add new credit cards in user wallet
 */
@AndroidEntryPoint
class NewCardFragment : Fragment() {

    companion object {
        fun newInstance() = NewCardFragment()
    }

    @Inject
    lateinit var messageFactory: MessageFactory
    private val viewModel: NewCardViewModel by viewModels()
    private lateinit var viewBinding: NewCardFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = NewCardFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    /**
     * Sets button click listener and validates userInput in EditText
     */
    private fun setListeners() {

        viewBinding.cfButtonAddcard.setOnClickListener {

            if (viewBinding.cfEdittextCreditcard.text.isNotEmpty()) {

                var firstDigit = viewBinding.cfEdittextCreditcard.text[0]

                if ((firstDigit == '4' || firstDigit == '5') && (viewBinding.cfEdittextCreditcard.text.length == 16)) {
                    processCard(viewBinding.cfEdittextCreditcard.text.toString())
                } else if (firstDigit == '3' && (viewBinding.cfEdittextCreditcard.text.length == 15)) {
                    processCard(viewBinding.cfEdittextCreditcard.text.toString())
                } else {
                    messageFactory.showToast(R.string.ncf_invalid_card_msg, Gravity.CENTER)
                }

            }

        }

        viewBinding.cfEdittextCreditcard.doOnTextChanged { text, start, before, count ->

            if (text?.length!! > 0) {
                when (text[0]) {
                    '4', '5' -> {
                        viewBinding.cfTextCardBrand.text =
                            getString(R.string.ncf_visa_msg); viewBinding.cfImageviewCard.setImageResource(
                            R.drawable.creditcard_visa
                        )
                    }
                    '3' -> {
                        viewBinding.cfTextCardBrand.text =
                            getString(R.string.ncf_american_msg); viewBinding.cfImageviewCard.setImageResource(
                            R.drawable.creditcard_american
                        )
                    }
                    else -> {
                        viewBinding.cfTextCardBrand.text =
                            getString(R.string.ncf_invalid_input_msg); viewBinding.cfImageviewCard.setImageDrawable(
                            null
                        )
                    }
                }
            } else {
                viewBinding.cfTextCardBrand.text = ""
                viewBinding.cfImageviewCard.setImageDrawable(null)
            }

        }

    }

    private fun processCard(userInput: String) {

        if (!userInput.checkIfCardIsValid(viewModel.getUserCreditCards().value.toString())) {
            viewModel.addCreditCard(userInput)
            messageFactory.showToast(R.string.nc_frag_card_add_msg, Gravity.CENTER)
        } else {
            messageFactory.showToast(R.string.nc_frag_card_in_db_msg, Gravity.CENTER)
        }
    }



}




