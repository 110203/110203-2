package com.example.test2.activity.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.R
import com.example.test2.data.Users
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        var visi = 0 // 預設密碼不可見(0)
        btnLoginVisi.setOnClickListener {
            if(visi == 0){
                txtLoginPwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                visi = 1
            }else{
                txtLoginPwd.transformationMethod = PasswordTransformationMethod.getInstance()
                visi = 0
            }
        }

        btnLogin.setOnClickListener {
            val memNo = txtLoginEmail.text.toString()
            val memPwd = txtLoginPwd.text.toString()
            Users().postLogin(memNo, memPwd, editor, txtLoginPwd, this)
        }

        txtGoToSignup.setOnClickListener {
            val intent = Intent(this@Login, Signup::class.java)
            startActivity(intent)
        }
    }
}