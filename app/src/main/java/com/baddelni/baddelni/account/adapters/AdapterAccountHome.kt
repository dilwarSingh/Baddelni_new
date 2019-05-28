package com.baddelni.baddelni.account.adapters

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.baddelni.baddelni.R
import com.baddelni.baddelni.account.pojos.pojoAccountHome
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.categories.ImageListSliderActivity
import com.baddelni.baddelni.categories.ProductDetailActivity
import com.baddelni.baddelni.databinding.ItemAccountListBinding
import com.baddelni.baddelni.packageSection.EditPostActivity
import com.baddelni.baddelni.util.CommonObjects


class AdapterAccountHome(val context: Context, val list: MutableList<pojoAccountHome>, val favCount: MutableLiveData<Int>) : RecyclerView.Adapter<AdapterAccountHome.ViewHolder>() {

    private val co: CommonObjects by lazy { CommonObjects(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_account_list, parent, false)
        val b: ItemAccountListBinding = DataBindingUtil.bind(view)!!
        return ViewHolder(b)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, postion: Int) {

        viewHolder.onBind(list[postion])
        viewHolder.binding.root.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("pId", list[viewHolder.adapterPosition].pId)
            context.startActivity(intent)
        }
        viewHolder.binding.saveBt.visibility = View.INVISIBLE
        viewHolder.binding.shareBt.visibility = View.INVISIBLE
        viewHolder.binding.saveBt.width = 0
        viewHolder.binding.imageView8.visibility = View.INVISIBLE
        /*  viewHolder.binding.saveBt.setOnClickListener {
              co.MarkProductFav(list[viewHolder.adapterPosition].pId.toString(), favCount)
          }
          viewHolder.binding.shareBt.setOnClickListener {
              co.shareText(list[viewHolder.adapterPosition].pId, list[viewHolder.adapterPosition].name, list[viewHolder.adapterPosition].description)
          }*/
        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("pId", list[viewHolder.adapterPosition].pId)
            intent.putExtra("isMy", true)
            intent.putExtra("isFav", list[viewHolder.adapterPosition].isFav)

            context.startActivity(intent)
        }


    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemAccountListBinding) : RecyclerView.ViewHolder(binding.root) {


        fun onBind(data: pojoAccountHome) {
            binding.title.text = data.name
            binding.description.text = data.description

            binding.image.setGlideImageNetworkPath(data.productImage, false)
            binding.image.setOnClickListener {
                val imageUrl = data.productImage
                val intent = Intent(context, ImageListSliderActivity::class.java)

                val arr = arrayListOf(imageUrl)

                intent.putExtra("imageList", arr.toTypedArray())
                context.startActivity(intent)
            }

            binding.menuBt.setOnClickListener {
                setPopUpWindow(this@ViewHolder)
            }

            if (data.img1.isNotEmpty()) {
                binding.product1Img.setGlideImageNetworkPath(data.img1)
            }
            if (data.img2.isNotEmpty()) {
                binding.product2Img.setGlideImageNetworkPath(data.img2)
            }


        }

        private fun setPopUpWindow(viewHolder: ViewHolder) {
            val inflater: LayoutInflater = context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.popup, null)

            val mypopupWindow = PopupWindow(view, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true)
            mypopupWindow.showAsDropDown(binding.menuBt, 0, -binding.menuBt.height)


            val share = view.findViewById<TextView>(R.id.share)
            val save = view.findViewById<TextView>(R.id.save)
            val edit = view.findViewById<TextView>(R.id.edit)
            val republish = view.findViewById<TextView>(R.id.republish)
            val delete = view.findViewById<TextView>(R.id.delete)


            share.setOnClickListener {
                co.shareText(list[viewHolder.adapterPosition].pId, list[viewHolder.adapterPosition].name, list[viewHolder.adapterPosition].description)
                mypopupWindow.dismiss()
            }
            save.setOnClickListener {
                co.MarkProductFav(list[viewHolder.adapterPosition].pId.toString(), favCount)
                mypopupWindow.dismiss()
            }
            delete.setOnClickListener {
                co.DeleteProduct(list[viewHolder.adapterPosition].pId.toString(), this@AdapterAccountHome, viewHolder.adapterPosition, list)
                mypopupWindow.dismiss()
            }
            republish.setOnClickListener {
                co.RePublishProduct(list[viewHolder.adapterPosition].pId.toString(), this@AdapterAccountHome, viewHolder.adapterPosition, list)
                mypopupWindow.dismiss()
            }
            edit.setOnClickListener {
                val intent = Intent(context, EditPostActivity::class.java)
                intent.putExtra("pid", list[adapterPosition].pId)
                context.startActivity(intent)
                
                mypopupWindow.dismiss()
            }


        }
    }
}