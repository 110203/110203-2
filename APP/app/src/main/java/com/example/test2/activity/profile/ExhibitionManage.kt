package com.example.test2.activity.profile

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.test2.R
import com.example.test2.data.Exhibition
import kotlinx.android.synthetic.main.activity_exhibition_manage.*

class ExhibitionManage : AppCompatActivity() {
    var items = ArrayList<Map<String, Any?>>()
    private var getMemNo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exhibition_manage)
        supportActionBar?.hide()

        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        getMemNo = sharedPref?.getString("memNo", "").toString()
        Exhibition().postMemExhibition(getMemNo, txtNotFoundExhibitionMan, exhibitionList, showInputPIN, applicationContext, this)

        btnPINClose.setOnClickListener {
            showInputPIN.visibility = View.INVISIBLE
        }

        btnPIN.setOnClickListener {
            val pin = txtPIN.text.toString()
            Exhibition().postLoginExhibition(getMemNo, pin, txtMsg, txtPIN, applicationContext)
        }

        btnToBackProfileExhibitionManage.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        getMemNo = sharedPref?.getString("memNo", "").toString()

        Exhibition().postMemExhibition(getMemNo, txtNotFoundExhibitionMan, exhibitionList, showInputPIN, applicationContext, this)

        super.onResume()
    }
}