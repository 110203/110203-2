package com.example.test2.activity.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.R
import com.example.test2.data.Exhibition
import kotlinx.android.synthetic.main.activity_application_list.*

class ApplicationList : AppCompatActivity() {
    var items = ArrayList<Map<String, Any?>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_list)
        supportActionBar?.hide()

        val getMemNo: String = intent.getBundleExtra("bundle")?.getString("memNo").toString()
        Exhibition().postApplicationList(getMemNo, items, applicationList, txtNotFoundApplication, applicationContext, this)

        btnOpenForm.setOnClickListener {
            val uri: Uri = Uri.parse(this.getString(R.string.googleForm))
            val i = Intent(Intent.ACTION_VIEW, uri)
            startActivity(i)
        }

        btnToBackProfileApplyList.setOnClickListener {
            finish()
        }
    }
}