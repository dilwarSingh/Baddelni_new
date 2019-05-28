package com.baddelni.baddelni.categories

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baddelni.baddelni.R

class CatRecyclerAdapter(val context: Context, val list: List<String>) : RecyclerView.Adapter<CatRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CatRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sub_categories, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(vh: CatRecyclerAdapter.ViewHolder, postion: Int) {

        vh.title.text = "item $postion"
        vh.description.text = "desc $postion"
        vh.image.setImageDrawable(context.getDrawable(R.drawable.account_selected))

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
        val userImage: ImageView = view.findViewById(R.id.userImage)
        val title: TextView = view.findViewById(R.id.text)
        val description: TextView = view.findViewById(R.id.description)
    }
}