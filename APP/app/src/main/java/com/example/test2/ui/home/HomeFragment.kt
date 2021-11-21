package com.example.test2.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.test2.R
import com.example.test2.adapter.ExhibitionListForHomeAdapter
import com.example.test2.data.Exhibition
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var mActivity: Activity? = null

    private var exhibitionType = arrayListOf("全部")

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

        val typeAdapter = activity?.let { ArrayAdapter<String>(it, R.layout.spinner_item, exhibitionType) }
        typeAdapter?.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
        spnType.adapter = typeAdapter

        // recycler
        val layoutManager = GridLayoutManager(activity, 2)
        exhibitionView.layoutManager = layoutManager

        val items = Exhibition().postExhibition(exhibitionType, exhibitionView, activity, spnType, msgErrorHome)

        spnType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = spnType.selectedItem.toString()
                val selectItems = ArrayList<Map<String, Any?>>()
                if(type == "全部"){
                    exhibitionView.adapter = ExhibitionListForHomeAdapter(items)
                }else{
                    for(i in 0 until items.size){
                        if(items[i]["exhibitionType"] == type){
                            selectItems.add(items[i])
                        }
                    }
                    exhibitionView.adapter = ExhibitionListForHomeAdapter(selectItems)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val listener = SwipeRefreshLayout.OnRefreshListener {
            Exhibition().postExhibition(
                exhibitionType,
                exhibitionView,
                activity,
                spnType,
                msgErrorHome
            )
            swipe.isRefreshing = false  //讓下拉更新的進度條（轉圈）停止顯示
        }
        swipe.setOnRefreshListener(listener)
    }
}