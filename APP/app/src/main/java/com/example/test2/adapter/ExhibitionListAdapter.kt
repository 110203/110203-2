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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.R
import com.example.test2.activity.home.ExhibitionDetail
import com.example.test2.activity.profile.ExhibitionManageDetail
import com.squareup.picasso.Picasso

class ExhibitionListForHomeAdapter(
    private val items: ArrayList<Map<String, Any?>>
) : RecyclerView.Adapter<ExhibitionListForHomeAdapter.ViewHolder>() {

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
        var exhibitionStartTime = items[position]["exhibitionStartTime"].toString()
        var exhibitionEndTime = items[position]["exhibitionEndTime"].toString()

        exhibitionStartTime = exhibitionStartTime.split("T")[0]
        exhibitionEndTime = exhibitionEndTime.split("T")[0]

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
            bundle.putString("showStartTime", exhibitionStartTime)
            bundle.putString("showEndTime", exhibitionEndTime)
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

class ExhibitionListForExhibitionManageAdapter(private val items: ArrayList<Map<String, Any?>>, private val showInputPIN: ConstraintLayout) : RecyclerView.Adapter<ExhibitionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExhibitionViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_exhibition_home, parent, false)
        return ExhibitionViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ExhibitionViewHolder, position: Int) {
        val exhibitionNo = items[position]["exhibitionNo"].toString()
        val exhibitionName = items[position]["exhibitionName"].toString()
        val exhibitionText = items[position]["exhibitionText"].toString()
        val exhibitionType = items[position]["exhibitionType"].toString()
        val photoPath = items[position]["exhibitionImg"].toString()
        val exhibitionStartTime = items[position]["exhibitionStartTime"].toString().replace(".000Z", "")
        val exhibitionEndTime = items[position]["exhibitionEndTime"].toString().replace(".000Z", "")
        val exhibitionStyle = items[position]["exhibitionStyle"].toString()

        if(exhibitionNo === "footer"){
            holder.inputPinLayout.visibility = View.VISIBLE
            holder.exhibitionLayout.visibility = View.INVISIBLE
        }else{
            holder.inputPinLayout.visibility = View.INVISIBLE
            holder.exhibitionLayout.visibility = View.VISIBLE

            holder.exhibitionName.text = exhibitionName
            holder.exhibitionText.text = exhibitionText
            val imgUrl: String = holder.toto.getString(R.string.BASE_IMG_URL)
            Picasso.get().load(imgUrl + photoPath).into(holder.exhibitionImg)
        }

        holder.pinImg.setOnClickListener {
            this.showInputPIN.visibility = View.VISIBLE
        }
        holder.itemView.setOnClickListener {

        }
        holder.exhibitionImg.clipToOutline = true
        holder.exhibitionImg.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("exhibitionNo", exhibitionNo)
            bundle.putString("exhibitionName", exhibitionName)
            bundle.putString("exhibitionType", exhibitionType)
            bundle.putString("exhibitionText", exhibitionText)
            bundle.putString("exhibitionStartTime", exhibitionStartTime)
            bundle.putString("exhibitionEndTime", exhibitionEndTime)
            bundle.putString("exhibitionStyle", exhibitionStyle)
            val intent = Intent(holder.toto, ExhibitionManageDetail::class.java)
            intent.putExtra("bundle", bundle)
            holder.toto.startActivity(intent)
        }
    }
}

class ExhibitionViewHolder(v: View) : RecyclerView.ViewHolder(v){
    val exhibitionName : TextView = v.findViewById(R.id.showName)
    val exhibitionText : TextView = v.findViewById(R.id.showText)
    val exhibitionImg : ImageView = v.findViewById(R.id.showImg)
    val exhibitionLayout : ConstraintLayout = v.findViewById(R.id.showLayout)
    val inputPinLayout : ConstraintLayout = v.findViewById(R.id.input_pin_footer)
    val pinImg : ImageView = v.findViewById(R.id.pinImg)

    val toto : Context = v.context
}