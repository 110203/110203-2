package com.example.test2.activity.profile

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.R
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.UserResponse
import com.example.test2.data.model.UserUpdate
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_signup.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.hide()

        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val memNo : String? = sharedPref.getString("memNo", "")
        postMemberProfile(memNo, editor)

        txtMemNo.setText(memNo)
        txtMemNo.isEnabled = false


        btnToBackProfileEditProfile.setOnClickListener {
            finish()
        }

        btnEditPwd.setOnClickListener {
            var bundle = Bundle()
            bundle.putString("userId", "12345") //TODO
            var intent = Intent(this, EditPassword::class.java)
            intent.putExtra("bundle", bundle)
            this.startActivity(intent)
        }

        btnProfileSave.setOnClickListener {
            var memName = txtMemName.text.toString()
            var memPhone = txtTel.text.toString()
            var memAddress = txtAddress.text.toString()
            postUpdateMember(memNo, memName, memPhone, memAddress, editor)
        }

    }

    private fun postMemberProfile(memNo: String?, editor: SharedPreferences.Editor) {
        ////////// POST //////////
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        Log.d("40:", requestBody.toString())

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳UserResponse回來
        RetrofitClient.instance.appMemberProfile(requestBody).enqueue(object: Callback<UserResponse>{
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@EditProfile, t.message, Toast.LENGTH_LONG).show()
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
                    val memPhone = data?.get(0).memPhone
                    editor.putString("memNo", memNo).apply()
                    editor.putString("memName", memName).apply()
                    editor.putString("memAddress", memAddress).apply()
                    editor.putString("memPhone", memPhone).apply()

                    txtMemName.setText(memName)
                    txtAddress.setText(memAddress)
                    txtTel.setText(memPhone)
                }else{
                    Toast.makeText(this@EditProfile, "咦，好像出了什麼問題", Toast.LENGTH_LONG).show()
                }
            }

        })
        //////////////////////////////
    }

    private fun postUpdateMember(
        memNo: String?,
        memName: String,
        memPhone: String,
        memAddress: String,
        editor: SharedPreferences.Editor
    ) {
        ////////// POST //////////
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("memName", memName)
            .put("address", memAddress)
            .put("phone", memPhone)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳UserResponse回來
        RetrofitClient.instance.appUpdateMember(requestBody).enqueue(object:
            Callback<UserUpdate> {
            override fun onFailure(call: Call<UserUpdate>, t: Throwable) {
                Toast.makeText(this@EditProfile, t.message, Toast.LENGTH_LONG).show()
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<UserUpdate>,
                response: Response<UserUpdate>
            ) {
                var status = response.body()?.status.toString()
                if(status == "success"){
                    editor.putString("memNo", memNo).apply()
                    editor.putString("memName", memName).apply()
                    editor.putString("memAddress", memAddress).apply()
                    editor.putString("memPhone", memPhone).apply()
                    Toast.makeText(this@EditProfile, "更新成功！", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@EditProfile, "更新失敗，請稍後再試！", Toast.LENGTH_LONG).show()
                }
            }

        })
        //////////////////////////////
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // if按下Back
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder(this)
                .setMessage("確認已儲存更新內容？")
                .setTitle("返回")
                .setPositiveButton("確認",
                    DialogInterface.OnClickListener { dialog, id ->
                        finish()
                    })
                .setNeutralButton("取消", null)
                .show()
        }
        return false
    }
}