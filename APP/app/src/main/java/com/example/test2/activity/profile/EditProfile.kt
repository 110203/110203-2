package com.example.test2.activity.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test2.R
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_dashboard.btnProfileSave

class EditProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.hide()

        btnProfileSave.setOnClickListener {
            txtUserName.text.toString()
            txtUserId.text.toString()
            txtTel.text.toString()
            txtAddress.text.toString()
            // 與資料庫連結
            // 修改資料庫
        }

        btnProfileCancel.setOnClickListener {
            finish()
        }
    }
}