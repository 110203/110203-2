package com.example.test2.activity.cart

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.R
import kotlinx.android.synthetic.main.activity_cart_setting_address.*

class CartSettingAddress : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_setting_address)
        supportActionBar?.hide()

        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val getAddress = intent.getBundleExtra("bundle")?.getString("address")
        val getTel = intent.getBundleExtra("bundle")?.getString("tel")

        txtSetAddress.setText(getAddress.toString())
        txtSetTel.setText(getTel.toString())

        btnToBackSetAddress.setOnClickListener {
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

        btnSetAddressSave.setOnClickListener {
            editor.putString("paymentName", txtSetName.text.toString()).apply()
            editor.putString("paymentAddress", txtSetAddress.text.toString()).apply()
            editor.putString("paymentTel", txtSetTel.text.toString()).apply()
            finish()
        }


    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // if按下Back
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder(this)
                .setMessage("確認已儲存更新內容？")
                .setTitle("返回")
                .setPositiveButton("確認",
                    DialogInterface.OnClickListener { dialog, id ->
                        finish()
                    })
                .setNeutralButton("取消", null)
                .show()
        }
        return false
    }
}