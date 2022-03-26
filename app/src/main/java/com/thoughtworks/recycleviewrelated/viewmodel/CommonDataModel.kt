package com.thoughtworks.recycleviewrelated.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommonDataModel : ViewModel() {
    val mStringList = MutableLiveData<List<String>>()

    private val mHandler = Handler(Looper.getMainLooper())

    private val mList = arrayListOf<String>()

    init {
        for (i in 0 until 10) {
            mList.add("Hello $i 你好啊 ")
        }
    }

    fun requestStringList() {
        mHandler.postDelayed({
            mStringList.postValue(mList)
        }, 1000)
    }


}