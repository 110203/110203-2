package com.example.test2.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.test2.R
import com.example.test2.activity.profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getSharedPreferences("User", Context.MODE_PRIVATE)
        val memNo : String? = sharedPref?.getString("memNo", "")
        if(memNo == ""){
            activity?.let {
                var intent = Intent(it, Login::class.java)
                it.startActivity(intent)
            }
        }


        // 個人資料
        btnToEditProfile.setOnClickListener {
            activity?.let {
                var intent = Intent(it, EditProfile::class.java)
                it.startActivity(intent)
            }
        }

        // 申請建展
        btnToApplication.setOnClickListener {
            activity?.let {
                var intent = Intent(it, Application::class.java)
                it.startActivity(intent)
            }
        }

        // 管理展覽
        btnToManageExhibition.setOnClickListener {
            activity?.let {
                var bundle = Bundle()
                bundle.putString("userId", "12345") //TODO
                var intent = Intent(it, ExhibitionManage::class.java)
                intent.putExtra("bundle", bundle)
                it.startActivity(intent)
            }
        }

        // 查看訂單
        btnToOrder.setOnClickListener {
            activity?.let {
                var bundle = Bundle()
                bundle.putString("userId", "12345") //TODO
                var intent = Intent(it, OrderQuery::class.java)
                intent.putExtra("bundle", bundle)
                it.startActivity(intent)
            }
        }

        // 修改密碼
        btnApplyView.setOnClickListener {
            activity?.let {
                var bundle = Bundle()
                bundle.putString("userId", "12345") //TODO
                var intent = Intent(it, ApplicationList::class.java)
                intent.putExtra("bundle", bundle)
                it.startActivity(intent)
            }
        }
    }
}