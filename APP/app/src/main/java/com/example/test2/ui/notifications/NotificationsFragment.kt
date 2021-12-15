package com.example.test2.ui.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.test2.R
import com.example.test2.activity.cart.CartCheckout
import com.example.test2.activity.profile.Login
import com.example.test2.adapter.CartListForShoppingCartGetData
import com.example.test2.data.ShoppingCart
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    var totPrice = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getSharedPreferences("User", Context.MODE_PRIVATE)
        val memNo : String? = sharedPref?.getString("memNo", "")
        if(memNo == ""){
            btnCheckout.isEnabled = false
            btnCheckout.isClickable = false

            activity?.let {
                val intent = Intent(it, Login::class.java)
                it.startActivity(intent)
            }
        }else{
            ShoppingCart().postMyCart(memNo, msgError, applicationList, btnCheckout, activity)
        }

        btnCheckout.setOnClickListener {
            val selectGood = CartListForShoppingCartGetData().getSelectGood()
            val selectGoodTotPrice = CartListForShoppingCartGetData().getSelectGoodTotPrice()
            if(selectGoodTotPrice == 0){
                Toast.makeText(activity, "請先選擇要結帳的商品！", Toast.LENGTH_SHORT).show()
            }else{
                activity?.let {
                    val bundle = Bundle()
                    bundle.putString("memNo", memNo)
                    bundle.putInt("selectGoodTotPrice", selectGoodTotPrice)
                    bundle.putString("selectCartCount", selectGood.size.toString())
                    for(i in 0 until selectGood.size){
                        bundle.putString("data$i", selectGood[i].toString())
                    }

                    val intent = Intent(it, CartCheckout::class.java)
                    intent.putExtra("bundle", bundle)
                    it.startActivity(intent)
                }
            }
        }
    }

    override fun onResume() {
        val sharedPref = activity?.getSharedPreferences("User", Context.MODE_PRIVATE)
        val memNo : String? = sharedPref?.getString("memNo", "")

        ShoppingCart().postMyCart(memNo, msgError, applicationList, btnCheckout, activity)

        super.onResume()
    }
}
