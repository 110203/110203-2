package com.example.test2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.activity.profile.Login


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val sharedPref = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        var sharedPreferencesMemNo = ""
        sharedPreferencesMemNo = sharedPref.getString("memNo", "").toString()

        if(sharedPreferencesMemNo == ""){
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                var intent = Intent(this@SplashActivity, VisitorActivity::class.java)
                startActivity(intent)
            }, 3000)
        }else{
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                var intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }, 3000)
        }
    }
}
