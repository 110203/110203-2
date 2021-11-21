package com.example.test2.activity.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.MainActivity
import com.example.test2.R
import com.example.test2.data.Goods
import kotlinx.android.synthetic.main.activity_commodity.*


class Commodity : AppCompatActivity() {
    var items = ArrayList<MutableMap<String, Any?>>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity)
        supportActionBar?.hide()

        // 接收Exhibition_2D的資料
        val getShowNo = intent.getBundleExtra("bundle")?.getString("showNo").toString()
        val getShowName = intent.getBundleExtra("bundle")?.getString("showName")
        textView7.text = "$getShowName 商品"

        Goods().postCommodity(getShowNo, items, commList, msgErrorCommodity, applicationContext, this)

        // 返回展覽
        btnToBack2D.setOnClickListener {
            finish()
        }

        // 離開展覽 > 回到首頁
        btnToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}