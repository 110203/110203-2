package com.example.test2.activity.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test2.R
import kotlinx.android.synthetic.main.activity_cart_checkout.*
import kotlinx.android.synthetic.main.activity_exhibition_2_d.*
import kotlinx.android.synthetic.main.activity_exhibition_2_d.btnToBackHome

class CartCheckout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_checkout)
        supportActionBar?.hide()

        btnToBackNotifications.setOnClickListener {
            finish()
        }

    }
}