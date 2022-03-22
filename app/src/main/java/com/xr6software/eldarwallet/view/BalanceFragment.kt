package com.xr6software.eldarwallet.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.VerifiedInputEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xr6software.eldarwallet.R
import com.xr6software.eldarwallet.databinding.BalanceFragmentBinding
import com.xr6software.eldarwallet.viewmodel.BalanceViewModel


class BalanceFragment : Fragment() {

    companion object {
        fun newInstance() = BalanceFragment()
    }

    private lateinit var viewModel: BalanceViewModel
    private lateinit var viewBinding: BalanceFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = BalanceFragmentBinding.inflate(inflater, container, false)

        return viewBinding.root
        //return inflater.inflate(R.layout.balance_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(BalanceViewModel::class.java)
        viewModel.setUser(requireActivity())

        setObservables()

    }

    fun setObservables() {

        viewModel.getUsername().observe(viewLifecycleOwner, Observer {
            viewBinding.bfTextviewUsername.text = getString(R.string.bf_welcome_msg) + it

        })
        viewModel.getBalance().observe(viewLifecycleOwner, Observer {
            viewBinding.bfTextviewBalance.text = getString(R.string.bf_balance_msg) + ": $ $it"
        })
        viewModel.getCards().observe(viewLifecycleOwner, Observer {
            if (it.length >=0 )fillSpinner(getCardsFromString(it))
        })

    }

    fun fillSpinner(cardList: List<String>) {

        val adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item,cardList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewBinding.bfSpinnerCards.adapter = adapter

    }

    fun getCardsFromString(cards: String): List<String> {

        var cardsList: MutableList<String> = ArrayList()
        var newCards: String = cards
        var card: String = ""
        var index: Int = 0

        while (newCards.length >= (15) ) {

            when (newCards[index]) {
                '4', '5' -> {
                    card = newCards.substring(index, index + 16);index += 16;
                }
                '3' -> {
                    card = newCards.substring(index, index + 15); index += 15;
                }
            }

            newCards = newCards.substring(index, newCards.length);
            index = 0
            cardsList.add(card)

        }

        return cardsList
    }

}