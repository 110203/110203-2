package com.example.test2.ui.notifications

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.test2.R
import com.example.test2.activity.profile.Login
import com.example.test2.adapter.CartListForShoppingCartGetData
import com.example.test2.data.ShoppingCart
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var mActivity: Activity? = null

    var totPrice = 0
    lateinit var txtTotPrice_ : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        activity?.let {
            this.mActivity = it
        }

        return inflater.inflate(R.layout.fragment_notifications, container, false)
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

        ShoppingCart().postMyCart(memNo, msgError, applicationList, btnCheckout, mActivity)

        btnCheckout.setOnClickListener {
            val result = CartListForShoppingCartGetData().getSelectGoodNo()
            btnCheckout.text = result.toString()
//            activity?.let {
//                val bundle = Bundle()
//                bundle.putString("memNo", memNo)
//                val intent = Intent(it, CartCheckout::class.java)
//                intent.putExtra("bundle", bundle)
//                it.startActivity(intent)
//            }
        }

    }
}