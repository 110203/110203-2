package com.example.test2.activity.profile

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test2.R
import com.example.test2.data.Order
import kotlinx.android.synthetic.main.activity_order_query.*

class OrderQuery : AppCompatActivity() {
    var items = ArrayList<MutableMap<String, Any?>>()
    private var getMemNo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_query)
        supportActionBar?.hide()

        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        getMemNo = sharedPref?.getString("memNo", "").toString()
        Order().postOrderRecord(getMemNo, orderList, msgNotFoundOrder, this)

        ///// BUTTON /////
        // back
        btnToBackProfileOrderQuery.setOnClickListener {
            finish()
        }
        //////////////////
    }
}
