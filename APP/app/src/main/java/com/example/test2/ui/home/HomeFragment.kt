package com.example.test2.ui.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.test2.R
import com.example.test2.adapter.ExhibitionListForHomeAdapter
import com.example.test2.data.api.RetrofitClient
import com.example.test2.data.model.ExhibitionResponse
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var mActivity: Activity? = null

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

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // recycler
        val layoutManager = GridLayoutManager(activity, 2)
        exhibitionView.layoutManager = layoutManager

        postExhibition()
    }

    override fun onStart() {
        super.onStart()

        val listener = SwipeRefreshLayout.OnRefreshListener {
            postExhibition()
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
                    val items = ArrayList<Map<String, Any?>>()
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
                        if (data[i].eFile2D == null) {
                            item["exhibitionUrl2D"] = "not built"
                        } else {
                            item["exhibitionUrl2D"] = data[i].eFile2D
                        }
                        items.add(item)
                    }
                    // recycler
                    exhibitionView.adapter = ExhibitionListForHomeAdapter(items)

                    // spinner
                    exhibitionType = ArrayList(HashSet(exhibitionType)) // 移除exhibitionType的重複值
                    val typeAdapter = activity?.let { ArrayAdapter<String>(it, R.layout.spinner_item, exhibitionType) }
                    typeAdapter?.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
                    spnType.adapter = typeAdapter

                }else{
                    Log.d("ERROR", "NOT FOUND")
                }
            }
        })
        //////////////////////////
    }

}