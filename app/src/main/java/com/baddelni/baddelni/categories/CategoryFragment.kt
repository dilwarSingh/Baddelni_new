package com.baddelni.baddelni.categories

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baddelni.baddelni.App
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.categories.categoriesNew.CategoriesItem
import com.baddelni.baddelni.Response.categories.categoriesNew.CategoriesResponse
import com.baddelni.baddelni.Response.home.SlidesItem
import com.baddelni.baddelni.packageSection.CreatePostActivity
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.fragment_categories.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ss.com.bannerslider.banners.Banner
import ss.com.bannerslider.banners.RemoteBanner


class CategoryFragment : Fragment() {
    val co: CommonObjects by lazy { CommonObjects(context!!) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subCatRecycler.isNestedScrollingEnabled = false
        backBt.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("tabLocation", 0)
            startActivity(intent)
        }

        fabCreatePost.setOnClickListener {
            if (co.getStringPrams() == AppConstants.GuestUserId) {
                co.showLoginDialog(getString(R.string.dont_have_permission))
            } else
                startActivity(Intent(context, CreatePostActivity::class.java))
        }

        getCategoriesData(App.fromSelling)

    }

    private fun createBanners(sliderList: List<SlidesItem>) {
        val banners: ArrayList<Banner> = arrayListOf()

        sliderList.forEach {
            val remoteBanner = RemoteBanner(it.img)
            banners.add(remoteBanner)
        }
        adsSlider.setOnBannerClickListener { position ->
            val slidesItem = sliderList[position]
            openBrowserWithLink(slidesItem.link ?: "")
        }

        adsSlider.setBanners(banners)


    }

    private fun openBrowserWithLink(url: String) {
        if (url.length < 5) {
            co.showToastDialog(detail = getString(R.string.malformedLink), yesNo = null)
            return
        }
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    fun getCategoriesData(isSelling: Boolean) {
        co.showLoading()

        val api = if (isSelling)
            Api.getApi().getCategoriesAndSelling(co.getAppLanguage().langCode(), "1")
        else
            Api.getApi().getCategoriesAndSubCats(co.getAppLanguage().langCode())

        api.enqueue(object : Callback<CategoriesResponse> {

            override fun onResponse(call: Call<CategoriesResponse>?, response: Response<CategoriesResponse>?) {
                response?.body()?.apply {
                    if (code!!.isSuccess()) {
                        categories.add(0, CategoriesItem("", "", "", null, "", getString(R.string.all), -1, getString(R.string.all)))
                        recycler.adapter = AdapterCategories(context!!, categories as List<CategoriesItem>)
                        subCatRecycler.adapter = CategorySubCatAdapter(context!!, data)

                        createBanners(sliders)
                    }
                }
                co.hideLoading()
            }

            override fun onFailure(call: Call<CategoriesResponse>?, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })

    }
}