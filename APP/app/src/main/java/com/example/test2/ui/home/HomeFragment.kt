package com.example.test2.ui.home

import android.app.Activity
import android.app.Dialog
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.ExhibitionDetail
import com.example.test2.activity.home.Exhibition_2D
import com.example.test2.R
import com.example.test2.activity.home.Exhibition_3D
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
    var items = ArrayList<Map<String, Any?>>()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        activity?.let{
            this.mActivity = it
        }

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postExhibition()
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
                var status = response.body()?.status.toString()
                val data = ArrayList(response.body()?.data)

                if(status == "success"){
                    var exhibitionType = arrayListOf<String>()

                    // 將data裝進HashMap中
                    for(i in data?.indices) {
                        var item = HashMap<String, Any?>()

                        item["exhibitionNo"] = data?.get(i).eNo
                        item["exhibitionName"] = data?.get(i).eName
                        item["exhibitionText"] = data?.get(i).eIntrodution
                        item["exhibitionType"] = data?.get(i).eType
                        item["exhibitionStartTime"] = data?.get(i).startTime
                        item["exhibitionEndTime"] = data?.get(i).endTime

                        exhibitionType.add(data?.get(i).eType)
                        if (data?.get(i).eImage == null) {
                            item["exhibitionImg"] = "null.jpg"
                        } else {
                            item["exhibitionImg"] = data?.get(i).eImage
                        }
                        items.add(item)
                    }

                    // recycler
                    var layoutManager = GridLayoutManager(activity, 2)
                    exhibitoinView.layoutManager = layoutManager
                    exhibitoinView.adapter = ExhibitionListAdapter(items)

                    // spinner
                    exhibitionType = ArrayList(HashSet(exhibitionType)) // 移除exhibitionType的重複值
                    val typeAdapter = activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_dropdown_item, exhibitionType) }
                    spnType.adapter = typeAdapter

                }else{
                    Log.d("ERROR", "NOT FOUND")
                }
            }
        })
        //////////////////////////
    }

    class ExhibitionListAdapter(private val items: ArrayList<Map<String, Any?>>) : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_exhibition_home, parent, false)

            return ViewHolder(v)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int){
            var exhibitionNo = items[position]["exhibitionNo"].toString()
            var exhibitionName = items[position]["exhibitionName"].toString()
            var exhibitionText = items[position]["exhibitionText"].toString()
            var photoPath = items[position]["exhibitionImg"].toString()

            holder.exhibitionName.text = exhibitionName
            holder.exhibitionText.text = exhibitionText
            Picasso.get().load("http://140.131.114.155/file/$photoPath").into(holder.exhibitionImg)

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
        }

    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val exhibitionName: TextView = v.findViewById(R.id.showName)
        val exhibitionText: TextView = v.findViewById(R.id.showText)
        val exhibitionImg: ImageView = v.findViewById(R.id.showImg)

        val toto: Context? = v.context

    }
}