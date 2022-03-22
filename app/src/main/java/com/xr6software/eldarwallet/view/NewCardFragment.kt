package com.xr6software.eldarwallet.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.xr6software.eldarwallet.R
import com.xr6software.eldarwallet.databinding.BalanceFragmentBinding
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
                    Toast.makeText(requireContext(), "La Tarjeta es invalida",Toast.LENGTH_LONG).show()
                }

            }

        }

        viewBinding.cfEdittextCreditcard.doOnTextChanged { text, start, before, count ->

            if (text?.length!! > 0) {
                when (text[0]) {
                    '4','5' -> viewBinding.cfTextCardBrand.text = "VISA"
                    '3' -> viewBinding.cfTextCardBrand.text = "AMERICAN EXPRESS"
                    else -> viewBinding.cfTextCardBrand.text = "Invalid card. Please check your first digit"
                }
            }

        }

    }


    fun proccesCard(userInput: String){

        if(!checkIfCardExists(userInput)) {
            viewModel.addCreditCard(requireActivity(), UserSingletonModel.username, userInput)
            viewModel.setUser(requireActivity())
            Toast.makeText(requireContext(), getString(R.string.nc_frag_card_add_msg),Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(requireContext(), getString(R.string.nc_frag_card_in_db_msg),Toast.LENGTH_LONG).show()
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