package com.baddelni.baddelni.account.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baddelni.baddelni.R
import com.baddelni.baddelni.account.pojos.pojoCats
import com.baddelni.baddelni.account.setGlideImageNetworkPath

class AdapterEditIntrests(val context: Context, val list: Array<pojoCats>) : RecyclerView.Adapter<AdapterEditIntrests.viewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): viewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_categories, viewGroup, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: viewHolder, position: Int) {

        val pojo = list[position]

        viewHolder.text.text = pojo.name
        viewHolder.image.setGlideImageNetworkPath(pojo.imageUrl)

    }

    override fun getItemCount() = list.size

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var text: TextView = itemView.findViewById(R.id.text)

    }
}