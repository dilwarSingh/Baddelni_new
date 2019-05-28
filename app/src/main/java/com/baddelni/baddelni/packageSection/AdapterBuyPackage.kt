package com.baddelni.baddelni.packageSection

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.PayPackages.PackagesItem
import com.baddelni.baddelni.databinding.LayoutPackageViewBinding

class AdapterBuyPackage(val context: Context, val list: List<PackagesItem>)
    : RecyclerView.Adapter<AdapterBuyPackage.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterBuyPackage.viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_package_view, parent, false)

        val bind = DataBindingUtil.bind<LayoutPackageViewBinding>(view)

        return viewHolder(bind!!)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: AdapterBuyPackage.viewHolder, postion: Int) {

        holder.onBind(list[postion])

    }


    inner class viewHolder(val b: LayoutPackageViewBinding) : RecyclerView.ViewHolder(b.root) {

        fun onBind(data: PackagesItem) {


            b.packageName.text = data.name//
            b.validity.text = context.getString(R.string.validFor) + " ${data.time} " + context.getString(R.string.days)
            b.posts.text = "${data.count} " + context.getString(R.string.posts)
            b.price.text = "${data.price}" + context.getString(R.string.currencyKd)

            b.buyPosts.setOnClickListener {
                val intent = Intent(context, PaymentWebViewActivity::class.java)
                intent.putExtra("payId", data.id)
                context.startActivity(intent)
            }
            b.executePendingBindings()
        }


    }
}