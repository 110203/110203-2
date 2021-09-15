package com.example.test2.activity.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.MainActivity
import com.example.test2.R
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.UserResponse
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            var memNo = txtLoginEmail.text.toString()
            var memPwd = txtLoginPwd.text.toString()
            postLogin(memNo, memPwd, editor, txtLoginPwd)
        }

        txtGoToSignup.setOnClickListener {
            var intent = Intent(this@Login, Signup::class.java)
            startActivity(intent)
        }
    }

    private fun postLogin(
        memNo: String,
        memPwd: String,
        editor: SharedPreferences.Editor,
        txtLoginPwd: EditText
    ) {
        ////////// POST //////////
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("memPassword", memPwd)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        Log.d("40:", requestBody.toString())

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳UserResponse回來
        RetrofitClient.instance.appLogin(requestBody).enqueue(object: Callback<UserResponse>{
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@Login, t.message, Toast.LENGTH_LONG).show()
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                var status = response.body()?.status.toString()
                if(status == "success"){
                    val data = ArrayList(response.body()?.data)
                    val memName = data?.get(0).memName
                    val memAddress = data?.get(0).memAddress
                    val memPhone = data?.get(0).memAddress
                    editor.putString("memNo", memNo).apply()
                    editor.putString("memName", memName).apply()
                    editor.putString("memAddress", memAddress).apply()
                    editor.putString("memPhone", memPhone).apply()
                    txtLoginPwd.setText("")

                    var intent = Intent(this@Login, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }else{
                    Toast.makeText(this@Login, "登入失敗，請確認帳號密碼", Toast.LENGTH_LONG).show()
                }
            }

        })
        //////////////////////////////
    }
}