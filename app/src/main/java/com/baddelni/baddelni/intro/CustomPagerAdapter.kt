package com.baddelni.baddelni.intro

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.home.SlidesItem
import com.baddelni.baddelni.util.CommonObjects
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CustomPagerAdapter(private val mContext: Context, val imagesList: List<Int>?, val networkImage: SlidesItem? = null) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(R.layout.view_red, collection, false) as ViewGroup

        val imageView = layout.findViewById<ImageView>(R.id.image)
        if (imagesList != null) {
            val image = getImage(position)
            imageView.setImageDrawable((collection.context).resources.getDrawable(image))
        } else {
            Glide.with(imageView)
                    .setDefaultRequestOptions(
                            RequestOptions()
                                    .centerInside()
                    )
                    .load(networkImage?.img)
                    .into(imageView)
            layout.setOnClickListener {
                openBrowserWithLink(networkImage?.link ?: "")

            }
        }

        collection.addView(layout)
        return layout
    }

    private fun openBrowserWithLink(url: String) {
        if (url.length < 5) {
            CommonObjects(mContext).showToastDialog(detail = mContext.getString(R.string.malformedLink), yesNo = null)
            return
        }
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        mContext.startActivity(browserIntent)
    }

    private fun getImage(postition: Int): Int = imagesList?.get(postition)!!


    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return imagesList?.size ?: 1
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getPageTitle(position: Int): CharSequence? {
        //  val customPagerEnum = ModelObject.values()[position]
        return "Ant Title"// mContext.getString(customPagerEnum.titleResId)
    }

}