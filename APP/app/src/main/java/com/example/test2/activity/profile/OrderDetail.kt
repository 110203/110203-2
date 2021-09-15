package com.example.test2.activity.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test2.R
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.activity_order_query.*

class OrderDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        supportActionBar?.hide()

        // 接收home的資料
        var getOrNo = intent.getBundleExtra("bundle")?.getString("orNo")
        var getOrState = intent.getBundleExtra("bundle")?.getString("orState")

        txtOrderNo.text = getOrNo
        if(getOrState=="1"){
        }

        ///// BUTTON /////
        // back
        btnToBackOrderDetail.setOnClickListener {
            finish()
        }
        //////////////////
    }
}