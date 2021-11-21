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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.R
import com.example.test2.adapter.OrderListForOrderQueryAdapter
import com.example.test2.cusFormatDateTime
import com.example.test2.data.Order
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.OrderResponse
import com.squareup.picasso.Picasso
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
    private var getMemNo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_query)
        supportActionBar?.hide()

        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        getMemNo = sharedPref?.getString("memNo", "").toString()
        Order().postOrderRecord(getMemNo, orderList, msgNotFoundOrder, this)

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
        RetrofitClient.instance.appOrderRecord(requestBody).enqueue(object:
            Callback<OrderResponse> {
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
                        item["orTotalPrice"] = data[i].orTotalPrice
                        item["orImg"] = data[i].gImage
                        items.add(item)
                    }
                    val layoutManager = LinearLayoutManager(this@OrderQuery)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    orderList.layoutManager = layoutManager
                    orderList.adapter = OrderListForOrderQueryAdapter(items)
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
        val orTime = items[position]["orTime"].toString().replace(".000Z", "")
        val timeSplit = orTime.split("T")

        val orState = items[position]["orState"].toString()
        val orTotalPrice = items[position]["orTotalPrice"].toString()
        val photoPath = items[position]["orImg"].toString()
        holder.txtOrderId.text = "#$orNo"
        holder.txtOrderDate.text = cusFormatDateTime(timeSplit[0], timeSplit[1])
        val imgUrl: String? = holder.toto?.getString(R.string.BASE_IMG_URL)
        Picasso.get().load(imgUrl + photoPath).into(holder.imgOrder)

        when(orState){
            "0" -> holder.txtOrderStatus.text = "訂單確認"
            "1" -> holder.txtOrderStatus.text = "準備出貨中"
            "2" -> holder.txtOrderStatus.text = "出貨"
            else -> holder.txtOrderStatus.text = "商品已送達"
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("orNo", orNo)
            bundle.putString("orState", orState)
            bundle.putString("orTotalPrice", orTotalPrice)
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
    val imgOrder : ImageView = v.findViewById(R.id.imgOrder)

    val toto: Context? = v.context
}