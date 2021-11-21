package com.example.test2.activity.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test2.R
import com.example.test2.data.Exhibition
import kotlinx.android.synthetic.main.activity_exhibition_manage_detail.*

class ExhibitionManageDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exhibition_manage_detail)
        supportActionBar?.hide()

        val getExhibitionNo = intent.getBundleExtra("bundle")?.getString("exhibitionNo")
        val getExhibitionName = intent.getBundleExtra("bundle")?.getString("exhibitionName")
        val getExhibitionType = intent.getBundleExtra("bundle")?.getString("exhibitionType")
        val getExhibitionText = intent.getBundleExtra("bundle")?.getString("exhibitionText")
        val getExhibitionStartTime = intent.getBundleExtra("bundle")?.getString("exhibitionStartTime")
        val getExhibitionEndTime = intent.getBundleExtra("bundle")?.getString("exhibitionEndTime")
        val getExhibitionStyle = intent.getBundleExtra("bundle")?.getString("exhibitionStyle")

        txtExhibitionName.setText(getExhibitionName)
        txtExhibitionType.setText(getExhibitionType)
        txtExhibitionText.setText(getExhibitionText)
        txtExhibitionEndTime.setText(getExhibitionEndTime)

        val startTimeSplit = getExhibitionStartTime?.split("T")
        txtExhibitionStartTime.setText(startTimeSplit?.get(0))
        val endTimeSplit = getExhibitionEndTime?.split("T")
        txtExhibitionEndTime.setText(endTimeSplit?.get(0))

        when(getExhibitionStyle){
            "2D" -> chkExhibition2D.isChecked = true
            "3D" -> chkExhibition3D.isChecked = true
            "2D, 3D" -> {
                chkExhibition2D.isChecked = true
                chkExhibition3D.isChecked = true
            }
        }

        btnToBackProfileExhibitionManageDetail.setOnClickListener {
            finish()
        }

        btnSaveEditExhibition.setOnClickListener {
            val txtExhibitionName = txtExhibitionName.text.toString()
            val txtExhibitionType = txtExhibitionType.text.toString()
            val txtExhibitionText = txtExhibitionText.text.toString()
            if (getExhibitionNo != null) {
                Exhibition().postUpdateExhibition(getExhibitionNo, txtExhibitionName, txtExhibitionType, txtExhibitionText, this)
            }
        }

    }
}