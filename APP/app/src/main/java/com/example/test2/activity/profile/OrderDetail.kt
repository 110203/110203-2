package com.example.test2.activity.profile

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.test2.R
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetail : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        supportActionBar?.hide()

        val getOrNo = intent.getBundleExtra("bundle")?.getString("orNo")
        val getOrState = intent.getBundleExtra("bundle")?.getString("orState")
        val getOrTotalPrice = intent.getBundleExtra("bundle")?.getString("orTotalPrice")?.toInt()

        txtOrderNo.text = getOrNo
        val s = String.format("%,d", getOrTotalPrice).replace(',', ',')
        txtShowOrderTot.text = "$$s"
        if(getOrState=="1"){
            txtOrderReadyDetail.setTextColor(this.getColor(R.color.fourth))
        }
        if(getOrState=="2"){
            txtOrderReadyDetail.setTextColor(this.getColor(R.color.fourth))
            txtOrderShipDetail.setTextColor(this.getColor(R.color.fourth))
        }
        if(getOrState=="3" || getOrState=="4"){
            txtOrderReadyDetail.setTextColor(this.getColor(R.color.fourth))
            txtOrderShipDetail.setTextColor(this.getColor(R.color.fourth))
            txtOrderArrDetail.setTextColor(this.getColor(R.color.fourth))
        }

        ///// BUTTON /////
        // back
        btnToBackOrderDetail.setOnClickListener {
            finish()
        }
        //////////////////
    }
}