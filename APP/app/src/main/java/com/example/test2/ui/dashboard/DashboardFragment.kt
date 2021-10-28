package com.example.test2.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.test2.MainActivity
import com.example.test2.R
import com.example.test2.SplashActivity
import com.example.test2.activity.profile.*
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
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getSharedPreferences("User", Context.MODE_PRIVATE)
        val memNo : String? = sharedPref?.getString("memNo", "")
        if(memNo == ""){
            activity?.let {
                val intent = Intent(it, Login::class.java)
                it.startActivity(intent)
            }
        }

        // 個人資料
        ddsds.setOnClickListener {
            activity?.let {
                val intent = Intent(it, EditProfile::class.java)
                it.startActivity(intent)
            }
        }

        // 申請建展
        btnToApplication.setOnClickListener {
            activity?.let {
                val bundle = Bundle()
                bundle.putString("memNo", memNo)
                val intent = Intent(it, Application::class.java)
                intent.putExtra("bundle", bundle)
                it.startActivity(intent)
            }
        }

        // 管理展覽
        btnToManageExhibition.setOnClickListener {
            activity?.let {
                val bundle = Bundle()
                bundle.putString("memNo", memNo)
                val intent = Intent(it, ExhibitionManage::class.java)
                intent.putExtra("bundle", bundle)
                it.startActivity(intent)
            }
        }

        // 查看申請紀錄
        btnApplyView.setOnClickListener {
            activity?.let {
                val bundle = Bundle()
                bundle.putString("memNo", memNo)
                val intent = Intent(it, ApplicationList::class.java)
                intent.putExtra("bundle", bundle)
                it.startActivity(intent)
            }
        }

        // 查看訂單
        btnToOrder.setOnClickListener {
            activity?.let {
                val bundle = Bundle()
                bundle.putString("memNo", memNo)
                val intent = Intent(it, OrderQuery::class.java)
                intent.putExtra("bundle", bundle)
                it.startActivity(intent)
            }
        }

        // 登出
        btnLogout.setOnClickListener {
            val editor = sharedPref?.edit()
            editor?.putString("memNo", "")?.apply()
            editor?.putString("memName", "")?.apply()
            editor?.putString("memAddress", "")?.apply()
            editor?.putString("memPhone", "")?.apply()
            // activity?.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            // activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
            activity?.let {
                val intent = Intent(it, SplashActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                it.startActivity(intent)
            }
        }
    }
}