package com.baddelni.baddelni.categories

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.baddelni.baddelni.R
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.util.CommonObjects

class AdapterSubCategory(val context: Context) : RecyclerView.Adapter<AdapterSubCategory.ViewHolder>(), Filterable {

    var filteredList: List<pojoProductDetail> = emptyList()
    var list: List<pojoProductDetail> = emptyList()

    fun setDataList(listt: List<pojoProductDetail>) {
        list = listt
        filteredList = listt
        notifyDataSetChanged()
    }

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var fList = emptyList<pojoProductDetail>().toMutableList()

            if (constraint.toString().isEmpty()) {
                fList = list.toMutableList()
            }
            val subId = constraint.toString().toInt()



            if (subId == -1) {
                fList = list.toMutableList()
            } else {

                list.forEach {
                    if (it.subCatId == subId) {
                        fList.add(it)
                    }

                }

            }


            val filterResults = FilterResults()
            filterResults.count = fList.size
            filterResults.values = fList
            return filterResults

        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            filteredList = results.values as List<pojoProductDetail>


            (context as SubCategoryActivity).isListEmpty(filteredList)

            notifyDataSetChanged()
        }

    }

    val co: CommonObjects by lazy { CommonObjects(context) }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sub_categories, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = filteredList.size

    override fun onBindViewHolder(holder: ViewHolder, postion: Int) {
        val data = filteredList[postion]

        holder.image.setGlideImageNetworkPath(data.imageUrl, false)
        holder.userImage.setGlideImageNetworkPath(data.userImage)
        holder.text.text = data.userName
        holder.description.text = data.userEmail

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("pId", filteredList[holder.adapterPosition].productId)
            intent.putExtra("isFav", filteredList[holder.adapterPosition].isFav)

            context.startActivity(intent)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var userImage: ImageView = itemView.findViewById(R.id.userImage)
        var text: TextView = itemView.findViewById(R.id.text)
        var description: TextView = itemView.findViewById(R.id.description)
    }
}