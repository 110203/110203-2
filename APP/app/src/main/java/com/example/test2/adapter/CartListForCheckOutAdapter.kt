package com.example.test2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.R
import com.squareup.picasso.Picasso

class CartListForCheckOutAdapter(val items: ArrayList<Map<String, Any?>>) : RecyclerView.Adapter<CartListForCheckOutAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_checkout_item, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val test = items[position]["data$position"].toString()
        val test2 = test.split(", ")
        val cartNo = test2[0].split("=")[1]
        val cartGoodNo = test2[1].split("=")[1]
        val cartGoodName = test2[2].split("=")[1]
        val cartGoodPrice = test2[3].split("=")[1]
        val cartGoodAmount = test2[4].split("=")[1]
        val cartGoodStock = test2[5].split("=")[1]
        val cartGoodImg = test2[6].split("=")[1].replace("}", "")

        val imgUrl: String? = holder.toto?.getString(R.string.BASE_IMG_URL)
        Picasso.get().load(imgUrl + cartGoodImg).into(holder.cartCheckoutImg)

        holder.cartCheckoutName.text = cartGoodName
        holder.cartCheckoutPrice.text = cartGoodPrice
        holder.cartCheckoutAmount.text = cartGoodAmount
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val cartCheckoutImg: ImageView = v.findViewById(R.id.imgCartCheckout)
        val cartCheckoutName: TextView = v.findViewById(R.id.txtCartCheckoutName)
        val cartCheckoutPrice: TextView = v.findViewById(R.id.txtCartCheckoutPrice)
        val cartCheckoutAmount: TextView = v.findViewById(R.id.txtCartCheckoutAmount)

        val toto: Context? = v.context
    }

}
