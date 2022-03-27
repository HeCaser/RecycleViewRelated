package com.thoughtworks.recycleviewrelated.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.recycleviewrelated.R

class RvCommonAdapter(private val data: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val colorList = arrayListOf<Int>(
        R.color.colorPrimary,R.color.colorAccent,R.color.color_937700,R.color.color_ff0033
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RvCommonHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RvCommonHolder).apply {
            holder.tvTest.text = "$position ${data[position]}"
            holder.itemView.setBackgroundColor(holder.itemView.context.resources.getColor(colorList[position%(colorList.size)]))
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class RvCommonHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTest = view.findViewById<TextView>(R.id.tvTest)

    }
}