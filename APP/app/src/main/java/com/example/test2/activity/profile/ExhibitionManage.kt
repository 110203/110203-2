package com.example.test2.activity.profile

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.R
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.ExhibitionResponseByMemNo
import com.example.test2.data.model.ExhibitionResponseByPin
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_commodity.*
import kotlinx.android.synthetic.main.activity_exhibition_manage.*
import kotlinx.android.synthetic.main.fragment_home.*
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

        // 接收dashboard的資料
        val getMemNo: String = intent.getBundleExtra("bundle")?.getString("memNo").toString()


        postMemExhibition(getMemNo)

        btnPINClose.setOnClickListener {
            showInputPIN.visibility = View.INVISIBLE
        }

        btnPIN.setOnClickListener {
            val pin = txtPIN.text.toString()
            postLoginExhibition(getMemNo, pin)
        }

        btnToBackProfileExhibitionManage.setOnClickListener {
            finish()
        }
    }

    private fun postMemExhibition(memNo : String) {
        ////////// POST //////////
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("memNo", memNo)

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳GoodResponse回來
        RetrofitClient.instance.appMemExhibitionChecked(requestBody).enqueue(object: Callback<ExhibitionResponseByMemNo> {
            override fun onFailure(call: Call<ExhibitionResponseByMemNo>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<ExhibitionResponseByMemNo>,
                response: Response<ExhibitionResponseByMemNo>
            ) {
                val status = response.body()?.status.toString()

                if(status == "success"){
                    val data = ArrayList(response.body()?.data)
                    // 將data裝進HashMap中
                    for(i in data.indices){
                        val item = HashMap<String, Any?>()

                        item["exhibitionNo"] = data[i].eNo
                        item["exhibitionName"] = data[i].eName
                        item["exhibitionText"] = data[i].eIntrodution
                        item["exhibitionType"] = data[i].eType
                        item["exhibitionStartTime"] = data[i].startTime
                        item["exhibitionEndTime"] = data[i].endTime

                        if (data[i].eImage == null) {
                            item["exhibitionImg"] = "null.jpg"
                        } else {
                            item["exhibitionImg"] = data[i].eImage
                        }
                        items.add(item)
                    }
                }else{
                    txtNotFoundExhibitionMan.text = "查無資料"
                    txtNotFoundExhibitionMan.visibility = View.VISIBLE
                }

                // recycler
                var footerItem = HashMap<String, Any?>()
                footerItem["exhibitionNo"] = "footer"
                items.add(footerItem)
                var layoutManager = GridLayoutManager(this@ExhibitionManage, 2)
                exhibitionList.layoutManager = layoutManager
                exhibitionList.adapter = ExhibitionListAdapter(items, showInputPIN)
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
        RetrofitClient.instance.appLoginExhibition(requestBody).enqueue(object: Callback<ExhibitionResponseByPin> {
            override fun onFailure(call: Call<ExhibitionResponseByPin>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                t.message?.let { Log.d("ERROR", it) }
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ExhibitionResponseByPin>,
                response: Response<ExhibitionResponseByPin>
            ) {
                var status = response.body()?.status
                val data = response.body()?.data
                if(status == "success"){
                    if(data == 0){
                        txtMsg.text = "＊請確認此PIN碼是否已輸入過＊"
                    }else{
                        txtMsg.text = "新增成功！"
                        txtPIN.setText("")
                    }
                }else{
                    txtMsg.text = "＊請確認PIN碼是否已輸入正確，建議直接複製貼上＊"
                }
            }

        })
        //////////////////////////////
    }
}

class ExhibitionListAdapter(private val items: ArrayList<Map<String, Any?>>, private val showInputPIN: ConstraintLayout) : RecyclerView.Adapter<ExhibitionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExhibitionViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_exhibition_home, parent, false)
        return ExhibitionViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ExhibitionViewHolder, position: Int) {
        val exhibitionNo = items[position]["exhibitionNo"].toString()
        val exhibitionName = items[position]["exhibitionName"].toString()
        val exhibitionText = items[position]["exhibitionText"].toString()
        val photoPath = items[position]["exhibitionImg"].toString()

        if(exhibitionNo === "footer"){
            holder.inputPinLayout.visibility = View.VISIBLE
            holder.exhibitionLayout.visibility = View.INVISIBLE
        }else{
            holder.inputPinLayout.visibility = View.INVISIBLE
            holder.exhibitionLayout.visibility = View.VISIBLE

            holder.exhibitionName.text = exhibitionName
            holder.exhibitionText.text = exhibitionText
            Picasso.get().load("http://140.131.114.155/file/$photoPath").into(holder.exhibitionImg)
        }

        holder.pinImg.setOnClickListener {
            this.showInputPIN.visibility = View.VISIBLE
        }
        holder.itemView.setOnClickListener {

        }
        holder.exhibitionImg.clipToOutline = true
    }

}

class ExhibitionViewHolder(v: View) : RecyclerView.ViewHolder(v){
    val exhibitionName : TextView = v.findViewById(R.id.showName)
    val exhibitionText : TextView = v.findViewById(R.id.showText)
    val exhibitionImg : ImageView = v.findViewById(R.id.showImg)
    val exhibitionLayout : ConstraintLayout = v.findViewById(R.id.showLayout)
    val inputPinLayout : ConstraintLayout = v.findViewById(R.id.input_pin_footer)
    val pinImg : ImageView = v.findViewById(R.id.pinImg)
}