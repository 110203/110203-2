package com.example.test2.activity.profile

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView.CommaTokenizer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.R
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.CartAdd
import com.example.test2.data.model.ExhibitionAdd
import kotlinx.android.synthetic.main.activity_application.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class Application : AppCompatActivity() {
    private var startYear = 0
    private var startMonth = 0
    private var startDay = 0
    private lateinit var calendar : Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application)
        supportActionBar?.hide()

        applyUserId.setText(R.string.memNo)
        applyUserId.setEnabled(false)

        // 展覽類型
        val exhibitionType = arrayListOf("藝術", "消費展", "商業", "書畫")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, exhibitionType)
        applyExhibitionType.setAdapter(adapter)
        applyExhibitionType.setTokenizer(CommaTokenizer())

        // 展覽日期
        calendar = Calendar.getInstance()
        startYear = calendar.get(Calendar.YEAR)
        startMonth = calendar.get(Calendar.MONTH)
        startDay = calendar.get(Calendar.DAY_OF_MONTH)

        btnApplySave.setOnClickListener {
            onClickApplySave()
        }
        btnApplyView.setOnClickListener {
            var intent = Intent(this, ApplicationList::class.java)
            startActivity(intent)
        }
        btnToBackProfileApplication.setOnClickListener {
            finish()
        }
    }

    fun onClickStartTime(view: View) {
        calendar = Calendar.getInstance()
        startYear = calendar.get(Calendar.YEAR)
        startMonth = calendar.get(Calendar.MONTH)
        startDay = calendar.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(this, { _, year, month, day_of_month ->
            calendar[Calendar.YEAR] = year
            calendar[Calendar.MONTH] = month
            calendar[Calendar.DAY_OF_MONTH] = day_of_month
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
            applyExhibitionStartTime.setText(sdf.format(calendar.time))

        }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
        calendar.add(Calendar.MONTH, 1)
        dialog.datePicker.minDate = calendar.timeInMillis
        dialog.show()
    }

    fun onClickEndTime(view: View) {
        val dialog = DatePickerDialog(this, { _, year, month, day_of_month ->
            calendar[Calendar.YEAR] = year
            calendar[Calendar.MONTH] = month
            calendar[Calendar.DAY_OF_MONTH] = day_of_month
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
            applyExhibitionEndTime.setText(sdf.format(calendar.time))
        }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
        calendar.add(startMonth, 0)
        dialog.datePicker.minDate = calendar.timeInMillis
        dialog.show()
    }

    fun onClickApplySave() {
        // 判斷錯誤類型
        var errSaveType = 1
        var errSave = ""
        val nameArray = arrayListOf("name", "type", "sTime", "eTime", "chk", "text")
        if(applyExhibitionName.text.isEmpty()){
            errSave = "展覽名稱"
            imgErr_name.visibility = View.VISIBLE
        }else if(applyExhibitionType.text.isEmpty()){
            errSave = "展覽類型"
            imgErr_type.visibility = View.VISIBLE
        }else if(applyExhibitionStartTime.text.isEmpty()){
            errSave = "開始日期"
            imgErr_sTime.visibility = View.VISIBLE
        }else if(applyExhibitionEndTime.text.isEmpty()){
            errSave = "結束日期"
            imgErr_eTime.visibility = View.VISIBLE
        }else if(applyExhibitionStartTime.text.toString() > applyExhibitionEndTime.text.toString()){
            errSaveType = 2
        }else if(!chkApply2D.isChecked && !chkApply3D.isChecked){
            errSaveType = 3
        }else if(applyExhibitionText.text.isEmpty()){
            errSave = "展覽簡介"
            imgErr_text.visibility = View.VISIBLE
        }else{
            errSaveType = 0
        }

        // 針對各類型進行提示
        when(errSaveType){
            0 -> postAddExhibition()
            1 -> Toast.makeText(this, "請填寫$errSave", Toast.LENGTH_LONG).show()
            2 -> Toast.makeText(this, "請檢查開始日期與結束日期", Toast.LENGTH_LONG).show()
            3 -> Toast.makeText(this, "請勾選欲建立的展覽", Toast.LENGTH_LONG).show()
        }
    }

    private fun postAddExhibition() {
        var chkStyle = ""
        chkStyle = if(chkApply2D.isChecked && !chkApply3D.isChecked){
            "2D"
        }else if(!chkApply2D.isChecked && chkApply3D.isChecked){
            "3D"
        }else{
            "2D, 3D"
        }

        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", "a22753516@gmail.com")
            .put("eName", applyExhibitionName.text)
            .put("eType", applyExhibitionType.text)
            .put("startTime", applyExhibitionStartTime.text)
            .put("endTime", applyExhibitionEndTime.text)
            .put("style", chkStyle)
            .put("introdution", applyExhibitionText.text)
            .toString().toRequestBody("application/json".toMediaTypeOrNull()) // TODO

        RetrofitClient.instance.appAddExhibition(requestBody).enqueue(object: Callback<ExhibitionAdd> {
            override fun onFailure(call: Call<ExhibitionAdd>, t: Throwable) {
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(call: Call<ExhibitionAdd>, response: Response<ExhibitionAdd>) {
                var status = response.body()?.status.toString()

                if(status == "success"){
                    Toast.makeText(this@Application, "新增成功!", Toast.LENGTH_LONG).show()
                    applyExhibitionName.setText("")
                    applyExhibitionType.setText("")
                    chkApply2D.isChecked = false
                    chkApply3D.isChecked = false
                    applyExhibitionText.setText("")
                }else{
                    Toast.makeText(this@Application, "新增失敗，請稍後再嘗試", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
