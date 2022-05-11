package com.xr6software.eldarwallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xr6software.eldarwallet.R
import com.xr6software.eldarwallet.databinding.BalanceFragmentBinding
import com.xr6software.eldarwallet.viewmodel.BalanceViewModel


class BalanceFragment : Fragment() {

    companion object {
        fun newInstance() = BalanceFragment()
    }

    private lateinit var viewModel: BalanceViewModel
    private lateinit var viewBinding: BalanceFragmentBinding
    private lateinit var creditCardAdapter: AdapterCreditCard

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

        //Init adapter and links it view recycler view
        creditCardAdapter = AdapterCreditCard()
        val recyclerView : RecyclerView = viewBinding.bfItemHolder

        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = creditCardAdapter
        }


        setObservables()

    }

    fun setObservables() {

        viewModel.getBalance().observe(viewLifecycleOwner, Observer {
            viewBinding.bfTxtBalance.text = getString(R.string.bf_balance_msg) + "$it"
        })
        viewModel.getCards().observe(viewLifecycleOwner, Observer {
            if (it.length >=0 ) creditCardAdapter.updateDataOnView(getCardsFromString(it))
        })

    }

    fun getCardsFromString(cards: String): ArrayList<String> {

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