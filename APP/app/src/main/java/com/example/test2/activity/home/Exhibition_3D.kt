package com.example.test2.activity.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test2.R
import kotlinx.android.synthetic.main.activity_exhibition_3_d.*

class Exhibition_3D : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exhibition_3_d)
        supportActionBar?.hide()

        // 接收home的資料
        var getShowNo = intent.getBundleExtra("bundle")?.getString("showNo")
        var getShowName = intent.getBundleExtra("bundle")?.getString("showName")
        txtShowName3D.text = getShowName.toString()

        btnToBackHome_3D.setOnClickListener {
            finish()
        }

        btnToCommdityList_3D.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("showNo", getShowNo)
            bundle.putString("showName", getShowName)
            val intent = Intent(this, Commodity::class.java)
            intent.putExtra("bundle", bundle)
            this.startActivity(intent)
        }
    }
}