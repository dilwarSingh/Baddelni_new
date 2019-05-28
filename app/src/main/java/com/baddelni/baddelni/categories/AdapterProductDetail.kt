package com.baddelni.baddelni.categories

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.baddelni.baddelni.R
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.LikeListener

class AdapterProductDetail(val context: Context, val list: MutableList<pojoProductDetail>, val isMyProduct: Boolean) : RecyclerView.Adapter<AdapterProductDetail.ViewHolder>() {

    private val co: CommonObjects by lazy { CommonObjects(context) }


    val LONG_VIEW = 1
    val SIMPLE_VIEW = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
                if (viewType == LONG_VIEW) {
                    LayoutInflater.from(context).inflate(R.layout.item_product_detail_long, parent, false)
                } else {
                    LayoutInflater.from(context).inflate(R.layout.item_product_detail_simple, parent, false)
                }
        return ViewHolder(view)

    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) LONG_VIEW else SIMPLE_VIEW
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]

        holder.image.setGlideImageNetworkPath(data.imageUrl, false)
        holder.title.text = data.name
        holder.description.text = data.description


        if (position == 0) {
            holder.itemView.findViewById<ImageButton>(R.id.shareBt).setOnClickListener {
                co.shareText(data.productId, data.name, data.description)
            }
            val likeBt = holder.itemView.findViewById<CheckedTextView>(R.id.likeBt)
            if (isMyProduct) {
                likeBt.visibility = View.INVISIBLE
                holder.itemView.findViewById<ImageButton>(R.id.imageButton4).visibility = View.INVISIBLE
            } else {
                likeBt.isChecked = list[0].isFav
                likeBt.setOnClickListener {
                    co.MarkProductFav(data.productId.toString(), null, object : LikeListener {
                        override fun liked() {
                            list[0].isFav = true
                            notifyItemChanged(0)
                        }

                        override fun unLiked() {
                            list[0].isFav = false
                            notifyItemChanged(0)

                        }

                    })
                }
            }
        } else {
            holder.image.setOnClickListener {
                val imageUrl = list[holder.adapterPosition].imageUrl
                val intent = Intent(context, ImageListSliderActivity::class.java)

                val arr = arrayListOf(imageUrl)

                intent.putExtra("imageList", arr.toTypedArray())
                context.startActivity(intent)
            }
        }

        //   holder.itemView.setOnClickListener { context.startActivity(Intent(context, ExchangeDetail::class.java)) }


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        //    val shareBt: TextView = itemView.findViewById(R.id.shareBt)

    }
}