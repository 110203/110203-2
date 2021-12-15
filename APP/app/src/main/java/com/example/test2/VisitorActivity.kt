package com.example.test2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_visitor.*

class VisitorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visitor)
        supportActionBar?.hide()

        btnVisitorGoToHome.setOnClickListener {
            var intent = Intent(this@VisitorActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}