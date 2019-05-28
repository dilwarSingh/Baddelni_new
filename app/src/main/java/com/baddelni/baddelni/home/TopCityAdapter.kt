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
import com.baddelni.baddelni.Response.home.MyCountryProductsItem
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.categories.ProductDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class TopCityAdapter(val context: Context, val list: List<MyCountryProductsItem>) : RecyclerView.Adapter<TopCityAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sub_categories, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, postion: Int) {
        val data = list[postion]
        // holder.image.setGlideImageNetworkPath(data.mainImage?.img!!, false)
        val ro = RequestOptions()
                .centerCrop()
        ro.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        ro.placeholder(R.mipmap.ic_launcher)

        Glide.with(context)
                .applyDefaultRequestOptions(ro)
                .load(data.mainImage?.img!!)
                .into(holder.image)


        holder.userImage.setGlideImageNetworkPath(data.user?.img ?: "")
        holder.text.text = data.user?.name
        holder.description.text = data.name


        holder.itemView.setOnClickListener {

            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("pId", list[holder.adapterPosition].id)
            intent.putExtra("isFav", list[holder.adapterPosition].fav.isFavorite())

            context.startActivity(intent)
        }

    }

    override fun getItemCount() = if (list.size > 6) 6 else list.size

    class viewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var userImage: ImageView = itemView.findViewById(R.id.userImage)
        var text: TextView = itemView.findViewById(R.id.text)
        var description: TextView = itemView.findViewById(R.id.description)
    }
}