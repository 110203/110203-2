package com.example.test2.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.R
import com.example.test2.activity.profile.OrderDetail
import com.example.test2.cusFormatDateTime
import com.squareup.picasso.Picasso

class OrderListForOrderQueryAdapter(private val items: ArrayList<MutableMap<String, Any?>>) : RecyclerView.Adapter<OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_order_item, parent, false)
        return OrderViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val orNo = items[position]["orNo"].toString()
        val orTime = items[position]["orTime"].toString().replace(".000Z", "")
        val timeSplit = orTime.split("T")

        val orState = items[position]["orState"].toString()
        val orTotalPrice = items[position]["orTotalPrice"].toString()
        val photoPath = items[position]["orImg"].toString()
        holder.txtOrderId.text = "#$orNo"
        holder.txtOrderDate.text = cusFormatDateTime(timeSplit[0], timeSplit[1])
        val imgUrl: String? = holder.toto?.getString(R.string.BASE_IMG_URL)
        Picasso.get().load(imgUrl + photoPath).into(holder.imgOrder)

        when(orState){
            "0" -> holder.txtOrderStatus.text = "訂單確認"
            "1" -> holder.txtOrderStatus.text = "準備出貨中"
            "2" -> holder.txtOrderStatus.text = "出貨"
            else -> holder.txtOrderStatus.text = "商品已送達"
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("orNo", orNo)
            bundle.putString("orState", orState)
            bundle.putString("orTotalPrice", orTotalPrice)
            val intent = Intent(holder.toto, OrderDetail::class.java)
            intent.putExtra("bundle", bundle)
            holder.toto?.startActivity(intent)
        }
    }
}

class OrderViewHolder(v: View) : RecyclerView.ViewHolder(v){
    val txtOrderId : TextView = v.findViewById(R.id.txtOrderId)
    val txtOrderDate : TextView = v.findViewById(R.id.txtOrderDate)
    val txtOrderStatus : TextView = v.findViewById(R.id.txtOrderStatus)
    val imgOrder : ImageView = v.findViewById(R.id.imgOrder)

    val toto: Context? = v.context
}