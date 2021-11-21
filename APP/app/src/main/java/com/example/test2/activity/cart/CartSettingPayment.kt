package com.example.test2.activity.cart

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.test2.R
import kotlinx.android.synthetic.main.activity_cart_setting_payment.*

class CartSettingPayment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_setting_payment)
        supportActionBar?.hide()

        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val getPayment = intent.getBundleExtra("bundle")?.getString("payment")
        val getCard = intent.getBundleExtra("bundle")?.getString("card")

        when(getPayment){
            "VISA" -> spnCardType.setSelection(0)
            "MasterCard" -> spnCardType.setSelection(1)
            "JCB" -> spnCardType.setSelection(2)
        }
        if(getPayment == "VISA"){

        }
        txtCardNumber.setText(getCard.toString())

        val cardType = arrayOf("信用卡/金融卡")
        val cardTypeAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, cardType)
        cardTypeAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
        spnPaymentMethod.adapter = cardTypeAdapter

        val methodType = arrayOf("VISA", "MasterCard", "JCB")
        val methodTypeAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, methodType)
        methodTypeAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
        spnCardType.adapter = methodTypeAdapter

        btnToBackSetPayment.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("確認不儲存返回上一頁？")
                .setTitle("返回")
                .setPositiveButton("確認"
                ) { _, _ ->
                    finish()
                }
                .setNeutralButton("取消", null)
                .show()
        }

        btnSetPaymentSave.setOnClickListener {
            editor.putString("paymentMethod", spnPaymentMethod.selectedItem.toString()).apply()
            editor.putString("paymentCard", txtCardNumber.text.toString()).apply()
            finish()
        }
    }
}