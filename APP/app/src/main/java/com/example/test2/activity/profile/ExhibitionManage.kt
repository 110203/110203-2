package com.example.test2.activity.profile

import android.content.res.Resources
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
import com.example.test2.data.model.ExhibitionResponse_memNo
import com.example.test2.data.model.ExhibitionResponse_pin
import kotlinx.android.synthetic.main.activity_commodity.*
import kotlinx.android.synthetic.main.activity_exhibition_manage.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExhibitionManage : AppCompatActivity() {
    var items = ArrayList<Map<String, Any?>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exhibition_manage)
        supportActionBar?.hide()
        val userNo = "a22753516@gmail.com" // TODO

        postMemExhibition(userNo)

        btnInputPIN.setOnClickListener {
            showInputPIN.visibility = View.VISIBLE
        }

        btnPINClose.setOnClickListener {
            showInputPIN.visibility = View.INVISIBLE
        }

        btnPIN.setOnClickListener {
            var pin = txtPIN.text.toString()
            postLoginExhibition(userNo, pin)
        }
    }

    private fun postMemExhibition(userNo : String) {
        ////////// POST //////////
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("memNo", userNo)

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳GoodResponse回來
        RetrofitClient.instance.appMemExhibition(requestBody).enqueue(object: Callback<ExhibitionResponse_memNo> {
            override fun onFailure(call: Call<ExhibitionResponse_memNo>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<ExhibitionResponse_memNo>,
                response: Response<ExhibitionResponse_memNo>
            ) {
                var status = response.body()?.status.toString()
                val data = ArrayList(response.body()?.data)
                if(status == "success"){
                    // 將data裝進HashMap中
                    for(i in data?.indices){
                        var item = HashMap<String, Any?>()

                        item["exhibitionNo"] = data?.get(i).eNo
                        item["exhibitionName"] = data?.get(i).eName
                        item["exhibitionText"] = data?.get(i).eIntrodution
                        item["exhibitionType"] = data?.get(i).eType
                        item["exhibitionStartTime"] = data?.get(i).startTime
                        item["exhibitionEndTime"] = data?.get(i).endTime

                        if (data?.get(i).eImage == null) {
                            item["exhibitionImg"] = "null.jpg"
                        } else {
                            item["exhibitionImg"] = data?.get(i).eImage
                        }

                        items.add(item)
                        Log.d("itemssssss", items.toString())
                    }

                    var layoutManager = LinearLayoutManager(this@ExhibitionManage)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    exhibitionList.layoutManager = layoutManager
                    exhibitionList.adapter = ExhibitionListAdapter(items)

                }else{
                    textView7.text = "NOT FOUND."
                }
            }

        })
        //////////////////////////////
    }

    private fun postLoginExhibition(userNo : String, pin : String) {
        ////////// POST //////////
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", userNo)
            .put("pin", pin)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        // 利用APIService中的appLoginExhibition, 將requestBody POST 至資料庫, 回傳ExhibitionResponse_pin回來
        RetrofitClient.instance.appLoginExhibition(requestBody).enqueue(object: Callback<ExhibitionResponse_pin> {
            override fun onFailure(call: Call<ExhibitionResponse_pin>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<ExhibitionResponse_pin>,
                response: Response<ExhibitionResponse_pin>
            ) {
                var status = response.body()?.status
                val data = response.body()?.data
                if(status == "success"){
                    if(data == 0){
                        txtMsg.text = "(1).請查核PIN碼是否正確 \n (2).請查看是否已新增過"
                    }else{
                        txtMsg.text = "新增成功！"
                    }
                }else{
                    Toast.makeText(applicationContext, "ERROR", Toast.LENGTH_LONG).show()
                }
            }

        })
        //////////////////////////////
    }
}

class ExhibitionListAdapter(val items: ArrayList<Map<String, Any?>>) : RecyclerView.Adapter<ExhibitionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExhibitionViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_exhibition_item, parent, false)
        return ExhibitionViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ExhibitionViewHolder, position: Int) {
        holder.itemView.setOnClickListener {  }
        holder.exhibitionName.text = items[position]["exhibitionName"].toString()
    }

}

class ExhibitionViewHolder(v: View) : RecyclerView.ViewHolder(v){
    val exhibitionName : TextView = v.findViewById(R.id.exhibitionItem)
}
