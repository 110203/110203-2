package com.example.test2.activity.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.test2.R
import com.example.test2.data.Goods
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_commodity_detail.*

class CommodityDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity_detail)
        supportActionBar?.hide()

        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        val memNo : String? = sharedPref.getString("memNo", "")

        val getGoodNo = intent.getBundleExtra("bundle")?.getString("goodNo")
        val getGoodName = intent.getBundleExtra("bundle")?.getString("goodName")
        val getGoodPrice = intent.getBundleExtra("bundle")?.getString("goodPrice")
        val getGoodText = intent.getBundleExtra("bundle")?.getString("goodText")
        val getGoodAmount = intent.getBundleExtra("bundle")?.getString("goodAmount")
        val getGoodImgPath = intent.getBundleExtra("bundle")?.getString("goodImgPath")

        goodDetailName.text = getGoodName
        goodDetailText.text = getGoodText
        goodDetailPrice.text = getGoodPrice
        goodDetailAmount.text = getGoodAmount
        val imgUrl: String = this.getString(R.string.BASE_IMG_URL)
        Picasso.get().load(imgUrl + getGoodImgPath).into(goodDetailImg)

        btnToBackCommoidty.setOnClickListener {
            finish()
        }

        var amount = 0
        btnAdd.setOnClickListener {
            amount += 1
            txtAmount.text = amount.toString()
        }
        btnSub.setOnClickListener {
            amount -= 1
            txtAmount.text = amount.toString()
        }

        btnAddGood.setOnClickListener {
            if (memNo == ""){
                Toast.makeText(this, "請登入後再添加商品喔！", Toast.LENGTH_LONG).show()
            }else if(amount == 0){
                Toast.makeText(this, "請重新確認商品數量！", Toast.LENGTH_LONG).show()
            }else{
                if (memNo != null) {
                    if (getGoodNo != null) {
                        Goods().postAddGood(memNo, getGoodNo, amount, this)
                    }
                }
            }

        }
    }
}