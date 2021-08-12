package com.example.test2.activity.home

import android.content.Context
import android.content.Intent
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
import com.example.test2.CommodityDetail
import com.example.test2.MainActivity
import com.example.test2.R
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.CartAdd
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity)
        // 隱藏TitleBar
        supportActionBar?.hide()

        // 接收Exhibition_2D的資料
        var getShowNo = intent.getBundleExtra("bundle")?.getString("showNo").toString()
        var getShowName = intent.getBundleExtra("bundle")?.getString("showName")
        textView7.text = "$getShowName 商品"

        postCommodity(getShowNo)

        // 返回展覽
        btnToBack2D.setOnClickListener {
            finish()
        }

        // 離開展覽 > 回到首頁 //有ERROR TODO
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
                var status = response.body()?.status.toString()
                val data = ArrayList(response.body()?.data)
                if(status == "success"){
                    // 將data裝進HashMap中
                    for(i in data?.indices){
                        var item = mutableMapOf<String, Any?>()

                        item["goodNo"] = data?.get(i).gNo
                        item["goodName"] = data?.get(i).gName
                        item["goodText"] = data?.get(i).gIntrodution
                        item["goodPrice"] = data?.get(i).gPrice
                        item["goodAmount"] = data?.get(i).gAmount
                        if(data?.get(i).gImage == null){
                            item["goodImg"] = "null.jpg"
                        }else{
                            item["goodImg"] = data?.get(i).gImage
                        }

                        item["myCartAmount"] = 0
                        items.add(item)
                        Log.d("itemssssss", items.toString())
                    }
                    var layoutManager = GridLayoutManager(this@Commodity, 2)
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


class CommodityListAdapter(val items: ArrayList<MutableMap<String, Any?>>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // recyclerView
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_commodity_home, parent, false)

//        // dialog
//        val myDialog = Dialog(parent.context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
//        myDialog.setContentView(R.layout.activity_commodity_detail)
//
//        v.setOnClickListener {
//            myDialog.show()
//        }

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        var goodNo = items[position]["goodNo"].toString()
        var goodName = items[position]["goodName"].toString()
        var goodPrice = items[position]["goodPrice"].toString()
        var goodText = items[position]["goodText"].toString()
        var goodAmount = items[position]["goodAmount"].toString()
        var goodMyCartAmount = items[position]["myCartAmount"].toString()
        var photoPath = items[position]["goodImg"].toString()

        // recyclerView
        holder.goodName.text = goodName
        holder.goodPrice.text = "$" + goodPrice
        Picasso.get().load("http://140.131.114.155/file/$photoPath").into(holder.goodImg)

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

//        // dialog
//        holder.goodDetailName.text = items[position]["goodName"].toString()
//        holder.goodDetailText.text = items[position]["goodText"].toString()
//        holder.goodDetailPrice.text = items[position]["goodPrice"].toString()
//        Picasso.get().load("http://140.131.114.155/file/$photoPath").into(holder.goodDetailImg)
//
//        var amount = 0
//        var goodAmount = items[position]["goodAmount"] as Int
//        holder.btnAdd.setOnClickListener {
//            amount += 1
//            holder.txtAmount.text = amount.toString()
//        }
//        holder.btnSub.setOnClickListener {
//            amount -= 1
//            holder.txtAmount.text = amount.toString()
//        }
//
//        holder.addGood.setOnClickListener {
//            if(goodAmount >= (amount + items[position]["myCartAmount"] as Int)){ // 目前要新增的數量+已加入購物車數量
//                items[position].put("myCartAmount", amount + items[position]["myCartAmount"] as Int)
//                postAddGood(position, it.getContext())
//            }else{
//                Log.d("cartttttttERROR", items[position]["myCartAmount"].toString())
//            }
//        }
    }

    private fun postAddGood(position: Int, context: Context) {
        ////////// POST //////////
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", "a22753516@gmail.com")
            .put("gNo", items[position]["goodNo"])
            .put("gAmount", items[position]["myCartAmount"])
            .toString().toRequestBody("application/json".toMediaTypeOrNull()) // TODO

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳CartAdd回來
        RetrofitClient.instance.appAddS(requestBody).enqueue(object: Callback<CartAdd>{
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

class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    // recyclerView
    val goodName: TextView = v.findViewById(R.id.goodName)
    val goodPrice: TextView = v.findViewById(R.id.goodPrice)
    val goodImg: ImageView = v.findViewById(R.id.goodImg)

    val toto: Context? = v.context

//    // dialog
//    val goodDetailName: TextView = myDialog.findViewById(R.id.exhibitionDetailText)
//    val goodDetailText: TextView = myDialog.findViewById(R.id.goodDetailText)
//    val goodDetailPrice: TextView = myDialog.findViewById(R.id.goodDetailPrice)
//    val goodDetailImg: ImageView = myDialog.findViewById(R.id.goodDetailImg)
//    val addGood: Button = myDialog.findViewById(R.id.btnAddGood)
//    val btnAdd: Button = myDialog.findViewById(R.id.btnAdd)
//    val btnSub: Button = myDialog.findViewById(R.id.btnSub)
//    val txtAmount: TextView = myDialog.findViewById(R.id.txtAmount)
}