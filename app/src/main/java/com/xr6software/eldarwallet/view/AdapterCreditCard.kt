package com.xr6software.eldarwallet.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xr6software.eldarwallet.R

//This is class is an adapter to parse data from the cellphones list to the listview items
class AdapterCreditCard() : RecyclerView.Adapter<AdapterCreditCard.ViewHolder>() {

    var creditCardList = ArrayList<String>()

    fun updateDataOnView(newCreditCardList: ArrayList<String>) {
        creditCardList.clear()
        creditCardList.addAll(newCreditCardList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return AdapterCreditCard.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_credit_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val creditCard = creditCardList[position]
        holder.textViewCardNumber.text = creditCard

        when (creditCard[0]) {
            '3' -> holder.imgViewCreditCard.setImageResource(R.drawable.creditcard_american)
            else -> holder.imgViewCreditCard.setImageResource(R.drawable.creditcard_visa)
        }

    }

    override fun getItemCount() = creditCardList.size

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textViewCardNumber: TextView = v.findViewById(R.id.item_textview_cardnumber)
        val imgViewCreditCard: ImageView = v.findViewById(R.id.item_imgview)
    }


}