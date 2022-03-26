package com.thoughtworks.recycleviewrelated.refresh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.thoughtworks.recycleviewrelated.R
import com.thoughtworks.recycleviewrelated.adapter.RvCommonAdapter
import com.thoughtworks.recycleviewrelated.viewmodel.CommonDataModel
import kotlinx.android.synthetic.main.rv_load_more_activity.*

class RvLoadMoreActivity : AppCompatActivity() {

    private lateinit var viewModel: CommonDataModel
    private lateinit var mAdapter: RvCommonAdapter
    private val mDataList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_load_more_activity)
        viewModel = ViewModelProviders.of(this).get(CommonDataModel::class.java)
        initViewAndData()
    }

    fun initViewAndData() {
        mAdapter = RvCommonAdapter(mDataList)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@RvLoadMoreActivity, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        tvTop.setOnClickListener {
            viewModel.requestStringList()
        }


        viewModel.mStringList.observe(this,{
            mDataList.addAll(it)
            mAdapter.notifyDataSetChanged()
        })
    }
}