package com.baddelni.baddelni.account.adapters

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.baddelni.baddelni.R
import com.baddelni.baddelni.account.ExchangeDetail
import com.baddelni.baddelni.account.pojos.pojoRequest
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.categories.ProductDetailActivity
import com.baddelni.baddelni.databinding.ItemRequestBinding

class AdapterRequests(val context: Context, val list: MutableList<pojoRequest>, val turnOnClicks: Boolean) : RecyclerView.Adapter<AdapterRequests.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_request, parent, false)
        val b = DataBindingUtil.bind<ItemRequestBinding>(view)
        return ViewHolder(b!!)
    }


    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, postion: Int) {

        val data = list[postion]

        holder.b.name.text = data.name
        holder.b.phone.text = data.phone
        holder.b.profileImage.setGlideImageNetworkPath(data.imageUrl)
        holder.b.image1.setGlideImageNetworkPath(data.img1,false)
        holder.b.image2.setGlideImageNetworkPath(data.img2,false)
        holder.b.location.text = data.loaction
        holder.b.requestText.text = data.statusText


        holder.b.pName.text = data.p1Name
        holder.b.pDesc.text = data.description1

        data.apply {
            if (statusText.equals(context.getString(R.string.refused), true)) {
                holder.b.requestText.background = context.resources.getDrawable(R.drawable.left_round_red, context.theme)
            } else if (statusText.equals(context.getString(R.string.accepted), true)) {
                holder.b.requestText.background = context.resources.getDrawable(R.drawable.left_round_green, context.theme)
            } else if (statusText.equals(context.getString(R.string.pending), true)) {
                holder.b.requestText.background = context.resources.getDrawable(R.drawable.left_round_yellow, context.theme)
            }
        }



        if (turnOnClicks) {
            if (data.statusText.equals(context.getString(R.string.pending))) {
                holder.itemView.setOnClickListener {
                    val intent = Intent(context, ExchangeDetail::class.java)
                    intent.putExtra("data", list[holder.adapterPosition])
                    context.startActivity(intent)
                }
            }
        } else {
            holder.itemView.setOnClickListener {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("pId", list[holder.adapterPosition].pId)
                intent.putExtra("isFav", list[holder.adapterPosition].isFav)

                context.startActivity(intent)
            }
        }
    }

    inner class ViewHolder(val b: ItemRequestBinding) : RecyclerView.ViewHolder(b.root)
}