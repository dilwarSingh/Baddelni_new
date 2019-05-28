package com.baddelni.baddelni.categories

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.categories.SubCategoryItem

class SubCatRecyclerAdapter(val context: Context, val list: List<SubCategoryItem>, val productListAdapter: AdapterSubCategory) : RecyclerView.Adapter<SubCatRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SubCatRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sub_cat, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(vh: SubCatRecyclerAdapter.ViewHolder, postion: Int) {
        vh.title.text = list[postion].subCategory
        vh.image.visibility = View.GONE
        vh.itemView.setOnClickListener {
            productListAdapter.filter.filter(list[postion].id.toString())
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.icon)
        val title: TextView = view.findViewById(R.id.title)
    }
}