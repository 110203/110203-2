package com.example.test2.activity.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.test2.R
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.CartAdd
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_commodity_detail.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommodityDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity_detail)
        supportActionBar?.hide()

        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        val memNo : String? = sharedPref.getString("memNo", "")

        // 接收home的資料
        val getGoodNo = intent.getBundleExtra("bundle")?.getString("goodNo")
        val getGoodName = intent.getBundleExtra("bundle")?.getString("goodName")
        val getGoodPrice = intent.getBundleExtra("bundle")?.getString("goodPrice")
        val getGoodText = intent.getBundleExtra("bundle")?.getString("goodText")
        val getGoodAmount = intent.getBundleExtra("bundle")?.getString("goodAmount")?.toInt()!!
        var getGoodMyCartAmount = intent.getBundleExtra("bundle")?.getString("goodMyCartAmount")?.toInt()!!
        val getGoodImgPath = intent.getBundleExtra("bundle")?.getString("goodImgPath")

        goodDetailName.text = getGoodName
        goodDetailText.text = getGoodText
        goodDetailPrice.text = getGoodPrice
        Picasso.get().load("http://140.131.114.155/file/$getGoodImgPath").into(goodDetailImg)


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
            // getGoodAmount 商品a庫存
            // amount 預加數量
            // getGoodMyCartAmount 商品a目前在購物車的數量
            if (getGoodAmount >= (amount + getGoodMyCartAmount)) { // 目前要新增的數量+已加入購物車數量
                if (getGoodNo != null) {
                    if (memNo != null) {
                        postAddGood(memNo, getGoodNo, amount, it.context)
                    }
                    getGoodMyCartAmount += amount
                }
            } else {
                Toast.makeText(this, "數量超過!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun postAddGood(memNo: String, goodNo: String, amount: Int, context: Context) {
        ////////// POST //////////
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("gNo", goodNo)
            .put("gAmount", amount)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳GoodResponse回來
        RetrofitClient.instance.appAddS(requestBody).enqueue(object: Callback<CartAdd> {
            override fun onFailure(call: Call<CartAdd>, t: Throwable) {
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<CartAdd>,
                response: Response<CartAdd>
            ) {
                val status = response.body()?.status.toString()

                if(status == "success"){
                    Toast.makeText(context, "新增成功!", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context, "新增失敗!", Toast.LENGTH_LONG).show()
                }
            }
        })
        //////////////////////////////
    }
}