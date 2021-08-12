package com.example.test2.activity.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.test2.R
import kotlinx.android.synthetic.main.activity_exhibition_2_d.*

class Exhibition_2D : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exhibition_2_d)

        // 隱藏TitleBar
        supportActionBar?.hide()

        // 接收home的資料
        var getShowNo = intent.getBundleExtra("bundle")?.getString("showNo")
        var getShowName = intent.getBundleExtra("bundle")?.getString("showName")
        txtShowName2D.text = getShowName.toString()

        // 顯示網頁(Roundme)
        val webView = findViewById<WebView>(R.id.webview)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://do649bnc9x4hqjt8vrtjca-on.drv.tw/exhibition/")
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true // 讓網頁js可以使用


        btnToBackHome.setOnClickListener {
            finish()
        }

        btnToCommdityList.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("showNo", getShowNo)
            bundle.putString("showName", getShowName)
            val intent = Intent(this, Commodity::class.java)
            intent.putExtra("bundle", bundle)
            this.startActivity(intent)
        }
    }


}