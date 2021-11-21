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
import com.example.test2.activity.home.CommodityDetail
import com.squareup.picasso.Picasso

class CommodityListForCommodityAdapter(val items: ArrayList<MutableMap<String, Any?>>) : RecyclerView.Adapter<CommodityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommodityViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_commodity_home, parent, false)

        return CommodityViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CommodityViewHolder, position: Int){
        val goodNo = items[position]["goodNo"].toString()
        val goodName = items[position]["goodName"].toString()
        val goodPrice = items[position]["goodPrice"].toString()
        val goodText = items[position]["goodText"].toString()
        val goodAmount = items[position]["goodAmount"].toString()
        val goodMyCartAmount = items[position]["myCartAmount"].toString()
        val photoPath = items[position]["goodImg"].toString()

        holder.goodName.text = goodName
        holder.goodPrice.text = "$$goodPrice"
        val imgUrl: String? = holder.toto?.getString(R.string.BASE_IMG_URL)
        Picasso.get().load(imgUrl + photoPath).into(holder.goodImg)

        holder.goodImg.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("goodNo", goodNo)
            bundle.putString("goodName", goodName)
            bundle.putString("goodPrice", goodPrice)
            bundle.putString("goodText", goodText)
            bundle.putString("goodAmount", goodAmount)
            bundle.putString("goodMyCartAmount", goodMyCartAmount)
            bundle.putString("goodImgPath", photoPath)
            val intent = Intent(holder.toto, CommodityDetail::class.java)
            intent.putExtra("bundle", bundle)
            holder.toto?.startActivity(intent)
        }
    }
}

class CommodityViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val goodName: TextView = v.findViewById(R.id.goodName)
    val goodPrice: TextView = v.findViewById(R.id.goodPrice)
    val goodImg: ImageView = v.findViewById(R.id.goodImg)

    val toto: Context? = v.context
}