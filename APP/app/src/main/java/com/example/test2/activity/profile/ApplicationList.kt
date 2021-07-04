package com.example.test2.activity.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.R
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.ExhibitionResponse_memNo
import kotlinx.android.synthetic.main.activity_application_list.*
import kotlinx.android.synthetic.main.activity_exhibition_manage.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApplicationList : AppCompatActivity() {
    var items = ArrayList<Map<String, Any?>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_list)
        supportActionBar?.hide()

        val memNo = "a22753516@gmail.com" // TODO

        postApplicationList(memNo)

        // 上傳
        btnOpenForm.setOnClickListener {
            val uri: Uri = Uri.parse("https://reurl.cc/8351xM")
            val i = Intent(Intent.ACTION_VIEW, uri)
            startActivity(i)
        }
    }


    private fun postApplicationList(memNo : String) {
        ////////// POST //////////
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("memNo", memNo)

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳GoodResponse回來
        RetrofitClient.instance.appMemExhibition(requestBody).enqueue(object:
            Callback<ExhibitionResponse_memNo> {
            override fun onFailure(call: Call<ExhibitionResponse_memNo>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<ExhibitionResponse_memNo>,
                response: Response<ExhibitionResponse_memNo>
            ) {
                var status = response.body()?.status.toString()

                if(status == "success"){
                    val data = ArrayList(response.body()?.data)
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

                    var layoutManager = LinearLayoutManager(this@ApplicationList)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    applicationList.layoutManager = layoutManager
                    applicationList.adapter = ApplyListAdapter(items)

                }else{
                    txtNotFoundApplication.text = "查無資料"
                    txtNotFoundApplication.visibility = View.VISIBLE
                }
            }

        })
        //////////////////////////////
    }
}


class ApplyListAdapter(private val applyData: ArrayList<Map<String, Any?>>) : RecyclerView.Adapter<ApplyListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyListHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_exhibition_item, parent, false)
        return ApplyListHolder(v)
    }

    override fun getItemCount(): Int {
        return applyData.size
    }

    override fun onBindViewHolder(holder: ApplyListHolder, position: Int) {
        holder.dataView.text = applyData[position]["exhibitionNo"].toString() + "｜" + applyData[position]["exhibitionName"].toString()
        holder.layout_All.setOnClickListener {  }
    }

}

class ApplyListHolder(v: View) : RecyclerView.ViewHolder(v){
    val dataView : TextView = v.findViewById(R.id.exhibitionItem)
    val layout_All : ConstraintLayout = v.findViewById(R.id.layout_All)
}