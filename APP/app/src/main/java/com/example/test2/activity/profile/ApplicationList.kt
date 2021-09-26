package com.example.test2.activity.profile

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.R
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.ExhibitionResponseByMemNo
import kotlinx.android.synthetic.main.activity_application_list.*
import kotlinx.android.synthetic.main.activity_exhibition_2_d.*
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

        // 接收dashboard的資料
        val getMemNo: String = intent.getBundleExtra("bundle")?.getString("memNo").toString()

        postApplicationList(getMemNo)

        ///// BUTTON /////
        // 上傳
        btnOpenForm.setOnClickListener {
            val uri: Uri = Uri.parse("https://reurl.cc/8351xM")
            val i = Intent(Intent.ACTION_VIEW, uri)
            startActivity(i)
        }

        // back
        btnToBackProfileApplyList.setOnClickListener {
            finish()
        }
        //////////////////
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
            Callback<ExhibitionResponseByMemNo> {
            override fun onFailure(call: Call<ExhibitionResponseByMemNo>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<ExhibitionResponseByMemNo>,
                response: Response<ExhibitionResponseByMemNo>
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
                        item["exhibitionCheck"] = data?.get(i).eCheck
                        item["exhibitionLogin"] = data?.get(i).eLogin
                        item["exhibitionStyle"] = data?.get(i).eStyle

                        if (data?.get(i).eImage == null) {
                            item["exhibitionImg"] = "null.jpg"
                        } else {
                            item["exhibitionImg"] = data?.get(i).eImage
                        }
                        items.add(item)
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

        // dialogDetail //
        val dialogDetail = holder.dialogDetail
        dialogDetail.setContentView(R.layout.layout_application_detail)

        val applyNo: TextView = dialogDetail.findViewById(R.id.txtApplyNo)
        val applyName: TextView = dialogDetail.findViewById(R.id.txtApplyName)
        val applyType: TextView = dialogDetail.findViewById(R.id.txtApplyType)
        val applyStartTime: TextView = dialogDetail.findViewById(R.id.txtApplyStartTime)
        val applyEndTime: TextView = dialogDetail.findViewById(R.id.txtApplyEndTime)
        val applyText: TextView = dialogDetail.findViewById(R.id.txtApplyText)
        val applyChk2D: CheckBox = dialogDetail.findViewById(R.id.chkApply2D_)
        val applyChk3D: CheckBox = dialogDetail.findViewById(R.id.chkApply3D_)

        applyNo.text = applyData[position]["exhibitionNo"].toString()
        applyName.text = applyData[position]["exhibitionName"].toString()
        applyType.text = applyData[position]["exhibitionType"].toString()
        applyStartTime.text = applyData[position]["exhibitionStartTime"].toString()
        applyEndTime.text = applyData[position]["exhibitionEndTime"].toString()
        applyText.text = applyData[position]["exhibitionText"].toString()
        when(applyData[position]["exhibitionStyle"].toString()){
            "2D" -> applyChk2D.isChecked = true
            "3D" -> applyChk3D.isChecked = true
            "2D, 3D" -> {
                applyChk2D.isChecked = true
                applyChk3D.isChecked = true
            }
        }
        //////////////////

        // dialogStatus //
        val dialogStatus = holder.dialogStatus
        dialogStatus.setContentView(R.layout.layout_application_detail_status)

        val applyNoStatus: TextView = dialogStatus.findViewById(R.id.txtApplyNo_)
        val msgCheck: TextView = dialogStatus.findViewById(R.id.msgCheck)
        val msgBuild: TextView = dialogStatus.findViewById(R.id.msgBuild)
        val msgWait: TextView = dialogStatus.findViewById(R.id.msgWait)
        val msgStart: TextView = dialogStatus.findViewById(R.id.msgStart)
        val msgFinish: TextView = dialogStatus.findViewById(R.id.msgFinish)

        applyNoStatus.text = applyData[position]["exhibitionNo"].toString()
        if(applyData[position]["exhibitionCheck"] == 0){
            msgBuild.visibility = View.INVISIBLE
            msgWait.visibility = View.INVISIBLE
            msgStart.visibility = View.INVISIBLE
            msgFinish.visibility = View.INVISIBLE
        }
        //////////////////

        // BUTTON //
        val btnStatus: ImageButton = dialogDetail.findViewById(R.id.btnApplyListStatus)
        val btnApplyList: ImageButton = dialogStatus.findViewById(R.id.btnApplyList)

        holder.itemView.setOnClickListener {
            dialogDetail.show()

        }

        btnStatus.setOnClickListener {
            dialogStatus.show()
            dialogDetail.dismiss()
        }

        btnApplyList.setOnClickListener {
            dialogStatus.dismiss()
            dialogDetail.show()
        }
        ////////////
    }

}

class ApplyListHolder(v: View) : RecyclerView.ViewHolder(v){
    val dataView : TextView = v.findViewById(R.id.exhibitionItem)

    val dialogDetail = Dialog(v.context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
    val dialogStatus = Dialog(v.context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)

}