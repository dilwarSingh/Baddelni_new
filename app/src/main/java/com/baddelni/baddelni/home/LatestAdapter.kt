package com.baddelni.baddelni.home

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.home.LatestProductsItem
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.categories.ProductDetailActivity
import com.baddelni.baddelni.databinding.ItemAccountListBinding
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.FavoriteCode
import com.baddelni.baddelni.util.LikeListener

class LatestAdapter(val context: Context, val list: List<LatestProductsItem>) : RecyclerView.Adapter<LatestAdapter.viewHolder>() {
    private val co: CommonObjects by lazy { CommonObjects(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_account_list, parent, false)
        return viewHolder(DataBindingUtil.bind(view)!!)
    }

    override fun onBindViewHolder(holder: viewHolder, postion: Int) {
        val data = list[postion]

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("pId", list[holder.adapterPosition].id)
            intent.putExtra("isFav", list[holder.adapterPosition].fav.isFavorite())

            context.startActivity(intent)
        }
        holder.binding.saveBt.isChecked = data.fav.isFavorite() ?: false

        holder.binding.saveBt.setOnClickListener {
            co.MarkProductFav(list[holder.adapterPosition].id.toString(), null, object : LikeListener {
                override fun liked() {
                    list[holder.adapterPosition].fav = FavoriteCode.FAVORITE
                    notifyItemChanged(holder.adapterPosition)
                }

                override fun unLiked() {
                    list[holder.adapterPosition].fav = FavoriteCode.NotFavorite
                    notifyItemChanged(holder.adapterPosition)

                }

            })
        }
        holder.onBind(data)
    }

    override fun getItemCount() = list.size
    inner class viewHolder(val binding: ItemAccountListBinding) : RecyclerView.ViewHolder(binding.root) {


        fun onBind(data: LatestProductsItem) {
            binding.title.text = data.name
            binding.description.text = data.description

            binding.shareBt.setOnClickListener {
                co.shareText(data.id!!, data.name ?: "", data.description ?: "")
            }


            binding.image.setGlideImageNetworkPath(data.mainImage?.img ?: "", false)

            if (!data.replacements.isNullOrEmpty()) {
                if (data.replacements.size > 1) {
                    binding.product1Img.setGlideImageNetworkPath(data.replacements[0]?.img ?: "")
                    binding.product2Img.setGlideImageNetworkPath(data.replacements[1]?.img ?: "")
                } else {
                    binding.product2Img.setGlideImageNetworkPath(data.replacements[0]?.img ?: "")
                }

            }
        }

    }
}