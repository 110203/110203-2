package com.example.test2.activity.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import com.example.test2.MainActivity
import com.example.test2.R
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.UserResponse
import com.example.test2.data.model.UserResponseBySignUp
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.view.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            var memNo = txtSignupEmail.text.toString()
            var memPwd = txtSignupPwd.text.toString()
            var memPwdChk = txtSignupPwdCheck.text.toString()
            var memName = txtSignupName.text.toString()
            if(memPwd == memPwdChk){
                postSignUp(memNo, memPwd, memName)
            }else{
                Toast.makeText(this@Signup, "密碼輸入不相同！", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun postSignUp(memNo: String, memPwd: String, memName: String) {
        ////////// POST //////////
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("memPassword", memPwd)
            .put("memName", memName)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳UserResponse回來
        RetrofitClient.instance.appAddMember(requestBody).enqueue(object: Callback<UserResponseBySignUp> {
            override fun onFailure(call: Call<UserResponseBySignUp>, t: Throwable) {
                Toast.makeText(this@Signup, t.message, Toast.LENGTH_LONG).show()
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<UserResponseBySignUp>,
                response: Response<UserResponseBySignUp>
            ) {
                var status = response.body()?.status.toString()
                if(status == "success"){
                    Toast.makeText(this@Signup, "註冊成功！", Toast.LENGTH_LONG).show()
                    txtSignupEmail.setText("")
                    txtSignupPwd.setText("")
                    txtSignupPwdCheck.setText("")
                    txtSignupName.setText("")
                }else{
                    Toast.makeText(this@Signup, "該email已註冊過！", Toast.LENGTH_LONG).show()
                }
            }

        })
        //////////////////////////////
    }
}