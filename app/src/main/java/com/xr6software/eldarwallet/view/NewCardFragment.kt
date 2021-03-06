package com.xr6software.eldarwallet.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xr6software.eldarwallet.R
import com.xr6software.eldarwallet.databinding.NewCardFragmentBinding
import com.xr6software.eldarwallet.model.UserSingletonModel
import com.xr6software.eldarwallet.viewmodel.NewCardViewModel

class NewCardFragment : Fragment() {

    companion object {
        fun newInstance() = NewCardFragment()
    }

    private lateinit var viewModel: NewCardViewModel
    private lateinit var viewBinding : NewCardFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = NewCardFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewCardViewModel::class.java)
        viewModel.setUser(requireActivity())
        setListeners()

    }

    fun setListeners(){

        viewBinding.cfButtonAddcard.setOnClickListener {

            if (viewBinding.cfEdittextCreditcard.text.length > 0) {

                var firstDigit = viewBinding.cfEdittextCreditcard.text[0]

                if ((firstDigit == '4' || firstDigit == '5') && (viewBinding.cfEdittextCreditcard.text.length == 16 )) {
                    proccesCard(viewBinding.cfEdittextCreditcard.text.toString())
                }
                else if (firstDigit == '3' && (viewBinding.cfEdittextCreditcard.text.length == 15 )) {
                    proccesCard(viewBinding.cfEdittextCreditcard.text.toString())
                }
                else {
                    MessageFactory.showToast(requireContext(),R.string.ncf_invalid_card_msg, Gravity.CENTER)
                }

            }

        }

        viewBinding.cfEdittextCreditcard.doOnTextChanged { text, start, before, count ->

            if (text?.length!! > 0) {
                when (text[0]) {
                    '4','5' -> { viewBinding.cfTextCardBrand.text = getString(R.string.ncf_visa_msg) ; viewBinding.cfImageviewCard.setImageResource(R.drawable.creditcard_visa) }
                    '3' -> { viewBinding.cfTextCardBrand.text = getString(R.string.ncf_american_msg) ; viewBinding.cfImageviewCard.setImageResource(R.drawable.creditcard_american) }
                    else -> { viewBinding.cfTextCardBrand.text = getString(R.string.ncf_invalid_input_msg) ; viewBinding.cfImageviewCard.setImageDrawable(null) }
                }
            }
            else {
                viewBinding.cfTextCardBrand.text = ""
                viewBinding.cfImageviewCard.setImageDrawable(null)
            }

        }

    }

    fun proccesCard(userInput: String){

        if(!checkIfCardExists(userInput)) {
            viewModel.addCreditCard(requireActivity(), UserSingletonModel.getUser().username.toString(), userInput)
            viewModel.setUser(requireActivity())
            MessageFactory.showToast(requireContext(), R.string.nc_frag_card_add_msg, Gravity.CENTER)
        }
        else {
            MessageFactory.showToast(requireContext(), R.string.nc_frag_card_in_db_msg, Gravity.CENTER)
        }
    }

    fun checkIfCardExists(userInput : String) : Boolean{

        var cardsList: MutableList<String> = ArrayList()
        var userCreditCards: String = viewModel.getUserCreditCards().value.toString()
        var card: String = ""
        var index: Int = 0

        while (userCreditCards.length >= (15) ) {

            when (userCreditCards[index]) {
                '4', '5' -> {
                    card = userCreditCards.substring(index, index + 16);index += 16;
                }
                '3' -> {
                    card = userCreditCards.substring(index, index + 15); index += 15;
                }
            }

            userCreditCards = userCreditCards.substring(index, userCreditCards.length);
            index = 0
            cardsList.add(card)
        }
        return cardsList.contains(userInput)
    }

}