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
import com.example.test2.data.Users
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
        Users().postMemberProfile(memNo, editor, txtMemName, txtAddress, txtTel, this)

        txtMemNo.setText(memNo)
        txtMemNo.isEnabled = false

        btnToBackProfileEditProfile.setOnClickListener {
            finish()
        }

        btnEditPwd.setOnClickListener {
            val intent = Intent(this, EditPassword::class.java)
            this.startActivity(intent)
        }

        btnProfileSave.setOnClickListener {
            val memName = txtMemName.text.toString()
            val memPhone = txtTel.text.toString()
            val memAddress = txtAddress.text.toString()
            Users().postUpdateMember(memNo, memName, memPhone, memAddress, editor, this)
        }
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