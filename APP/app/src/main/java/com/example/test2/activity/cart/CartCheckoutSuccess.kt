package com.example.test2.activity.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test2.R
import com.example.test2.activity.profile.OrderQuery
import kotlinx.android.synthetic.main.activity_cart_checkout_success.*

class CartCheckoutSuccess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_checkout_success)
        supportActionBar?.hide()

        val getMemNo: String = intent.getBundleExtra("bundle")?.getString("memNo").toString()

        btnCheckoutToCart.setOnClickListener {
            finish()
        }

        btnCheckoutToOrder.setOnClickListener {
            finish()
            val bundle = Bundle()
            bundle.putString("memNo", getMemNo)
            val intent = Intent(this, OrderQuery::class.java)
            intent.putExtra("bundle", bundle)
            this.startActivity(intent)
        }
    }
}