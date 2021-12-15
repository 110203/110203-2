package com.example.test2.data

import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.test2.MainActivity
import com.example.test2.activity.profile.EditPassword
import com.example.test2.activity.profile.EditProfile
import com.example.test2.activity.profile.Login
import com.example.test2.activity.profile.Signup
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.UserResponse
import com.example.test2.data.model.UserResponseBySignUp
import com.example.test2.data.model.UserUpdate
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Users {
    fun postUpdateMemberPwd(
        memNo: String?,
        pwdOld: String,
        pwdNew: String,
        showEditPwdSuccess: ConstraintLayout,
        editPassword: EditPassword
    ) {
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("memOldPassword", pwdOld)
            .put("memNewPassword", pwdNew)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appUpdateMemberPwd(requestBody).enqueue(object:
            Callback<UserUpdate> {
            override fun onFailure(call: Call<UserUpdate>, t: Throwable) {
                Toast.makeText(editPassword, "請確認網路連線正常與否", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<UserUpdate>,
                response: Response<UserUpdate>
            ) {
                val status = response.body()?.status.toString()
                if(status == "success"){
                    showEditPwdSuccess.visibility = View.VISIBLE
                }else{
                    Toast.makeText(editPassword, "更新失敗，請確認密碼輸入正確！", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun postUpdateMember(
        memNo: String?,
        memName: String,
        memPhone: String,
        memAddress: String,
        editor: SharedPreferences.Editor,
        editProfile: EditProfile
    ) {
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("memName", memName)
            .put("address", memAddress)
            .put("phone", memPhone)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appUpdateMember(requestBody).enqueue(object:
            Callback<UserUpdate> {
            override fun onFailure(call: Call<UserUpdate>, t: Throwable) {
                Toast.makeText(editProfile, "請確認網路連線正常與否", Toast.LENGTH_LONG).show()
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
                    Toast.makeText(editProfile, "更新成功！", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(editProfile, "更新失敗，請稍後再試！", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun postMemberProfile(
        memNo: String?,
        editor: SharedPreferences.Editor,
        txtMemName: EditText,
        txtAddress: EditText,
        txtTel: EditText,
        editProfile: EditProfile
    ) {
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appMemberProfile(requestBody).enqueue(object: Callback<UserResponse>{
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(editProfile, "請確認網路連線正常與否", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                val status = response.body()?.status.toString()
                if(status == "success"){
                    val data = ArrayList(response.body()?.data)
                    val memName = data[0].memName
                    val memAddress = data[0].memAddress
                    val memPhone = data[0].memPhone
                    editor.putString("memNo", memNo).apply()
                    editor.putString("memName", memName).apply()
                    editor.putString("memAddress", memAddress).apply()
                    editor.putString("memPhone", memPhone).apply()

                    txtMemName.setText(memName)
                    txtAddress.setText(memAddress)
                    txtTel.setText(memPhone)
                }else{
                    Toast.makeText(editProfile, "咦，好像出了什麼問題", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun postLogin(
        memNo: String,
        memPwd: String,
        editor: SharedPreferences.Editor,
        txtLoginPwd: EditText,
        login: Login
    ) {
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("memPassword", memPwd)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appLogin(requestBody).enqueue(object: Callback<UserResponse>{
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(login, "請確認網路連線正常與否", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                val status = response.body()?.status.toString()
                if(status == "success"){
                    val data = ArrayList(response.body()?.data)
                    val memName = data[0].memName
                    val memAddress = data[0].memAddress
                    val memPhone = data[0].memAddress
                    editor.putString("memNo", memNo).apply()
                    editor.putString("memName", memName).apply()
                    editor.putString("memAddress", memAddress).apply()
                    editor.putString("memPhone", memPhone).apply()
                    txtLoginPwd.setText("")

                    val intent = Intent(login, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    login.startActivity(intent)
                }else{
                    Toast.makeText(login, "登入失敗，請確認帳號密碼", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun postSignUp(
        memNo: String,
        memPwd: String,
        memName: String,
        txtSignupEmail: EditText,
        txtSignupPwd: EditText,
        txtSignupPwdCheck: EditText,
        txtSignupName: EditText,
        signup: Signup
    ) {
        val jsonObject = JSONObject()
        val requestBody = jsonObject.put("memNo", memNo)
            .put("memPassword", memPwd)
            .put("memName", memName)
            .toString().toRequestBody("application/json".toMediaTypeOrNull())

        RetrofitClient.instance.appAddMember(requestBody).enqueue(object: Callback<UserResponseBySignUp> {
            override fun onFailure(call: Call<UserResponseBySignUp>, t: Throwable) {
                Toast.makeText(signup, "請確認網路連線正常與否", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<UserResponseBySignUp>,
                response: Response<UserResponseBySignUp>
            ) {
                val status = response.body()?.status.toString()
                if(status == "success"){
                    Toast.makeText(signup, "註冊成功！", Toast.LENGTH_LONG).show()
                    txtSignupEmail.setText("")
                    txtSignupPwd.setText("")
                    txtSignupPwdCheck.setText("")
                    txtSignupName.setText("")
                    signup.finish()
                }else{
                    Toast.makeText(signup, "該email已註冊過！", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
