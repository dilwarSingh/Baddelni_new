package com.baddelni.baddelni.loginRegister.interests

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baddelni.baddelni.R
import com.baddelni.baddelni.account.setGlideImageNetworkPath

class AdapterInterests(private var titles: List<IntrestsPojo>, private var context: Context) : RecyclerView.Adapter<AdapterInterests.viewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): viewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_categories, viewGroup, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: viewHolder, position: Int) {

        val pojo = titles[position]

        viewHolder.text.text = pojo.catName
        viewHolder.image.setGlideImageNetworkPath(pojo.imageUrl)

        if (pojo.status) {
            viewHolder.image.background = context.resources.getDrawable(R.drawable.brown_bg)
        } else {
            viewHolder.image.background = null
        }
        viewHolder.image.setOnClickListener {
            titles[viewHolder.adapterPosition].status = !titles[viewHolder.adapterPosition].status
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var text: TextView = itemView.findViewById(R.id.text)

    }
}
