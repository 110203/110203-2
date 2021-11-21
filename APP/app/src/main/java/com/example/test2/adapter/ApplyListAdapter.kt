package com.example.test2.adapter

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.R

class ApplyListAdapter(private val applyData: ArrayList<Map<String, Any?>>) : RecyclerView.Adapter<ApplyListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyListHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_exhibition_item, parent, false)

        return ApplyListHolder(v)
    }

    override fun getItemCount(): Int {
        return applyData.size
    }

    override fun onBindViewHolder(holder: ApplyListHolder, position: Int) {
        holder.dataView.text = applyData[position]["exhibitionNo"].toString() + "｜" + applyData[position]["exhibitionName"].toString()

        // dialogDetail //
        val dialogDetail = holder.dialogDetail
        dialogDetail.setContentView(R.layout.layout_application_detail)

        val applyNo: TextView = dialogDetail.findViewById(R.id.txtApplyNo)
        val applyName: TextView = dialogDetail.findViewById(R.id.txtApplyName)
        val applyType: TextView = dialogDetail.findViewById(R.id.txtApplyType)
        val applyStartTime: TextView = dialogDetail.findViewById(R.id.txtApplyStartTime)
        val applyEndTime: TextView = dialogDetail.findViewById(R.id.txtApplyEndTime)
        val applyText: TextView = dialogDetail.findViewById(R.id.txtApplyText)
        val applyChk2D: CheckBox = dialogDetail.findViewById(R.id.chkApply2D_)
        val applyChk3D: CheckBox = dialogDetail.findViewById(R.id.chkApply3D_)

        applyNo.text = applyData[position]["exhibitionNo"].toString()
        applyName.text = applyData[position]["exhibitionName"].toString()
        applyType.text = applyData[position]["exhibitionType"].toString()
        applyStartTime.text = applyData[position]["exhibitionStartTime"].toString().split("T")[0]
        applyEndTime.text = applyData[position]["exhibitionEndTime"].toString().split("T")[0]
        applyText.text = applyData[position]["exhibitionText"].toString()
        when(applyData[position]["exhibitionStyle"].toString()){
            "2D" -> applyChk2D.isChecked = true
            "3D" -> applyChk3D.isChecked = true
            "2D, 3D" -> {
                applyChk2D.isChecked = true
                applyChk3D.isChecked = true
            }
        }
        //////////////////

        // dialogStatus //
        val dialogStatus = holder.dialogStatus
        dialogStatus.setContentView(R.layout.layout_application_detail_status)

        val applyNoStatus: TextView = dialogStatus.findViewById(R.id.txtApplyNo_)
        val msgCheck: TextView = dialogStatus.findViewById(R.id.msgCheck)
        val msgBuild: TextView = dialogStatus.findViewById(R.id.msgBuild)
        val msgWait: TextView = dialogStatus.findViewById(R.id.msgWait)
        val msgStart: TextView = dialogStatus.findViewById(R.id.msgStart)
        val msgFinish: TextView = dialogStatus.findViewById(R.id.msgFinish)

        applyNoStatus.text = applyData[position]["exhibitionNo"].toString()
        if(applyData[position]["exhibitionCheck"] == 0){
            msgBuild.visibility = View.INVISIBLE
            msgWait.visibility = View.INVISIBLE
            msgStart.visibility = View.INVISIBLE
            msgFinish.visibility = View.INVISIBLE
        }
        //////////////////

        // BUTTON //
        val btnStatus: ImageButton = dialogDetail.findViewById(R.id.btnApplyListStatus)
        val btnApplyList: ImageButton = dialogStatus.findViewById(R.id.btnApplyList)

        holder.itemView.setOnClickListener {
            dialogDetail.show()
        }

        btnStatus.setOnClickListener {
            dialogStatus.show()
            dialogDetail.dismiss()
        }

        btnApplyList.setOnClickListener {
            dialogStatus.dismiss()
            dialogDetail.show()
        }
        ////////////
    }

}

class ApplyListHolder(v: View) : RecyclerView.ViewHolder(v){
    val dataView : TextView = v.findViewById(R.id.exhibitionItem)

    val dialogDetail = Dialog(v.context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
    val dialogStatus = Dialog(v.context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)

}