package com.thoughtworks.recycleviewrelated.loadmore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.thoughtworks.recycleviewrelated.R
import com.thoughtworks.recycleviewrelated.adapter.RvCommonAdapter
import com.thoughtworks.recycleviewrelated.viewmodel.CommonDataModel
import kotlinx.android.synthetic.main.rv_load_more_activity.*

/**
 * 雪球需求，修改加载更多的交互。
 *
 * 上拉 Footer ，有文案，有 lottie。
 */
class RvLoadMoreActivity : AppCompatActivity() {

    private lateinit var viewModel: CommonDataModel
    private lateinit var mAdapter: RvCommonAdapter
    private val mDataList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_load_more_activity)
        viewModel = ViewModelProviders.of(this).get(CommonDataModel::class.java)
        initListener()

        initViewAndData()
    }

    fun initListener(){
        tvTop.setOnClickListener {
            viewModel.getStringList()
        }
        tvScroll.setOnClickListener {
            recyclerView.smoothScrollToPosition(4)
        }
    }
    fun initViewAndData() {
        smartRefresh?.apply {
            setRefreshFooter(FundLoadMoreFooter(this@RvLoadMoreActivity))
            setEnableRefresh(false)
            setEnableAutoLoadMore(false)
            setOnLoadMoreListener {
                viewModel.getStringList()
            }
        }
        mAdapter = RvCommonAdapter(mDataList)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager =
                SnappingLinearLayoutManager(
                    this@RvLoadMoreActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = mAdapter
        }




        viewModel.mStringList.observe(this, {
            handleRequestSuccess(it)

        })
    }

    private fun handleRequestSuccess(l: List<String>) {
        val size = mDataList.size
       val target =  if (size == 0) 0 else size
        mDataList.addAll(l)
        mAdapter.notifyDataSetChanged()
        smartRefresh.finishLoadMore()

        Handler().postDelayed({
            recyclerView.smoothScrollToPosition(target)
        }, 1500)

    }
}