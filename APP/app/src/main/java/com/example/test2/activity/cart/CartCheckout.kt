package com.example.test2.activity.cart

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.R
import com.example.test2.adapter.CartListForCheckoutAdapter
import com.example.test2.data.Order
import kotlinx.android.synthetic.main.activity_cart_checkout.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.Map
import kotlin.collections.set

class CartCheckout : AppCompatActivity() {
    private var cartAddress = ""
    private var cartTel = ""
    private var cartPayment = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_checkout)
        supportActionBar?.hide()

        cartAddress = txtCheckoutAddress.text.toString()
        cartTel = txtCheckoutTel.text.toString()
        cartPayment = txtCheckoutPayment.text.toString()

        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        var address : String? = sharedPref.getString("paymentAddress", "")
        var tel : String? = sharedPref.getString("paymentTel", "")
        var payment : String? = sharedPref.getString("paymentMethod", "")
        val card : String? = sharedPref.getString("paymentCard", "")

        txtCheckoutAddress.text = address
        txtCheckoutTel.text = tel
        txtCheckoutPayment.text = payment
        txtCheckoutCard.text = card

        btnToBackNotifications.setOnClickListener {
            finish()
        }

        // 接收Exhibition_2D的資料
        val getMemNo = intent.getBundleExtra("bundle")?.getString("memNo").toString()
        val getSelectGoodTotPrice = intent.getBundleExtra("bundle")?.getInt("selectGoodTotPrice").toString().toInt()
        val getSelectCartCount = intent.getBundleExtra("bundle")?.getString("selectCartCount").toString().toInt()
        val items = ArrayList<Map<String, Any?>>()
        for(i in 0 until getSelectCartCount){
            val item = HashMap<String, Any?>()
            item["data$i"] = intent.getBundleExtra("bundle")?.getString("data$i")
            items.add(item)
        }

        // recycler
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        applicationCheckoutList.layoutManager = layoutManager
        applicationCheckoutList.adapter = CartListForCheckoutAdapter(items)

        btnEditAddress.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("address", txtCheckoutAddress.text.toString())
            bundle.putString("tel", txtCheckoutTel.text.toString())
            val intent = Intent(this, CartSettingAddress::class.java)
            intent.putExtra("bundle", bundle)
            startActivity(intent)
        }

        btnEditPayment.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("payment", txtCheckoutPayment.text.toString())
            bundle.putString("card", txtCheckoutCard.text.toString())
            val intent = Intent(this, CartSettingPayment::class.java)
            intent.putExtra("bundle", bundle)
            startActivity(intent)
        }

        val deliveryCharge = 60
        val orderTotPrice = getSelectGoodTotPrice + deliveryCharge
        txtGoodTotPrice.text = String.format("%,d", getSelectGoodTotPrice).replace(',', ',')
        txtCharge.text = deliveryCharge.toString()
        txtOrderTotPrice.text = String.format("%,d", orderTotPrice).replace(',', ',')

        btnDoubleCheckout.setOnClickListener {
            address = txtCheckoutAddress.text.toString()
            tel = txtCheckoutTel.text.toString()
            payment = txtCheckoutPayment.text.toString()
            if(address == "" || tel == "" || payment == ""){
                Toast.makeText(this, "地址、電話、支付方式請勿留空喔！", Toast.LENGTH_LONG).show()
            }else{
                btnDoubleCheckout.isEnabled = false
                btnDoubleCheckout.isClickable = false
                Order().postAddOrder(this, getMemNo, cartAddress, cartTel, cartPayment, items, orderTotPrice)
            }
        }
    }

    override fun onResume() {
        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        val address : String? = sharedPref.getString("paymentAddress", "")
        val tel : String? = sharedPref.getString("paymentTel", "")
        val payment : String? = sharedPref.getString("paymentMethod", "")
        val card : String? = sharedPref.getString("paymentCard", "")
        cartAddress = txtCheckoutAddress.text.toString()
        cartTel = txtCheckoutTel.text.toString()
        cartPayment = txtCheckoutPayment.text.toString()

        txtCheckoutAddress.text = address
        txtCheckoutTel.text = tel
        txtCheckoutPayment.text = payment
        txtCheckoutCard.text = card

        super.onResume()
    }
}