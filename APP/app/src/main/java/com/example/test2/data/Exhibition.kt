package com.example.test2.data

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.R
import com.example.test2.activity.profile.Application
import com.example.test2.activity.profile.ApplicationList
import com.example.test2.activity.profile.ExhibitionManage
import com.example.test2.activity.profile.ExhibitionManageDetail
import com.example.test2.adapter.ApplyListAdapter
import com.example.test2.adapter.ExhibitionListForExhibitionManageAdapter
import com.example.test2.adapter.ExhibitionListForHomeAdapter
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.ExhibitionAdd
import com.example.test2.data.model.ExhibitionResponse
import com.example.test2.data.model.ExhibitionResponseByMemNo
import com.example.test2.data.model.ExhibitionResponseByPin
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Exhibition {
    fun postExhibition(
        exhibitionType: ArrayList<String>,
        exhibitionView: RecyclerView,
        activity: FragmentActivity?,
        spnType: Spinner,
        msgErrorHome: TextView
    ): ArrayList<Map<String, Any?>> {
        val items = ArrayList<Map<String, Any?>>()
        RetrofitClient.instance.appAllExhibition().enqueue(object: Callback<ExhibitionResponse> {
            override fun onFailure(call: Call<ExhibitionResponse>, t: Throwable) {
                msgErrorHome.text = "請確認網路連線正常與否！"
                msgErrorHome.visibility = View.VISIBLE
                exhibitionView.visibility = View.INVISIBLE
            }

            override fun onResponse(
                call: Call<ExhibitionResponse>,
                response: Response<ExhibitionResponse>
            ) {
                val status = response.body()?.status.toString()
                val data = ArrayList(response.body()?.data)
                val exhibitionTypeDB = arrayListOf<String>()

                if(status == "success"){
                    for(i in data.indices) {
                        val item = HashMap<String, Any?>()

                        item["exhibitionNo"] = data[i].eNo
                        item["exhibitionName"] = data[i].eName
                        item["exhibitionText"] = data[i].eIntrodution
                        item["exhibitionType"] = data[i].eType
                        item["exhibitionStartTime"] = data[i].startTime
                        item["exhibitionEndTime"] = data[i].endTime

                        exhibitionTypeDB.add(data[i].eType)
                        if (data[i].eImage == null) {
                            item["exhibitionImg"] = "null.jpg"
                        } else {
                            item["exhibitionImg"] = data[i].eImage
                        }
                        if (data[i].eFile2D == null) {
                            item["exhibitionUrl2D"] = "not built"
                        } else {
                            item["exhibitionUrl2D"] = data[i].eFile2D
                        }
                        items.add(item)
                    }
                    // recycler
                    exhibitionView.adapter = ExhibitionListForHomeAdapter(items)

                    // spinner
                    val exhibitionTypeA = ArrayList(HashSet(exhibitionTypeDB)) // 移除exhibitionType的重複值
                    exhibitionType.addAll(exhibitionTypeA)
                    val typeAdapter = activity?.let { ArrayAdapter<String>(it, R.layout.spinner_item, exhibitionType) }
                    typeAdapter?.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
                    spnType.adapter = typeAdapter

                }else{
                    msgErrorHome.text = "請確認網路連線正常與否！"
                    msgErrorHome.visibility = View.VISIBLE
                    exhibitionView.visibility = View.INVISIBLE
                }
            }
        })
        return items
        //////////////////////////
    }

    fun postAddExhibition(
        memNo: String,
        chkApply2D: CheckBox,
        chkApply3D: CheckBox,
        applyExhibitionName: EditText,
        applyExhibitionType: MultiAutoCompleteTextView,
        applyExhibitionStartTime: EditText,
        applyExhibitionEndTime: EditText,
        applyExhibitionText: EditText,
        application: Application
    ) {
        var chkStyle = ""
        chkStyle = if(chkApply2D.isChecked && !chkApply3D.isChecked){
            "2D"
        }else if(!chkApply2D.isChecked && chkApply3D.isChecked){
            "3D"
        }else{
            "2D, 3D"
        }

        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("eName", applyExhibitionName.text)
            .put("eType", applyExhibitionType.text)
            .put("startTime", applyExhibitionStartTime.text)
            .put("endTime", applyExhibitionEndTime.text)
            .put("style", chkStyle)
            .put("introdution", applyExhibitionText.text)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appAddExhibition(requestBody).enqueue(object: Callback<ExhibitionAdd> {
            override fun onFailure(call: Call<ExhibitionAdd>, t: Throwable) {
                Toast.makeText(application, "請確認網路連線正常與否！", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ExhibitionAdd>, response: Response<ExhibitionAdd>) {
                val status = response.body()?.status.toString()

                if(status == "success"){
                    Toast.makeText(application, "新增成功!", Toast.LENGTH_LONG).show()
                    application.finish()
                }else{
                    Toast.makeText(application, "新增失敗，請稍後再試", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun postApplicationList(
        memNo: String,
        items: ArrayList<Map<String, Any?>>,
        applicationList: RecyclerView,
        txtNotFoundApplication: TextView,
        applicationContext: Context,
        applicationListCon: ApplicationList
    ) {
        val jsonObject = JSONObject()
        jsonObject.put("memNo", memNo)
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appMemExhibition(requestBody).enqueue(object:
            Callback<ExhibitionResponseByMemNo> {
            override fun onFailure(call: Call<ExhibitionResponseByMemNo>, t: Throwable) {
                Toast.makeText(applicationContext, "請確認網路連線正常與否！", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ExhibitionResponseByMemNo>,
                response: Response<ExhibitionResponseByMemNo>
            ) {
                val status = response.body()?.status.toString()

                if(status == "success"){
                    val data = ArrayList(response.body()?.data)
                    for(i in data.indices){
                        val item = HashMap<String, Any?>()

                        item["exhibitionNo"] = data[i].eNo
                        item["exhibitionName"] = data[i].eName
                        item["exhibitionText"] = data[i].eIntrodution
                        item["exhibitionType"] = data[i].eType
                        item["exhibitionStartTime"] = data[i].startTime
                        item["exhibitionEndTime"] = data[i].endTime
                        item["exhibitionCheck"] = data[i].eCheck
                        item["exhibitionLogin"] = data[i].eLogin
                        item["exhibitionStyle"] = data[i].eStyle

                        if (data[i].eImage == null) {
                            item["exhibitionImg"] = "null.jpg"
                        } else {
                            item["exhibitionImg"] = data[i].eImage
                        }
                        items.add(item)
                    }

                    val layoutManager = LinearLayoutManager(applicationListCon)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    applicationList.layoutManager = layoutManager
                    applicationList.adapter = ApplyListAdapter(items)

                }else{
                    txtNotFoundApplication.text = "查無資料"
                    txtNotFoundApplication.visibility = View.VISIBLE
                }
            }
        })
    }

    fun postMemExhibition(
        memNo: String,
        txtNotFoundExhibitionMan: TextView,
        exhibitionList: RecyclerView,
        showInputPIN: ConstraintLayout,
        applicationContext: Context,
        exhibitionManage: ExhibitionManage
    ) {
        val items = ArrayList<Map<String, Any?>>()

        val jsonObject = JSONObject()
        jsonObject.put("memNo", memNo)
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appMemExhibitionChecked(requestBody).enqueue(object: Callback<ExhibitionResponseByMemNo> {
            override fun onFailure(call: Call<ExhibitionResponseByMemNo>, t: Throwable) {
                Toast.makeText(applicationContext, "請確認網路連線正常與否", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ExhibitionResponseByMemNo>,
                response: Response<ExhibitionResponseByMemNo>
            ) {
                val status = response.body()?.status.toString()

                if(status == "success"){
                    val data = ArrayList(response.body()?.data)
                    for(i in data.indices){
                        val item = HashMap<String, Any?>()

                        item["exhibitionNo"] = data[i].eNo
                        item["exhibitionName"] = data[i].eName
                        item["exhibitionText"] = data[i].eIntrodution
                        item["exhibitionType"] = data[i].eType
                        item["exhibitionStartTime"] = data[i].startTime
                        item["exhibitionEndTime"] = data[i].endTime
                        item["exhibitionStyle"] = data[i].eStyle

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
                val footerItem = HashMap<String, Any?>()
                footerItem["exhibitionNo"] = "footer"
                items.add(footerItem)
                val layoutManager = GridLayoutManager(exhibitionManage, 2)
                exhibitionList.layoutManager = layoutManager
                exhibitionList.adapter = ExhibitionListForExhibitionManageAdapter(items, showInputPIN)
            }
        })
    }

    fun postLoginExhibition(
        userNo: String,
        pin: String,
        txtMsg: TextView,
        txtPIN: EditText,
        applicationContext: Context
    ) {
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", userNo)
            .put("pin", pin)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appLoginExhibition(requestBody).enqueue(object: Callback<ExhibitionResponseByPin> {
            override fun onFailure(call: Call<ExhibitionResponseByPin>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ExhibitionResponseByPin>,
                response: Response<ExhibitionResponseByPin>
            ) {
                val status = response.body()?.status
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
    }

    fun postUpdateExhibition(
        exhibitionNo: String,
        exhibitionName: String,
        exhibitionType: String,
        exhibitionText: String,
        exhibitionManageDetail: ExhibitionManageDetail
    ) {
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("eNo", exhibitionNo)
            .put("eName", exhibitionName)
            .put("eType", exhibitionType)
            .put("introdution", exhibitionText)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appUpdateExhibition(requestBody).enqueue(object: Callback<ExhibitionAdd> {
            override fun onFailure(call: Call<ExhibitionAdd>, t: Throwable) {
                Toast.makeText(exhibitionManageDetail, "請確認網路連線正常與否！", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ExhibitionAdd>, response: Response<ExhibitionAdd>) {
                val status = response.body()?.status.toString()

                if(status == "success"){
                    Toast.makeText(exhibitionManageDetail, "更新成功!", Toast.LENGTH_LONG).show()
                    exhibitionManageDetail.finish()
                }else{
                    Toast.makeText(exhibitionManageDetail, "更新失敗，請稍後再試", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}