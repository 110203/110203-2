package com.example.test2.activity.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        // 接收ExhibitionDetail的資料
        val getShowNo = intent.getBundleExtra("bundle")?.getString("showNo")
        val getShowName = intent.getBundleExtra("bundle")?.getString("showName")
        val getEUrl2D = intent.getBundleExtra("bundle")?.getString("eUrl2D")
        txtShowName2D.text = getShowName.toString()

        // 顯示網頁
        val webView = findViewById<WebView>(R.id.webview)
        webView.webViewClient = WebViewClient()
        if (getEUrl2D != null && getEUrl2D != "not built" ) {
            msgLoading.visibility = View.VISIBLE
            webView.loadUrl(getEUrl2D)
        } else {
            webview.visibility = View.INVISIBLE
            msgNotBuilt2D.visibility = View.VISIBLE
        }

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