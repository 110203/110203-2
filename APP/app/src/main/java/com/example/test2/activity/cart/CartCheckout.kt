package com.example.test2.activity.cart

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.R
import com.example.test2.adapter.CartListForCheckOutAdapter
import com.example.test2.adapter.ExhibitionListForHomeAdapter
import kotlinx.android.synthetic.main.activity_application_list.*
import kotlinx.android.synthetic.main.activity_cart_checkout.*
import kotlinx.android.synthetic.main.activity_commodity.*
import kotlinx.android.synthetic.main.activity_exhibition_2_d.*
import kotlinx.android.synthetic.main.activity_exhibition_2_d.btnToBackHome
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList

class CartCheckout : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_checkout)
        supportActionBar?.hide()

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
        applicationCheckoutList.adapter = CartListForCheckOutAdapter(items)


        val deliveryCharge = 60
        val orderTotPrice = getSelectGoodTotPrice + deliveryCharge
        txtGoodTotPrice.text = String.format("%,d", getSelectGoodTotPrice).replace(',', ',')
        txtCharge.text = deliveryCharge.toString()
        txtOrderTotPrice.text = String.format("%,d", orderTotPrice).replace(',', ',')


    }
}