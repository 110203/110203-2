package com.example.test2.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.test2.ExhibitionDetail
import com.example.test2.R
import com.example.test2.adapter.ExhibitionListAdapter
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.ExhibitionResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var mActivity: Activity? = null
    private var items = ArrayList<Map<String, Any?>>()
    private var exhibitionType = arrayListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        activity?.let {
            this.mActivity = it
        }

        val rootView =  inflater.inflate(R.layout.fragment_home, container, false)
        postExhibition()
        // rest of my stuff
//        val layoutManager = GridLayoutManager(activity, 2)
//        exhibitionView.layoutManager = layoutManager
//        exhibitionView.adapter = ExhibitionListAdapter(items)
        // return the root view
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postExhibition()


    }

    override fun onStart() {
        super.onStart()




        // spinner
        exhibitionType = ArrayList(HashSet(exhibitionType)) // 移除exhibitionType的重複值
        val typeAdapter = activity?.let { ArrayAdapter<String>(it, R.layout.spinner_dropdown_item, exhibitionType) }
        typeAdapter?.setDropDownViewResource(R.layout.spinner_item)
        spnType.adapter = typeAdapter

        val superHero = arrayOf<String?>("Batman", "SuperMan", "Flash", "AquaMan", "Shazam")
        val arrayAdapter = activity?.let { ArrayAdapter<String>(it, R.layout.spinner_item, superHero) }
        arrayAdapter?.setDropDownViewResource(R.layout.spinner_item)
        spinner2.adapter = arrayAdapter

        //設定刷新View的顏色
        swipe.setColorSchemeResources(
            R.color.first,
            R.color.fourth,
            R.color.fifth
        )

        val listener = SwipeRefreshLayout.OnRefreshListener {
            // 此為 Lambda 寫法
            ExhibitionListAdapter(items).notifyDataSetChanged()  //讓 RecyclerView 的 Adapter 更新畫面
            swipe.isRefreshing = false  //讓下拉更新的進度條（轉圈）停止顯示
        }
        swipe.setOnRefreshListener(listener)
    }

    private fun postExhibition() {
        ////////// POST //////////
        // 利用APIService
        RetrofitClient.instance.appAllExhibition().enqueue(object: Callback<ExhibitionResponse>{
            override fun onFailure(call: Call<ExhibitionResponse>, t: Throwable) {
                t.message?.let { Log.d("ERROR", it) }
            }

            override fun onResponse(
                call: Call<ExhibitionResponse>,
                response: Response<ExhibitionResponse>
            ) {
                val status = response.body()?.status.toString()
                val data = ArrayList(response.body()?.data)

                if(status == "success"){

                    // 將data裝進HashMap中
                    for(i in data.indices) {
                        val item = HashMap<String, Any?>()

                        item["exhibitionNo"] = data[i].eNo
                        item["exhibitionName"] = data[i].eName
                        item["exhibitionText"] = data[i].eIntrodution
                        item["exhibitionType"] = data[i].eType
                        item["exhibitionStartTime"] = data[i].startTime
                        item["exhibitionEndTime"] = data[i].endTime

                        exhibitionType.add(data[i].eType)
                        if (data[i].eImage == null) {
                            item["exhibitionImg"] = "null.jpg"
                        } else {
                            item["exhibitionImg"] = data[i].eImage
                        }
                        items.add(item)
                    }
                }else{
                    Log.d("ERROR", "NOT FOUND")
                }
                // recycler
//                val layoutManager = activity?.let { GridLayoutManager(it, 2) }
//                exhibitionView.layoutManager = layoutManager
//                exhibitionView.adapter = ExhibitionListAdapter(items)
//                ExhibitionListAdapter(items).updateList(items)
            }
        })
        //////////////////////////


    }

    class ExhibitionListAdapter(private var items: ArrayList<Map<String, Any?>>) : RecyclerView.Adapter<ViewHolder>() {
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

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val exhibitionName: TextView = v.findViewById(R.id.showName)
        val exhibitionText: TextView = v.findViewById(R.id.showText)
        val exhibitionImg: ImageView = v.findViewById(R.id.showImg)

        val toto: Context? = v.context

    }
}