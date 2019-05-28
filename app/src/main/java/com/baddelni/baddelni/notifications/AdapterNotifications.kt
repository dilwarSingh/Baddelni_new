package com.baddelni.baddelni.notifications

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.Notification.NotificationsItem
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.categories.ProductDetailActivity

class AdapterNotifications(val context: Context, val list: List<NotificationsItem>) : RecyclerView.Adapter<AdapterNotifications.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNotifications.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_notifications, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: AdapterNotifications.ViewHolder, position: Int) {

        val dataItem = list[position]

        holder.image.setGlideImageNetworkPath(dataItem.product?.mainImage?.img ?: "", false)
        holder.name.text = dataItem.product?.name
        holder.description.text = dataItem.text

        holder.itemView.setOnClickListener {
            if (dataItem.product != null && dataItem.productId != null) {
                if (dataItem.productId > 0) {
                    val intent = Intent(context, ProductDetailActivity::class.java)
                    intent.putExtra("pId", dataItem.productId)
                    intent.putExtra("isFav", false)
                    context.startActivity(intent)
                }
            }

        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById(R.id.image) as ImageView
        val name = itemView.findViewById(R.id.name) as TextView
        val description = itemView.findViewById(R.id.description) as TextView
    }

}