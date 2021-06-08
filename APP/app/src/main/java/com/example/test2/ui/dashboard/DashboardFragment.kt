package com.example.test2.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.test2.activity.profile.EditProfile
import com.example.test2.activity.profile.ExhibitionManage
import com.example.test2.activity.profile.OrderQuery
import com.example.test2.R
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

        // 個人資料
        btnToEditProfile.setOnClickListener {
            activity?.let {
                var bundle = Bundle()
                bundle.putString("userId", "12345")
                var intent = Intent(it, EditProfile::class.java)
                intent.putExtra("bundle", bundle)
                it.startActivity(intent)
            }
        }

        // 申請建展
        btnToApplication.setOnClickListener {
            showApplication.visibility = View.VISIBLE
            msgContact.text = "請將詳細需求、展覽檔案等傳至"
            msgContact3.text = "跟我們做聯繫喔！"
        }
        btnApplyClose.setOnClickListener {
            showApplication.visibility = View.INVISIBLE
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
    }
}