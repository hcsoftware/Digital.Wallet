package com.xr6software.digitalwallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xr6software.digitalwallet.R
import com.xr6software.digitalwallet.databinding.BalanceFragmentBinding
import com.xr6software.digitalwallet.viewmodel.BalanceViewModel


/**
@author Hernán Carrera
@version 1.0
This Fragment shows the balance and client credit cards.
 */
class BalanceFragment : Fragment() {

    companion object {
        fun newInstance() = BalanceFragment()
    }

    private val viewModel: BalanceViewModel by viewModels()
    private lateinit var viewBinding: BalanceFragmentBinding
    private lateinit var creditCardAdapter: AdapterCreditCard

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = BalanceFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Init adapter and links it view recycler view
        creditCardAdapter = AdapterCreditCard()
        val recyclerView : RecyclerView = viewBinding.bfItemHolder

        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = creditCardAdapter
        }


        setObservables()

    }

    private fun setObservables() {

        viewModel.getBalance().observe(viewLifecycleOwner, Observer {
            viewBinding.bfTxtBalance.text = getString(R.string.bf_balance_msg) + "$it"
        })
        viewModel.getCards().observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank() ) creditCardAdapter.updateDataOnView(getCardsFromString(it))
        })

    }

    /**
     * This method separates the different cards inside the userCards String, and returns a ArrayList with all the cards.
     * @param cards String with all the cards together.
     * @return ArrayList<String> with the cards separated.
     */
    private fun getCardsFromString(cards: String): ArrayList<String> {

        var cardsList = ArrayList<String>()
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