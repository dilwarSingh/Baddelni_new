package com.baddelni.baddelni.home

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.home.MyProductsItem
import com.baddelni.baddelni.categories.ProductDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlin.reflect.KClass

class QuickAdapter(val context: Context, val list: List<MyProductsItem>) : RecyclerView.Adapter<QuickAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_quick, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, postion: Int) {
        val data = list[postion]

        val ro = RequestOptions()
                .centerCrop()
        ro.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        ro.placeholder(R.mipmap.ic_launcher)

        Glide.with(context)
                .applyDefaultRequestOptions(ro)
                .load(data.mainImage?.img!!)
                .into(holder.image)

        holder.text.text = data.name
        holder.text.setTextColor(context.resources.getColor(R.color.black))

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("pId", list[holder.adapterPosition].id as Int)
            intent.putExtra("isFav", list[holder.adapterPosition].fav.isFavorite())
            intent.putExtra("isMy", true)

            context.startActivity(intent)
        }


    }

    override fun getItemCount() = list.size
    class viewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val image = item.findViewById<ImageView>(R.id.image)
        val text = item.findViewById<TextView>(R.id.text)
    }
}

fun Context.startActivity(cls: KClass<*>) {
    startActivity(Intent(this, cls.java))
}