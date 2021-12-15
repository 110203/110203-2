package com.example.test2.activity.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test2.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_exhibition_detail.*

class ExhibitionDetail : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exhibition_detail)
        supportActionBar?.hide()

        val getShowNo = intent.getBundleExtra("bundle")?.getString("showNo")
        val getShowName = intent.getBundleExtra("bundle")?.getString("showName")
        val getShowImgPath = intent.getBundleExtra("bundle")?.getString("showImgPath")
        val getShowText = intent.getBundleExtra("bundle")?.getString("showText")
        val getShowStartTime = intent.getBundleExtra("bundle")?.getString("showStartTime")
        val getShowEndTime = intent.getBundleExtra("bundle")?.getString("showEndTime")
        val getEUrl2D = intent.getBundleExtra("bundle")?.getString("eUrl2D")
        val getEStyle = intent.getBundleExtra("bundle")?.getString("eStyle")

        exhibitionDetailName.text = getShowName
        exhibitionDetailText.text = getShowText
        exhibitionTime.text = "$getShowStartTime 至 $getShowEndTime"
        val imgUrl: String = this.getString(R.string.BASE_IMG_URL)
        Picasso.get().load(imgUrl + getShowImgPath).into(exhibitionDetailImg)

        when(getEStyle){
            "2D" -> {
                btn3D.isEnabled = false
                btn3D.isClickable = false
            }
            "3D" -> {
                btn2D.isEnabled = false
                btn2D.isClickable = false
            }
            "2D, 3D" -> {}
            else -> {
                btn2D.isEnabled = false
                btn2D.isClickable = false
                btn3D.isEnabled = false
                btn3D.isClickable = false
            }
        }
        if (getEUrl2D == "not built") {
            btn2D.isEnabled = false
            btn2D.isClickable = false
        }

        btn2D.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("showNo", getShowNo)
            bundle.putString("showName", getShowName)
            bundle.putString("eUrl2D", getEUrl2D)
            val intent = Intent(this, Exhibition2D::class.java)
            intent.putExtra("bundle", bundle)
            startActivity(intent)
        }

        btn3D.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("showNo", getShowNo)
            bundle.putString("showName", getShowName)
            val intent = Intent(this, Exhibition3D::class.java)
            intent.putExtra("bundle", bundle)
            startActivity(intent)
        }

        btnToBackHome.setOnClickListener {
            finish()
        }
    }
}