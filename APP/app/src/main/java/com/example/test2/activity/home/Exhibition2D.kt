package com.example.test2.activity.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.test2.R
import kotlinx.android.synthetic.main.activity_exhibition_2_d.*

class Exhibition2D : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exhibition_2_d)
        supportActionBar?.hide()

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

        btnToBackHome_2D.setOnClickListener {
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