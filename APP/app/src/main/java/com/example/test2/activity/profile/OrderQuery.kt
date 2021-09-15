package com.example.test2.activity.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.R
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.OrderResponse
import kotlinx.android.synthetic.main.activity_commodity.*
import kotlinx.android.synthetic.main.activity_order_query.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderQuery : AppCompatActivity() {
    var items = ArrayList<MutableMap<String, Any?>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_query)
        supportActionBar?.hide()

        // 接收dashboard的資料
        val getMemNo: String = intent.getBundleExtra("bundle")?.getString("memNo").toString()
        postOrderRecord(getMemNo)

        ///// BUTTON /////
        // back
        btnToBackProfileOrderQuery.setOnClickListener {
            finish()
        }
        //////////////////
    }

    private fun postOrderRecord(memNo: String) {
        ////////// POST //////////
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val jsonObject = JSONObject()
        val requestBody =jsonObject.put("memNo", memNo).toString().toRequestBody("application/json".toMediaTypeOrNull())

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳GoodResponse回來
        RetrofitClient.instance.appOrderRecord(requestBody).enqueue(object: Callback<OrderResponse> {
            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                t.message?.let { Log.d("ERROR", it) }
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
                        items.add(item)
                    }
                    val layoutManager = LinearLayoutManager(this@OrderQuery)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    orderList.layoutManager = layoutManager
                    orderList.adapter = OrderListAdapter(items)
                }else{
                    textView7.text = "NOT FOUND."
                }
                if(items.toString() == ""){
                    msgNotFoundOrder.visibility = View.VISIBLE
                }
            }

        })
        //////////////////////////////
    }
}

class OrderListAdapter(private val items: ArrayList<MutableMap<String, Any?>>) : RecyclerView.Adapter<OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_order_item, parent, false)
        return OrderViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val orNo = items[position]["orNo"].toString()
        val orTime = items[position]["orTime"].toString()
        val orState = items[position]["orState"].toString()
        holder.txtOrderId.text = "#$orNo"
        holder.txtOrderDate.text = orTime
        if(orState=="0"){
            holder.txtOrderStatus.text = "訂單確認"
        }else if(orState=="1"){
            holder.txtOrderStatus.text = "準備出貨中"
        }else if(orState=="2"){
            holder.txtOrderStatus.text = "出貨"
        }else{
            holder.txtOrderStatus.text = "商品已送達"
        }


        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("orNo", orNo)
            bundle.putString("orState", orState)
            val intent = Intent(holder.toto, OrderDetail::class.java)
            intent.putExtra("bundle", bundle)
            holder.toto?.startActivity(intent)
        }
    }

}

class OrderViewHolder(v: View) : RecyclerView.ViewHolder(v){
    val txtOrderId : TextView = v.findViewById(R.id.txtOrderId)
    val txtOrderDate : TextView = v.findViewById(R.id.txtOrderDate)
    val txtOrderStatus : TextView = v.findViewById(R.id.txtOrderStatus)

    val toto: Context? = v.context
}
