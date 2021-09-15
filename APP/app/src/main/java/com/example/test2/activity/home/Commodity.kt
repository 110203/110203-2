package com.example.test2.activity.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.MainActivity
import com.example.test2.R
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.GoodResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_commodity.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Commodity : AppCompatActivity() {
    var items = ArrayList<MutableMap<String, Any?>>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity)
        // 隱藏TitleBar
        supportActionBar?.hide()

        // 接收Exhibition_2D的資料
        val getShowNo = intent.getBundleExtra("bundle")?.getString("showNo").toString()
        val getShowName = intent.getBundleExtra("bundle")?.getString("showName")
        textView7.text = "$getShowName 商品"

        postCommodity(getShowNo)

        // 返回展覽
        btnToBack2D.setOnClickListener {
            finish()
        }

        // 離開展覽 > 回到首頁
        btnToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

    }

    private fun postCommodity(showNo: String) {
        ////////// POST //////////
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val jsonObject = JSONObject()
        val requestBody =jsonObject.put("eNo", showNo).toString().toRequestBody("application/json".toMediaTypeOrNull())

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳GoodResponse回來
        RetrofitClient.instance.appAllGoods(requestBody).enqueue(object: Callback<GoodResponse>{
            override fun onFailure(call: Call<GoodResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                t.message?.let { Log.d("ERROR", it) }
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
                    val layoutManager = GridLayoutManager(this@Commodity, 2)
                    commList.layoutManager = layoutManager
                    commList.adapter = CommodityListAdapter(items)

                }else{
                    textView7.text = "NOT FOUND."
                }
            }

        })
        //////////////////////////////
    }

}


class CommodityListAdapter(private val items: ArrayList<MutableMap<String, Any?>>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_commodity_home, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val goodNo = items[position]["goodNo"].toString()
        val goodName = items[position]["goodName"].toString()
        val goodPrice = items[position]["goodPrice"].toString()
        val goodText = items[position]["goodText"].toString()
        val goodAmount = items[position]["goodAmount"].toString()
        val goodMyCartAmount = items[position]["myCartAmount"].toString()
        val photoPath = items[position]["goodImg"].toString()

        holder.goodName.text = goodName
        holder.goodPrice.text = "$$goodPrice"
        val imgUrl: String = Resources.getSystem().getString(R.string.BASE_IMG_URL)
        Picasso.get().load(imgUrl + photoPath).into(holder.goodImg)

        holder.goodImg.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("goodNo", goodNo)
            bundle.putString("goodName", goodName)
            bundle.putString("goodPrice", goodPrice)
            bundle.putString("goodText", goodText)
            bundle.putString("goodAmount", goodAmount)
            bundle.putString("goodMyCartAmount", goodMyCartAmount)
            bundle.putString("goodImgPath", photoPath)
            val intent = Intent(holder.toto, CommodityDetail::class.java)
            intent.putExtra("bundle", bundle)
            holder.toto?.startActivity(intent)
        }
    }
}

class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val goodName: TextView = v.findViewById(R.id.goodName)
    val goodPrice: TextView = v.findViewById(R.id.goodPrice)
    val goodImg: ImageView = v.findViewById(R.id.goodImg)

    val toto: Context? = v.context
}