package com.baddelni.baddelni.categories

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.categories.categoriesNew.CategoriesItem
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.util.CommonObjects

class AdapterCategories(private var context: Context, val list: List<CategoriesItem>) : RecyclerView.Adapter<AdapterCategories.viewHolder>() {
    val co: CommonObjects by lazy { CommonObjects(context) }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): viewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_categories_new, viewGroup, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: viewHolder, position: Int) {

        val category = list[position]
        viewHolder.text.text = if (co.getAppLanguage().isEnglish()) category.category else category.categoryAr
        category.img?.let { viewHolder.image.setGlideImageNetworkPath(it) }

        if (position > 0) {
            viewHolder.itemView.setOnClickListener {
                val categoriesItem = list[viewHolder.adapterPosition]
                val intent = Intent(context, SubCategoryActivity::class.java)
                //   intent.putParcelableArrayListExtra("subCats", categoriesItem.subCategory)
                intent.putExtra("title", categoriesItem.category)
                intent.putExtra("logo", categoriesItem.img)
                intent.putExtra("subList", categoriesItem.subCategory)
                intent.putExtra("id", categoriesItem.id)

                context.startActivity(intent)
            }
        }

    }

    override fun getItemCount() = list.size

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var text: TextView = itemView.findViewById(R.id.text)

    }
}
