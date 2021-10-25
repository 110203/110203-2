package com.example.test2.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.ExhibitionDetail
import com.example.test2.R
import com.squareup.picasso.Picasso

class ExhibitionListForHomeAdapter(private val items: ArrayList<Map<String, Any?>>) : RecyclerView.Adapter<ExhibitionListForHomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_exhibition_home, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val exhibitionNo = items[position]["exhibitionNo"].toString()
        val exhibitionName = items[position]["exhibitionName"].toString()
        val exhibitionText = items[position]["exhibitionText"].toString()
        val photoPath = items[position]["exhibitionImg"].toString()
        val exhibitionUrl2D = items[position]["exhibitionUrl2D"].toString()

        holder.exhibitionName.text = exhibitionName
        holder.exhibitionText.text = exhibitionText
        val imgUrl: String? = holder.toto?.getString(R.string.BASE_IMG_URL)
        Picasso.get().load(imgUrl + photoPath).into(holder.exhibitionImg)

        holder.exhibitionImg.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("showNo", exhibitionNo)
            bundle.putString("showName", exhibitionName)
            bundle.putString("showImgPath", photoPath)
            bundle.putString("showText", exhibitionText)
            bundle.putString("eUrl2D", exhibitionUrl2D)
            val intent = Intent(holder.toto, ExhibitionDetail::class.java)
            intent.putExtra("bundle", bundle)
            holder.toto?.startActivity(intent)
        }
        holder.exhibitionImg.clipToOutline = true
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val exhibitionName: TextView = v.findViewById(R.id.showName)
        val exhibitionText: TextView = v.findViewById(R.id.showText)
        val exhibitionImg: ImageView = v.findViewById(R.id.showImg)

        val toto: Context? = v.context
    }

}
