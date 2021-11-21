package com.example.test2.data

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.adapter.CartListForShoppingCartAdapter
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.CartAdd
import com.example.test2.data.model.CartDelete
import com.example.test2.data.model.CartResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShoppingCart {
    var items = ArrayList<MutableMap<String, Any?>>()
    fun postMyCart(
        memNo: String?,
        msgError: TextView,
        applicationList: RecyclerView,
        btnCheckout: Button,
        mActivity: Activity?
    ) {

        var totPrice = 0
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appAllShoppingcart(requestBody).enqueue(object: Callback<CartResponse> {
            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                msgError.text = "請確認網路連線正常與否！"
                msgError.visibility = View.VISIBLE
                applicationList.visibility = View.INVISIBLE
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<CartResponse>,
                response: Response<CartResponse>
            ) {
                val status = response.body()?.status.toString()
                val data = ArrayList(response.body()?.data)

                if(status == "success"){
                    for(i in data.indices){
                        val item = mutableMapOf<String, Any?>()
                        item["cartNo"] = data[i].scNo
                        item["cartGoodNo"] = data[i].gNo
                        item["cartGoodName"] = data[i].gName
                        item["cartGoodPrice"] = data[i].gPrice
                        item["cartGoodAmount"] = data[i].gAmount
                        item["cartGoodStock"] = data[i].gStock
                        if(data[i].gImage == null){
                            item["cartGoodImg"] = "null.jpg"
                        }else{
                            item["cartGoodImg"] = data[i].gImage
                        }
                        items.add(item)
                    }
                    if(items.isEmpty()){
                        msgError.text = "目前購物車尚無商品\n快參觀展覽找到有興趣的商品吧！"
                        msgError.visibility = View.VISIBLE
                        applicationList.visibility = View.INVISIBLE
                    }

                    val layoutManager = LinearLayoutManager(mActivity)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL

                    applicationList.layoutManager = layoutManager
                    applicationList.adapter = memNo?.let { CartListForShoppingCartAdapter(it, items, msgError, applicationList, btnCheckout, mActivity, totPrice) }
                }else{
                    msgError.visibility = View.VISIBLE
                }
            }
        })
    }

    fun postUpdateCart(
        memNo: String,
        cartGoodNo: String,
        updateAmount: Int,
        toto: Context?,
        cartGoodAmount: TextView
    ) {
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("gNo", cartGoodNo)
            .put("gAmount", updateAmount)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appAddShopCart(requestBody).enqueue(object: Callback<CartAdd> {

            override fun onFailure(call: Call<CartAdd>, t: Throwable) {
                Toast.makeText(toto, "Oops，出了點問題，請稍後再試！", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<CartAdd>,
                response: Response<CartAdd>
            ) {
                val status = response.body()?.status.toString()
                val gAmount = response.body()?.gAmount

                when(status){
                    "update success" -> gAmount?.let { showResult(it) }
                    else -> Toast.makeText(toto, "更新失敗，請稍後再試!", Toast.LENGTH_SHORT).show()
                }
            }

            fun showResult(result: Int){
                cartGoodAmount.text = result.toString()
            }
        })
    }

    fun postDeleteCart(
        memNo: String,
        cartGoodNo: String,
        toto: Context?,
        msgError: TextView,
        applicationList: RecyclerView,
        btnCheckout: Button,
        mActivity: Activity?
    ) {
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("gNo", cartGoodNo)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appDeleteS(requestBody).enqueue(object: Callback<CartDelete> {

            override fun onFailure(call: Call<CartDelete>, t: Throwable) {
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<CartDelete>,
                response: Response<CartDelete>
            ) {
                val status = response.body()?.status.toString()

                when(status){
                    "success" -> {
                        Toast.makeText(toto, "success", Toast.LENGTH_SHORT).show()
                        ShoppingCart().postMyCart(
                            memNo,
                            msgError,
                            applicationList,
                            btnCheckout,
                            mActivity
                        )
                    }
                    else -> Toast.makeText(toto, "更新失敗，請稍後再試!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}