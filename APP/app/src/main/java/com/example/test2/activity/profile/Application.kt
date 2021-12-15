package com.example.test2.activity.profile

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView.CommaTokenizer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.R
import com.example.test2.data.Exhibition
import kotlinx.android.synthetic.main.activity_application.*
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

        val getMemNo: String = intent.getBundleExtra("bundle")?.getString("memNo").toString()

        applyUserId.setText(getMemNo)
        applyUserId.isEnabled = false

        val exhibitionType = arrayListOf("藝術", "消費展", "商業", "書畫")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, exhibitionType)
        applyExhibitionType.setAdapter(adapter)
        applyExhibitionType.setTokenizer(CommaTokenizer())

        ///// BUTTON /////
        // 展覽日期
        calendar = Calendar.getInstance()
        startYear = calendar.get(Calendar.YEAR)
        startMonth = calendar.get(Calendar.MONTH)
        startDay = calendar.get(Calendar.DAY_OF_MONTH)

        // 建展需求文件
        txtReqCheck.setOnClickListener {
            showReqBuild.visibility = View.VISIBLE
        }
        btnReqClose.setOnClickListener {
            showReqBuild.visibility = View.INVISIBLE
        }

        // 送出
        btnApplySave.setOnClickListener {
            onClickApplySave(getMemNo)
        }

        // back
        btnToBackProfileApplication.setOnClickListener {
            finish()
        }
        //////////////////

        txtSendEmail.setOnClickListener {
            val email = txtSendEmail.text.toString()
            val uri = Uri.parse("mailto:$email")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            startActivity(intent)
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

    fun onClickApplySave(memNo: String) {
        // 判斷錯誤類型
        var errSaveType = 1
        var errSave = ""
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
            0 -> Exhibition().postAddExhibition(memNo, chkApply2D, chkApply3D, applyExhibitionName, applyExhibitionType, applyExhibitionStartTime, applyExhibitionEndTime, applyExhibitionText, this)
            1 -> Toast.makeText(this, "請填寫$errSave", Toast.LENGTH_LONG).show()
            2 -> Toast.makeText(this, "請檢查開始日期與結束日期", Toast.LENGTH_LONG).show()
            3 -> Toast.makeText(this, "請勾選欲建立的展覽", Toast.LENGTH_LONG).show()
        }
    }
}
