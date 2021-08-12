package com.example.test2.activity.profile

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.test2.R
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.UserUpdate
import kotlinx.android.synthetic.main.activity_edit_password.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            var pwdOld = txtEditPwdOld.text.toString()
            var pwdNew = txtEditPwdNew.text.toString()
            var pwdNewCheck = txtEditPwdNewCheck.text.toString()
            if(pwdNew == pwdNewCheck){
                postUpdateMemberPwd(memNo, pwdOld, pwdNew)
            }else{
                Toast.makeText(this@EditPassword, "密碼輸入不相同！", Toast.LENGTH_LONG).show()
            }
        }

        btnEditPwdSuccess.setOnClickListener {
            finish()
        }
    }

    private fun postUpdateMemberPwd(memNo: String?, pwdOld: String, pwdNew: String) {
        ////////// POST //////////
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("memOldPassword", pwdOld)
            .put("memNewPassword", pwdNew)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳UserResponse回來
        RetrofitClient.instance.appUpdateMemberPwd(requestBody).enqueue(object:
            Callback<UserUpdate> {
            override fun onFailure(call: Call<UserUpdate>, t: Throwable) {
                Toast.makeText(this@EditPassword, t.message, Toast.LENGTH_LONG).show()
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<UserUpdate>,
                response: Response<UserUpdate>
            ) {
                var status = response.body()?.status.toString()
                if(status == "success"){
                    showEditPwdSuccess.visibility = View.VISIBLE
                }else{
                    Toast.makeText(this@EditPassword, "更新失敗，請確認密碼輸入正確！", Toast.LENGTH_LONG).show()
                }
            }

        })
        //////////////////////////////
    }
}