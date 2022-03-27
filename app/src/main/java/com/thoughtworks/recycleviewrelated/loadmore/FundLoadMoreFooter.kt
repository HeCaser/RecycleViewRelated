package com.thoughtworks.recycleviewrelated.loadmore

import android.content.Context
import android.util.AttributeSet
import android.view.FocusFinder
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.NonNull
import com.scwang.smart.refresh.classics.ClassicsAbstract
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.thoughtworks.recycleviewrelated.R
import kotlinx.android.synthetic.main.fund_load_more_footer.view.*

open class FundLoadMoreFooter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ClassicsAbstract<FundLoadMoreFooter>(context, attrs, defStyleAttr), RefreshFooter {


    protected var mTextPulling = "上拉加载更多"
    protected var mTextRelease = "释放立即加载"
    protected var mTextLoading = "正在加载..."
    protected var mTextRefreshing = "正在刷新..."
    protected var mTextFinish = "加载完成"
    protected var mTextFailed = "加载失败"
    protected var mTextNothing = "没有更多数据了"

    protected var mNoMoreData = false
    private  var mKernel: RefreshKernel?=null

    init {
        LayoutInflater.from(context).inflate(R.layout.fund_load_more_footer, this)

        mTitleText = tvTitle
        ivLoading.visibility = View.INVISIBLE
        // 基类中用到，这里直接创建，防止空指针
        mArrowView = ImageView(context)
        mProgressView = ImageView(context)
        mTitleText.text = mTextPulling
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightSpec = MeasureSpec.makeMeasureSpec(400, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightSpec)
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        super.onFinish(refreshLayout, success)
        if (!mNoMoreData) {
            mTitleText.text = if (success) mTextFinish else mTextFailed
            return mFinishDuration
        }
        return 0
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    override fun setNoMoreData(noMoreData: Boolean): Boolean {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData
            if (noMoreData) {
                mTitleText.text = mTextNothing
            } else {
                mTitleText.text = mTextPulling
            }
        }
        return true
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
        if (!mNoMoreData) {
            when (newState) {
                RefreshState.None -> {
                    mTitleText.text = mTextPulling
                }
                RefreshState.PullUpToLoad -> {
                    mTitleText.text = mTextPulling
                }
                RefreshState.Loading, RefreshState.LoadReleased -> {
                    mTitleText.text = mTextLoading
                }
                RefreshState.ReleaseToLoad -> {
                    mTitleText.text = mTextRelease
                    ivLoading.visibility = View.VISIBLE
                    ivLoading.setBackgroundColor(resources.getColor(R.color.color_ff0033))
                }
                RefreshState.Refreshing -> {
                    mTitleText.text = mTextRefreshing
                }
            }
        }
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
        super.onInitialized(kernel, height, maxDragHeight)
        mKernel = kernel
    }

    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int
    ) {
//        if (percent > 0.5f) {
//            mKernel?.setState(RefreshState.Loading)
//        }
        setViewAlpha(percent)
        super.onMoving(isDragging, percent, offset, height, maxDragHeight)

    }


    fun setViewAlpha(alpha: Float) {
        ivLoading.alpha = alpha
    }
}