package com.example.test2.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.R
import com.example.test2.data.ShoppingCart
import com.squareup.picasso.Picasso
var selectGoodNoList = ArrayList<Any>()

class CartListForShoppingCartGetData {
    fun getSelectGoodNo(): ArrayList<Any> {
        return selectGoodNoList
    }

    fun getSelectGoodItems(): ArrayList<Any> {
        return selectGoodNoList
    }

}
class CartListForShoppingCartAdapter(
    val memNo: String,
    private val items: ArrayList<MutableMap<String, Any?>>,
    val msgError: TextView,
    val applicationList: RecyclerView,
    val btnCheckout: Button,
    val mActivity: Activity?,
    var totPrice: Int
) : RecyclerView.Adapter<CartListForShoppingCartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_cart_item, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photoPath = items[position]["cartGoodImg"].toString()
        val cartNo = items[position]["cartNo"].toString()
        val cartGoodNo = items[position]["cartGoodNo"].toString()
        val cartGoodName = items[position]["cartGoodName"].toString()
        val cartGoodPrice = items[position]["cartGoodPrice"].toString()
        val cartGoodAmount = items[position]["cartGoodAmount"].toString()
        val cartGoodStock = items[position]["cartGoodStock"].toString().toInt()

        holder.cartGoodName.text = cartGoodName
        holder.cartGoodPrice.text = cartGoodPrice
        holder.cartGoodAmount.text = cartGoodAmount

        val imgUrl: String? = holder.toto?.getString(R.string.BASE_IMG_URL)
        Picasso.get().load(imgUrl + photoPath).into(holder.cartGoodImg)


        checkStockAndSetButton(cartGoodStock, cartGoodAmount.toInt(), holder)

        var amount = cartGoodAmount.toInt()

        holder.chkCart.setOnClickListener {
            val scNoItem = items[position]
            val scNoItem2 = mutableMapOf<String, Any?>()
            scNoItem2[cartNo] = scNoItem
            if(holder.chkCart.isChecked){
                updateCheckout("add", cartGoodPrice.toInt(), amount)
                selectGoodNoList.add(scNoItem2)
            }else{
                updateCheckout("sub", cartGoodPrice.toInt(), amount)
                selectGoodNoList.remove(scNoItem2)
            }

        }

        holder.btnAddCart.setOnClickListener {
            amount += 1
            ShoppingCart().postUpdateCart(memNo, cartGoodNo, 1, holder.toto, holder.cartGoodAmount).toString()
            if(holder.chkCart.isChecked){
                updateCheckout("add", cartGoodPrice.toInt(), 1)
            }
            checkStockAndSetButton(cartGoodStock, amount, holder)
        }

        holder.btnSubCart.setOnClickListener {
            amount += -1
            ShoppingCart().postUpdateCart(memNo, cartGoodNo, -1, holder.toto, holder.cartGoodAmount).toString()
            if(holder.chkCart.isChecked){
                updateCheckout("sub", cartGoodPrice.toInt(), 1)
            }
            checkStockAndSetButton(cartGoodStock, amount, holder)
        }

        holder.btnDeleteCart.setOnClickListener {
            ShoppingCart().postDeleteCart(memNo, cartGoodNo, holder.toto, msgError, applicationList, btnCheckout, mActivity)
            if(holder.chkCart.isChecked){
                updateCheckout("sub", cartGoodPrice.toInt(), amount)
            }
        }
    }

    private fun checkStockAndSetButton(cartGoodStock: Int, cartGoodAmount: Int, holder: ViewHolder) {
        if(cartGoodStock == cartGoodAmount){
            holder.btnAddCart.visibility = View.INVISIBLE
        }else{
            holder.btnAddCart.visibility = View.VISIBLE
        }

        if(cartGoodAmount == 1){
            holder.btnSubCart.visibility = View.INVISIBLE
        }else{
            holder.btnSubCart.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateCheckout(type: String, price: Int, amount: Int) {
        when(type){
            "add" -> totPrice += price * amount
            "sub" -> totPrice -= price * amount
        }
        val s = String.format("%,d", totPrice).replace(',', ',')
        btnCheckout.text = "填寫購買資訊(\$$s)"
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val cartGoodName: TextView = v.findViewById(R.id.txtCommodityName)
        val cartGoodPrice: TextView = v.findViewById(R.id.txtCommodityPrice)
        val cartGoodAmount: TextView = v.findViewById(R.id.txtCommodityAmount)
        val cartGoodImg: ImageView = v.findViewById(R.id.imgCommodityImg)
        val chkCart: CheckBox = v.findViewById(R.id.chkCart)
        val btnAddCart: Button = v.findViewById(R.id.btnAddCart)
        val btnSubCart: Button = v.findViewById(R.id.btnSubCart)
        val btnDeleteCart: Button = v.findViewById(R.id.btnDeleteCart)

        val toto: Context? = v.context
    }

}
