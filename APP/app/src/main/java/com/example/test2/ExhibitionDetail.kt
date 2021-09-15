package com.example.test2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test2.activity.home.Exhibition_2D
import com.example.test2.activity.home.Exhibition_3D
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_exhibition_2_d.*
import kotlinx.android.synthetic.main.activity_exhibition_detail.*

class ExhibitionDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exhibition_detail)
        supportActionBar?.hide()

        // 接收home的資料
        var getShowNo = intent.getBundleExtra("bundle")?.getString("showNo")
        var getShowName = intent.getBundleExtra("bundle")?.getString("showName")
        var getShowImgPath = intent.getBundleExtra("bundle")?.getString("showImgPath")
        var getShowText = intent.getBundleExtra("bundle")?.getString("showText")

        exhibitionDetailName.text = getShowName
        exhibitionDetailText.text = getShowText
        Picasso.get().load("http://140.131.114.155/file/$getShowImgPath").into(exhibitionDetailImg)

        btn2D.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("showNo", getShowNo)
            bundle.putString("showName", getShowName)
            val intent = Intent(this, Exhibition_2D::class.java)
            intent.putExtra("bundle", bundle)
            startActivity(intent)

        }
        btn3D.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("showNo", getShowNo)
            bundle.putString("showName", getShowName)
            val intent = Intent(this, Exhibition_3D::class.java)
            intent.putExtra("bundle", bundle)
            startActivity(intent)

        }
    }
}