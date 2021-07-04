package com.example.test2.activity.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.R
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_dashboard.btnProfileSave


class EditProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.hide()

        var userId = R.string.memNo

        txtUserId.setText(userId)
        txtUserId.setEnabled(false)

        btnProfileSave.setOnClickListener {
            txtUserName.text.toString()
            userId
            txtTel.text.toString()
            txtAddress.text.toString()
            // TODO
            // 與資料庫連結
            // 修改資料庫
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