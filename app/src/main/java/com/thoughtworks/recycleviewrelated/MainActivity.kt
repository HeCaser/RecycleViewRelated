package com.thoughtworks.recycleviewrelated

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thoughtworks.recycleviewrelated.loadmore.RvLoadMoreActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvGoTest.setOnClickListener {
            test()
        }

    }

    private fun test(){
        startActivity(Intent(this,RvLoadMoreActivity::class.java))
    }
}