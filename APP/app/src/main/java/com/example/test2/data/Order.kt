package com.example.test2.data

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.activity.cart.CartCheckout
import com.example.test2.activity.cart.CartCheckoutSuccess
import com.example.test2.activity.profile.OrderQuery
import com.example.test2.adapter.OrderListForOrderQueryAdapter
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.OrderAdd
import com.example.test2.data.model.OrderDetailAdd
import com.example.test2.data.model.OrderResponse
import kotlinx.android.synthetic.main.activity_commodity.*
import kotlinx.android.synthetic.main.activity_order_query.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Order {
    fun postAddOrder(
        cartCheckout: CartCheckout,
        memNo: String,
        address: String,
        tel: String,
        payment: String,
        items: ArrayList<Map<String, Any?>>,
        orderTotPrice: Int
    ) {
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("address", address)
            .put("tel", tel)
            .put("payment", payment)
            .put("totPrice", orderTotPrice)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appAddOrderRecord(requestBody).enqueue(object: Callback<OrderAdd> {
            override fun onFailure(call: Call<OrderAdd>, t: Throwable) {
                Toast.makeText(cartCheckout, "Oops，出了點問題，請稍後再試！", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<OrderAdd>,
                response: Response<OrderAdd>
            ) {
                val status = response.body()?.status.toString()
                val orNo = response.body()?.orNo.toString()

                when(status){
                    "success" -> {
                        cartCheckout.finish()
                        postAddOrderDetail(orNo, items, cartCheckout, memNo)
                    }
                }
            }
        })
    }

    fun postAddOrderDetail(
        orNo: String,
        items: ArrayList<Map<String, Any?>>,
        cartCheckout: CartCheckout,
        memNo: String
    ) {
        val allPostStatus = ArrayList<Any>()
        for(i in 0 until items.size){
            val test = items[i]["data$i"].toString()
            val test2 = test.split(", ")
            val cartGoodNo = test2[1].split("=")[1]
            val cartGoodAmount = test2[4].split("=")[1]

            val jsonObject = JSONObject()
            val requestBody = jsonObject.put("orNo", orNo)
                .put("gNo", cartGoodNo)
                .put("gAmount", cartGoodAmount)
                .put("memNo", memNo)
                .toString().toRequestBody("application/json".toMediaTypeOrNull())

            RetrofitClient.instance.appAddOrderDetail(requestBody).enqueue(object: Callback<OrderDetailAdd> {
                override fun onFailure(call: Call<OrderDetailAdd>, t: Throwable) {
                    Toast.makeText(cartCheckout, "Oops，出了點問題，請確認網路連線，並稍後再試！", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<OrderDetailAdd>,
                    response: Response<OrderDetailAdd>
                ) {
                    when(response.body()?.status.toString()){
                        "success" -> {
                            allPostStatus.add("success")
                        }
                        else -> {
                            Toast.makeText(cartCheckout, "Oops，出了點問題，請與我們聯繫！", Toast.LENGTH_LONG).show()
                        }
                    }

                    if(i==items.size-1){
                        checkAllSuccess(allPostStatus, items.size, cartCheckout, memNo)
                    }
                }
            })
        }
    }

    fun checkAllSuccess(
        allPostStatus: ArrayList<Any>,
        count: Int,
        cartCheckout: CartCheckout,
        memNo: String
    ) {
        if(allPostStatus.size == count){
            val bundle = Bundle()
            bundle.putString("memNo", memNo)
            val intent = Intent(cartCheckout, CartCheckoutSuccess::class.java)
            intent.putExtra("bundle", bundle)
            cartCheckout.startActivity(intent)
        }
    }

    fun postOrderRecord(
        memNo: String,
        orderList: RecyclerView,
        msgNotFoundOrder: TextView,
        orderQuery: OrderQuery
    ) {
        val items = ArrayList<MutableMap<String, Any?>>()
        val jsonObject = JSONObject()
        val requestBody =jsonObject.put("memNo", memNo).toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appOrderRecord(requestBody).enqueue(object:
            Callback<OrderResponse> {
            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Toast.makeText(orderQuery, "請確認網路連線正常與否", Toast.LENGTH_LONG).show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<OrderResponse>,
                response: Response<OrderResponse>
            ) {
                val status = response.body()?.status.toString()
                val data = ArrayList(response.body()?.data)
                if(status == "success"){
                    // 將data裝進HashMap中
                    for(i in data.indices){
                        val item = mutableMapOf<String, Any?>()
                        item["orNo"] = data[i].orNo
                        item["orTime"] = data[i].orTime
                        item["orState"] = data[i].orState
                        item["orTotalPrice"] = data[i].orTotalPrice
                        item["orImg"] = data[i].gImage
                        items.add(item)
                    }
                    val layoutManager = LinearLayoutManager(orderQuery)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    orderList.layoutManager = layoutManager
                    orderList.adapter = OrderListForOrderQueryAdapter(items)
                }
                if(items.toString() == ""){
                    msgNotFoundOrder.visibility = View.VISIBLE
                }
            }
        })
    }
}
