package com.baddelni.baddelni.account.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.FavResponse.DataItem
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.categories.ProductDetailActivity

class AdapterFavorites(val context: Context, val list: List<DataItem>) : RecyclerView.Adapter<AdapterFavorites.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_notifications, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dataItem = list[position]

        holder.image.setGlideImageNetworkPath(dataItem.mainImage?.img ?: "", false)
        holder.name.text = dataItem.name
        holder.description.text = dataItem.description

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("pId", list[holder.adapterPosition].id)
            intent.putExtra("isFav", true)

            context.startActivity(intent)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById(R.id.image) as ImageView
        val name = itemView.findViewById(R.id.name) as TextView
        val description = itemView.findViewById(R.id.description) as TextView
    }

}