package com.example.test2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.CartAdd
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_commodity_detail.*
import kotlinx.android.synthetic.main.activity_exhibition_detail.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.typeOf

class CommodityDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity_detail)

        // 隱藏TitleBar
        supportActionBar?.hide()

        // 接收home的資料
        var getGoodNo = intent.getBundleExtra("bundle")?.getString("goodNo")
        var getGoodName = intent.getBundleExtra("bundle")?.getString("goodName")
        var getGoodPrice = intent.getBundleExtra("bundle")?.getString("goodPrice")
        var getGoodText = intent.getBundleExtra("bundle")?.getString("goodText")
        var getGoodAmount = intent.getBundleExtra("bundle")?.getString("goodAmount")?.toInt()!!
        var getGoodMyCartAmount = intent.getBundleExtra("bundle")?.getString("goodMyCartAmount")?.toInt()!!
        var getGoodImgPath = intent.getBundleExtra("bundle")?.getString("goodImgPath")

        goodDetailName.text = getGoodName
        goodDetailText.text = getGoodText
        goodDetailPrice.text = getGoodPrice
        Picasso.get().load("http://140.131.114.155/file/$getGoodImgPath").into(goodDetailImg)


        btnToBackCommoidty.setOnClickListener {
            finish()
        }

        var amount = 0
        var goodAmount = getGoodAmount

        btnAdd.setOnClickListener {
            amount += 1
            txtAmount.text = amount.toString()
        }
        btnSub.setOnClickListener {
            amount -= 1
            txtAmount.text = amount.toString()
        }

        btnAddGood.setOnClickListener {
            if(goodAmount >= (amount + getGoodMyCartAmount)){ // 目前要新增的數量+已加入購物車數量
                if (getGoodNo != null) {
                    postAddGood(getGoodNo, amount, it.context)
                    getGoodMyCartAmount += amount
                }
            }else{
                Toast.makeText(this, "數量超過!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun postAddGood(goodNo: String, amount: Int, context: Context) {
        ////////// POST //////////
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", "a22753516@gmail.com")
            .put("gNo", goodNo)
            .put("gAmount", amount)
            .toString().toRequestBody("application/json".toMediaTypeOrNull()) // TODO

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳GoodResponse回來
        RetrofitClient.instance.appAddS(requestBody).enqueue(object: Callback<CartAdd> {
            override fun onFailure(call: Call<CartAdd>, t: Throwable) {
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<CartAdd>,
                response: Response<CartAdd>
            ) {
                var status = response.body()?.status.toString()

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