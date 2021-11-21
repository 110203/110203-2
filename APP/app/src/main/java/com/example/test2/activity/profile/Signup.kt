package com.example.test2.activity.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import com.example.test2.R
import com.example.test2.data.Users
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()

        var visi = 0 // 預設密碼不可見(0)
        btnSignupVisi.setOnClickListener {
            if(visi == 0){
                txtSignupPwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                visi = 1
            }else{
                txtSignupPwd.transformationMethod = PasswordTransformationMethod.getInstance()
                visi = 0
            }
        }

        btnSignUp.setOnClickListener {
            val memNo = txtSignupEmail.text.toString()
            val memPwd = txtSignupPwd.text.toString()
            val memPwdChk = txtSignupPwdCheck.text.toString()
            val memName = txtSignupName.text.toString()
            if(memPwd == memPwdChk){
                Users().postSignUp(memNo, memPwd, memName, txtSignupEmail, txtSignupPwd, txtSignupPwdCheck, txtSignupName, this)
            }else{
                Toast.makeText(this@Signup, "密碼輸入不相同！", Toast.LENGTH_LONG).show()
            }
        }
    }
}