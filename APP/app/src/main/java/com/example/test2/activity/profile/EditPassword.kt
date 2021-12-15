package com.example.test2.activity.profile

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.test2.R
import com.example.test2.data.Users
import kotlinx.android.synthetic.main.activity_edit_password.*

class EditPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_password)
        supportActionBar?.hide()

        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        val memNo : String? = sharedPref.getString("memNo", "")

        btnToBackProfileEditPwd.setOnClickListener {
            finish()
        }

        btnEditPwdSave.setOnClickListener {
            val pwdOld = txtEditPwdOld.text.toString()
            val pwdNew = txtEditPwdNew.text.toString()
            val pwdNewCheck = txtEditPwdNewCheck.text.toString()
            if(pwdNew == pwdNewCheck){
                Users().postUpdateMemberPwd(memNo, pwdOld, pwdNew, showEditPwdSuccess, this)
            }else{
                Toast.makeText(this@EditPassword, "密碼輸入不相同！", Toast.LENGTH_LONG).show()
            }
        }

        btnEditPwdSuccess.setOnClickListener {
            finish()
        }
    }
}