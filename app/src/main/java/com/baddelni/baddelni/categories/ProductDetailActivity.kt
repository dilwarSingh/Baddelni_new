package com.baddelni.baddelni.categories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.Countries.Countries
import com.baddelni.baddelni.Response.Countries.CountriesItem
import com.baddelni.baddelni.Response.categories.SingleProductResponse.SingleProductResponse
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.packageSection.MakeOrderActivity
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.activity_product_detail.*
import okhttp3.ResponseBody
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Hours
import org.joda.time.Minutes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ss.com.bannerslider.banners.Banner
import ss.com.bannerslider.banners.RemoteBanner
import java.util.*


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

        getCountyData()
    }

    private fun getCountyData() {


        Api.getApi().getCountries(co.getAppLanguage().langCode()).enqueue(object : Callback<Countries> {

            override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                val body = response.body()

                body?.apply {
                    if (code!!.isSuccess()) {
                        getData(countryList!!)
                    }
                }
            }

            override fun onFailure(call: Call<Countries>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
            }
        })
    }

    private fun getData(countryList: List<CountriesItem>) {
        co.showLoading()

        Api.getApi().singleProduct(pId!!, co.getAppLanguage().langCode()).enqueue(object : Callback<SingleProductResponse> {

            override fun onResponse(call: Call<SingleProductResponse>, response: Response<SingleProductResponse>) {
                val body = response.body()
                co.hideLoading()
                body?.apply {
                    if (code!!.isSuccess()) {

                        hitProductViewApi(product?.id)

                        var currency = "KWD"

                        countryList.forEach {
                            if (it.id == product?.country_id) {
                                currency = if (it.id == 2) {
                                    "EGP"
                                } else if (it.id == 3) {
                                    "SR"
                                } else {
                                    "KWD"
                                }
                                return@forEach
                            }
                        }

                        if (product?.price?.toInt() ?: 0 > 0) {
                            priceTag.text =
                                    "${getString(R.string.price)} ${product?.price?.toInt()
                                            ?: 0} $currency"
                        }



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

                     /*   val time = getTimeDetail(this@ProductDetailActivity, product.timestamp)
                        textView28.text = time
*/
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

    private fun getTimeDetail(context: Context, createdAt: Long?): String {
        //published_at": "2019-08-09 03:55:35

        /*   val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
           val dateStramp = formatter.parse(createdAt).time
   */
        val current = Date().time

        val dt1 = DateTime(createdAt)
        val dt2 = DateTime(current)

        //val month = Months.monthsBetween(dt1, dt2).months
        val days = Days.daysBetween(dt1, dt2).days
        val hours = Hours.hoursBetween(dt1, dt2).hours % 24
        val mints = Minutes.minutesBetween(dt1, dt2).minutes % 60

        val time = when {
            days > 0 -> "$days ${context.getString(R.string.days)}"
            hours > 0 -> "$hours ${context.getString(R.string.hours)}"
            else -> "$mints ${context.getString(R.string.minutes)}"
        }

        return time.toLowerCase()
    }

    private fun hitProductViewApi(id: Int?) {

        Api.getApi().markProductView(co.getStringPrams(), id ?: 0).enqueue(
                object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    }

                }
        )


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