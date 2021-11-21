package com.example.test2.data

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.activity.home.Commodity
import com.example.test2.adapter.CommodityListForCommodityAdapter
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.CartAdd
import com.example.test2.data.model.GoodResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Goods {
    fun postCommodity(
        showNo: String,
        items: ArrayList<MutableMap<String, Any?>>,
        commList: RecyclerView,
        msgErrorCommodity: TextView,
        applicationContext: Context,
        commodity: Commodity
    ) {
        val jsonObject = JSONObject()
        val requestBody =jsonObject.put("eNo", showNo).toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appAllGoods(requestBody).enqueue(object: Callback<GoodResponse> {
            override fun onFailure(call: Call<GoodResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "請確認網路連線正常與否！", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<GoodResponse>,
                response: Response<GoodResponse>
            ) {
                val status = response.body()?.status.toString()
                val data = ArrayList(response.body()?.data)
                if(status == "success"){
                    // 將data裝進HashMap中
                    for(i in data.indices){
                        val item = mutableMapOf<String, Any?>()

                        item["goodNo"] = data[i].gNo
                        item["goodName"] = data[i].gName
                        item["goodText"] = data[i].gIntrodution
                        item["goodPrice"] = data[i].gPrice
                        item["goodAmount"] = data[i].gAmount
                        if(data[i].gImage == null){
                            item["goodImg"] = "null.jpg"
                        }else{
                            item["goodImg"] = data[i].gImage
                        }
                        item["myCartAmount"] = 0
                        items.add(item)
                    }
                    val layoutManager = GridLayoutManager(commodity, 2)
                    commList.layoutManager = layoutManager
                    commList.adapter = CommodityListForCommodityAdapter(items)
                }else{
                    msgErrorCommodity.visibility = View.VISIBLE
                }
            }
        })
    }

    fun postAddGood(memNo: String, goodNo: String, amount: Int, context: Context) {
        ////////// POST //////////
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("gNo", goodNo)
            .put("gAmount", amount)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳GoodResponse回來
        RetrofitClient.instance.appAddShopCart(requestBody).enqueue(object: Callback<CartAdd> {
            override fun onFailure(call: Call<CartAdd>, t: Throwable) {
                Toast.makeText(context, "請確認網路連線正常與否！", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<CartAdd>,
                response: Response<CartAdd>
            ) {
                when(response.body()?.status.toString()){
                    "add success" -> Toast.makeText(context, "新增成功!", Toast.LENGTH_SHORT).show()
                    "update success" -> Toast.makeText(context, "更新成功!", Toast.LENGTH_SHORT).show()
                    "add fail" -> Toast.makeText(context, "新增失敗!", Toast.LENGTH_SHORT).show()
                    "update fail" -> Toast.makeText(context, "更新失敗!", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(context, "添加至購物車失敗，請稍後再試!", Toast.LENGTH_SHORT).show()
                }
            }
        })
        //////////////////////////////
    }
}
