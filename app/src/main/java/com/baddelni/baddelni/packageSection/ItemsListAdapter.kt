package com.baddelni.baddelni.packageSection

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.categories.SingleProductResponse.ReplacementsItem

class ItemsListAdapter(val list: List<ReplacementsItem>) : RecyclerView.Adapter<ItemsListAdapter.ViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, vt: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(vh: ViewHolder, postion: Int) {

        val item = list[postion]
        vh.tv.text = item.name
        vh.tv.isChecked = item.selected
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tv = v.findViewById<CheckBox>(R.id.tv)

        init {
            tv.setOnClickListener {
                list[adapterPosition].selected = (it as CheckBox).isChecked
                notifyDataSetChanged()
            }
        }
    }

}
