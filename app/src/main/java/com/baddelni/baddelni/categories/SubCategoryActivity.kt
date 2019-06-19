package com.baddelni.baddelni.categories

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.View.GONE
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.categories.categoriesNew.SubCategoryItem
import com.baddelni.baddelni.Response.categories.product.CategoryProducts
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.packageSection.CreatePostActivity
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.GlobalSharing
import kotlinx.android.synthetic.main.activity_category_detail.*
import kotlinx.android.synthetic.main.layout_home_top.view.title
import kotlinx.android.synthetic.main.layout_home_top_new.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ss.com.bannerslider.banners.Banner
import ss.com.bannerslider.banners.RemoteBanner

class SubCategoryActivity : AppCompatActivity() {

    val co: CommonObjects by lazy { CommonObjects(this) }
    private var categoryId = 0
    private var catTitle = ""
    private var logo = ""

    lateinit var productListAdapter: AdapterSubCategory


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    private fun createBanners() {

        val banners: ArrayList<Banner> = arrayListOf()
        val sliderList = GlobalSharing.bannerAdds
        recycler.setNestedScrollingEnabled(false)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detail)
        createBanners()
        //     include.text.text = getString(R.string.products)
        backBt.setOnClickListener { finish() }
        /* include.newPostBt.setOnClickListener {
             if (co.getStringPrams() == AppConstants.GuestUserId) {
                 co.myToast(getString(R.string.dont_have_permission))
             } else {
                 startActivity(Intent(this, CreatePostActivity::class.java))
             }
         }*/

        fabCreatePost.setOnClickListener {
            if (co.getStringPrams() == AppConstants.GuestUserId) {
                co.showLoginDialog(getString(R.string.dont_have_permission))
            } else
                startActivity(Intent(this, CreatePostActivity::class.java))
        }
        //   val parcelableArrayList = intent?.extras?.getParcelableArrayList<SubCategoryItem>("subCats")
        //      heading.text = intent?.extras?.getString("title", "")
        productListAdapter = AdapterSubCategory(this@SubCategoryActivity)
        recycler.adapter = productListAdapter


        if (intent?.extras?.getBoolean("isShowList", false) == true) {
            val showList = intent?.extras?.getSerializable("list") as Array<pojoProductDetail>
            //     include.count.text = showList.size.toString()
            include4.visibility = GONE
            showThisList(showList.toMutableList())

        } else {

            categoryId = intent?.extras?.getInt("id", 0)!!
            catTitle = intent?.extras?.getString("title", "Title")!!
            logo = intent?.extras?.getString("logo", "")!!
            val list = intent?.extras?.getSerializable("subList") as ArrayList<SubCategoryItem>

            include4.title.text = catTitle
            include4.logo.setGlideImageNetworkPath(logo, false)
            include4.showAllText.text = "${list.size} ${getString(R.string.new_add)}"

            val mList: MutableList<SubCategoryItem> = list
            mList.add(0, SubCategoryItem(categoryId, "", getString(R.string.all), "", "", logo, -1))
            catRecycler.adapter = SubCatRecyclerAdapter(this, mList
                    , productListAdapter)

            getData()

        }


    }


    private fun getData() {
        co.showLoading()

        Api.getApi().getCategoriesProduct(co.getStringPrams(), categoryId, co.getAppLanguage().langCode()).enqueue(object : Callback<CategoryProducts> {

            override fun onResponse(call: Call<CategoryProducts>, response: Response<CategoryProducts>) {
                val body = response.body()

                body?.apply {
                    if (code!!.isSuccess()) {

                        category?.apply {
                            val list = mutableListOf<pojoProductDetail>()

                            list.addAll(product?.map {
                                val data = pojoProductDetail(it.id!!)
                                data.name = it.name!!
                                data.description = it.description!!
                                data.imageUrl = it.mainImage?.img!!

                                data.userImage = it.user?.img!!
                                data.userEmail = it.name
                                data.userName = it.user.name!!
                                data.userId = it.user.id.toString()
                                data.isFav = it.fav.isFavorite()

                                if (it.sub_categories?.isNotEmpty() ?: false)
                                    data.subCatId = it.sub_categories?.get(0)?.subCategoryId!!


                                data
                            }!!.toMutableList()
                            )
                            //     include.count.text = list.size.toString()

                            showThisList(list)
                        }

                    }

                }
                co.hideLoading()
            }

            override fun onFailure(call: Call<CategoryProducts>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })


    }

    public fun isListEmpty(list: List<pojoProductDetail>) {
        if (list.isEmpty()) {
            noData.visibility = View.VISIBLE
        } else {
            noData.visibility = View.GONE
        }
    }

    private fun showThisList(list: MutableList<pojoProductDetail>) {

        if (list.isEmpty()) {
            noData.visibility = View.VISIBLE
        } else {
            noData.visibility = View.GONE
            productListAdapter.setDataList(list)
        }
    }
}