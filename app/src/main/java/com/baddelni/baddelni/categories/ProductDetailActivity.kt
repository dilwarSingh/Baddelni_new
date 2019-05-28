package com.baddelni.baddelni.categories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.categories.SingleProductResponse.SingleProductResponse
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.packageSection.MakeOrderActivity
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.activity_product_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ss.com.bannerslider.banners.Banner
import ss.com.bannerslider.banners.RemoteBanner


class ProductDetailActivity : AppCompatActivity() {

    private var pId: Int? = null
    private var isMyProduct = false
    private var isFav = false
    private var sliderArray: Array<String>? = null

    val co: CommonObjects by lazy { CommonObjects(this) }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        pId = intent?.extras?.getInt("pId")!!
        isFav = intent?.extras?.getBoolean("isFav", false)!!
        isMyProduct = intent?.extras?.getBoolean("isMy", false)!!

        /*   if (pId == null) {
               val appLinkIntent = intent
               val appLinkAction = appLinkIntent.action
               val appLinkData = appLinkIntent.data

               if (Intent.ACTION_VIEW == appLinkAction) {
                   appLinkData?.lastPathSegment
               }



           }*/
        if (isMyProduct) {
            phoneIcon.visibility = View.INVISIBLE
            orderBt.visibility = View.INVISIBLE
        }

        backBt.setOnClickListener { finish() }
        orderBt.setOnClickListener {
            if (co.getStringPrams() == AppConstants.GuestUserId) {
                co.showLoginDialog(getString(R.string.dont_have_permission))
            } else {
                val intent = Intent(this, MakeOrderActivity::class.java)
                intent.putExtra("pId", pId!!)
                startActivity(intent)
            }
        }


        getData()
    }

    private fun getData() {
        co.showLoading()

        Api.getApi().singleProduct(pId!!, co.getAppLanguage().langCode()).enqueue(object : Callback<SingleProductResponse> {

            override fun onResponse(call: Call<SingleProductResponse>, response: Response<SingleProductResponse>) {
                val body = response.body()
                co.hideLoading()
                body?.apply {
                    if (code!!.isSuccess()) {


                        product?.user.apply {

                            //          bgImage.setGlideImageNetworkPath(product?.mainImage?.img ?: "", false)
                            profileImage.setGlideImageNetworkPath(this?.img ?: "")
                            username.text = this?.name ?: ""

                            phoneIcon.setOnClickListener {
                                val callNo = if (product?.phone.isNullOrEmpty()) {
                                    this?.phone
                                } else {
                                    product?.phone
                                }
                                co.fireCallIntent(callNo)
                            }

                        }

                        val list = mutableListOf<pojoProductDetail>()

                        val pojo = pojoProductDetail(product?.id!!)

                        pojo.imageUrl = product.mainImage?.img!!
                        pojo.name = product.name!!
                        pojo.description = product.description!!
                        pojo.isFav = this@ProductDetailActivity.isFav

                        list.add(pojo)

                        product.replacements?.forEach {

                            val pojo1 = pojoProductDetail(it.id!!)
                            pojo1.imageUrl = it.img!!
                            pojo1.name = it.name!!
                            pojo1.description = it.description!!

                            list.add(pojo1)

                        }
                        recycler.adapter = AdapterProductDetail(this@ProductDetailActivity, list, isMyProduct)

                        val sliderList = mutableListOf<String>()
                        sliderList.add(product.mainImage.img)

                        product.subImages?.forEach {
                            sliderList.add(it.img ?: "")
                        }

                        makeSlider(sliderList)


                    }

                }


            }

            override fun onFailure(call: Call<SingleProductResponse>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })


    }

    private fun makeSlider(sliderList: MutableList<String>) {
        sliderArray = sliderList.toTypedArray()

        val banners1: ArrayList<Banner> = arrayListOf()

        for (imgPath in sliderArray!!) {
            val remoteBanner = RemoteBanner(imgPath)
            banners1.add(remoteBanner)
        }


/*  slider.setOnBannerClickListener(object : OnBannerClickListener {
              override fun onClick(position: Int) {
                  val intent = Intent(this@ProductDetailActivity, ImageListSliderActivity::class.java)
                  intent.putExtra("imageList", sliderArray)
                  startActivity(intent)
              }

          })*/
        zoomImg.setOnClickListener {
            val intent = Intent(this@ProductDetailActivity, ImageListSliderActivity::class.java)
            intent.putExtra("imageList", sliderArray)
            startActivity(intent)
        }

        slider.setBanners(banners1)


    }


}