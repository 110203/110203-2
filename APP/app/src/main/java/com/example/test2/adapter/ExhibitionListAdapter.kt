package com.example.test2.adapter

import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.ExhibitionDetail
import com.example.test2.R
import com.example.test2.ui.home.HomeFragment
import com.squareup.picasso.Picasso

class ExhibitionListAdapter(private var items: ArrayList<Map<String, Any?>>) : RecyclerView.Adapter<HomeFragment.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragment.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_exhibition_home, parent, false)

        return HomeFragment.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: HomeFragment.ViewHolder, position: Int){
        val exhibitionNo = items[position]["exhibitionNo"].toString()
        val exhibitionName = items[position]["exhibitionName"].toString()
        val exhibitionText = items[position]["exhibitionText"].toString()
        val photoPath = items[position]["exhibitionImg"].toString()

        holder.exhibitionName.text = exhibitionName
        holder.exhibitionText.text = exhibitionText
        val imgUrl: String = Resources.getSystem().getString(R.string.BASE_IMG_URL)
        Picasso.get().load(imgUrl + photoPath).into(holder.exhibitionImg)

        holder.exhibitionImg.setOnClickListener {
            //myDialog.show()
            val bundle = Bundle()
            bundle.putString("showNo", exhibitionNo)
            bundle.putString("showName", exhibitionName)
            bundle.putString("showImgPath", photoPath)
            bundle.putString("showText", exhibitionText)
            val intent = Intent(holder.toto, ExhibitionDetail::class.java)
            intent.putExtra("bundle", bundle)
            holder.toto?.startActivity(intent)
        }
        holder.exhibitionImg.clipToOutline = true
    }

    fun updateList(list:ArrayList<Map<String, Any?>>){
        items = list
    }

}