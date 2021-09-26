package com.example.test2.ui.notifications

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.R
import com.example.test2.activity.cart.CartCheckout
import com.example.test2.activity.profile.Login
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.CartResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_commodity.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var mActivity: Activity? = null
    var items = ArrayList<MutableMap<String, Any?>>()
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

        var price = 0
        postMyCart(memNo)

        btnCheckout.setOnClickListener {
            activity?.let {
                val bundle = Bundle()
                bundle.putString("memNo", memNo)
                val intent = Intent(it, CartCheckout::class.java)
                intent.putExtra("bundle", bundle)
                it.startActivity(intent)
            }
        }

    }

    private fun postMyCart(memNo: String?) {
        ////////// POST //////////
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("memNo", memNo)

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        // 利用APIService中的appAllGoods, 將requestBody(eNo) POST 至資料庫, 回傳GoodResponse回來
        RetrofitClient.instance.appAllShoppingcart(requestBody).enqueue(object: Callback<CartResponse> {
            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<CartResponse>,
                response: Response<CartResponse>
            ) {
                var status = response.body()?.status.toString()
                val data = ArrayList(response.body()?.data)

                if(status == "success"){
                    // 將data裝進HashMap中
                    for(i in data?.indices){
                        var item = mutableMapOf<String, Any?>()

                        item["cartGoodNo"] = data?.get(i).gNo
                        item["cartGoodName"] = data?.get(i).gName
                        item["cartGoodPrice"] = data?.get(i).gPrice
                        item["cartGoodAmount"] = data?.get(i).gAmount
                        totPrice += data?.get(i).gPrice * data?.get(i).gAmount
                        if(data?.get(i).gImage == null){
                            item["cartGoodImg"] = "null.jpg"
                        }else{
                            item["cartGoodImg"] = data?.get(i).gImage
                        }
                        items.add(item)
                    }
                    var layoutManager = LinearLayoutManager(mActivity)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL

                    applicationList.layoutManager = layoutManager
                    applicationList.adapter = CartListAdapter(items)
                    val s = String.format("%,d", totPrice).replace(',', ',')
                    btnCheckout.text = "填寫購買資訊(\$$s)"
                }else{
                    textView7.text = "NOT FOUND."
                }
            }
        })
        //////////////////////////////
    }

    class CartListAdapter(val items: ArrayList<MutableMap<String, Any?>>) : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_cart_item, parent, false)

            val pa = LayoutInflater.from(parent.context).inflate(R.layout.fragment_notifications, parent, false);


            return ViewHolder(v, pa)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var photoPath = items[position]["cartGoodImg"].toString()

            holder.cartGoodName.text = items[position]["cartGoodName"].toString()
            holder.cartGoodPrice.text = items[position]["cartGoodPrice"].toString()
            holder.cartGoodAmount.text = items[position]["cartGoodAmount"].toString()

            Picasso.get().load("http://140.131.114.155/file/$photoPath").into(holder.cartGoodImg)

            holder.chkCart.setOnClickListener {
                if(holder.chkCart.isChecked){
                    Log.d("jid", items[position]["cartGoodPrice"].toString())

                }else{
//                    it.btnCheckout.text = "0"
                }

            }

            holder.btnAddCart.setOnClickListener {
                postUpdateCart()
            }

            holder.btnSubCart.setOnClickListener {
                postUpdateCart()
            }

            holder.btnDeleteCart.setOnClickListener {
                postDeleteCart()
            }


        }

        private fun postDeleteCart() {
            TODO("Not yet implemented")
        }

        private fun postUpdateCart() {
            TODO("Not yet implemented")
        }

    }

    class ViewHolder(v: View, pa: View) : RecyclerView.ViewHolder(v) {
        val cartGoodName: TextView = v.findViewById(R.id.txtCommodityName)
        val cartGoodPrice: TextView = v.findViewById(R.id.txtCommodityPrice)
        val cartGoodAmount: TextView = v.findViewById(R.id.txtCommodityAmount)
        val cartGoodImg: ImageView = v.findViewById(R.id.imgCommodityImg)
        val chkCart: CheckBox = v.findViewById(R.id.chkCart)
        val btnAddCart: Button = v.findViewById(R.id.btnAddCart)
        val btnSubCart: Button = v.findViewById(R.id.btnSubCart)
        val btnDeleteCart: Button = v.findViewById(R.id.btnDeleteCart)


//        val btnCheckout: Button = pa.findViewById(R.id.btnCheckout)
    }
}