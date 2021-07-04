package com.example.test2.activity.profile

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.MultiAutoCompleteTextView.CommaTokenizer
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.R
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



        // 儲存
        btnApplySave.setOnClickListener {
            applyExhibitionName.setText("")
            applyExhibitionType.setText("")
            chkApply2D.isChecked = false
            chkApply3D.isChecked = false
            applyExhibitionText.setText("")
        }

        btnApplyView.setOnClickListener {
            var intent = Intent(this, ApplicationList::class.java)
            startActivity(intent)
        }
    }

    fun onClickStartTime(view: View) {
        startYear = calendar.get(Calendar.YEAR)
        startMonth = calendar.get(Calendar.MONTH)
        startDay = calendar.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(this, { _, year, month, day_of_month ->
            calendar[Calendar.YEAR] = year
            calendar[Calendar.MONTH] = month
            calendar[Calendar.DAY_OF_MONTH] = day_of_month
            val myFormat = "yyyy.MM.dd"
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
            val myFormat = "yyyy.MM.dd"
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
            applyExhibitionEndTime.setText(sdf.format(calendar.time))
        }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
        calendar.add(startMonth, 0)
        dialog.datePicker.minDate = calendar.timeInMillis
        dialog.show()
    }


}