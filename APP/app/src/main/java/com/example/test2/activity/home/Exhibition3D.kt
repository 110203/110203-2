package com.example.test2.activity.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.test2.R
import com.unity3d.player.UnityPlayerActivity
import kotlinx.android.synthetic.main.activity_exhibition_3_d.*

class Exhibition3D : UnityPlayerActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_exhibition_3_d)
        frameLayout.addView(mUnityPlayer.view)

        btnToBackHome_3D.setImageResource(R.drawable.ic_left_arrow)
        btnToBackHome_3D.setColorFilter(ContextCompat.getColor(this, R.color.fifth))
        btnToCommodityList_3D.setImageResource(R.drawable.ic_right_arrow)
        btnToCommodityList_3D.setColorFilter(ContextCompat.getColor(this, R.color.fifth))

        val getShowNo = intent.getBundleExtra("bundle")?.getString("showNo")
        val getShowName = intent.getBundleExtra("bundle")?.getString("showName")
        txtShowName3D.text = getShowName.toString()

        btnToBackHome_3D.setOnClickListener {
            finish()
        }

        btnToCommodityList_3D.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("showNo", getShowNo)
            bundle.putString("showName", getShowName)
            val intent = Intent(this, Commodity::class.java)
            intent.putExtra("bundle", bundle)
            this.startActivity(intent)
        }
    }
}
