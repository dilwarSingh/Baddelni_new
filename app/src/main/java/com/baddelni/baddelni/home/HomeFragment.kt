package com.baddelni.baddelni.home

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface.BOLD
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.MyAccount.MyAccount
import com.baddelni.baddelni.Response.home.AdsItem
import com.baddelni.baddelni.Response.home.HomeResponse
import com.baddelni.baddelni.Response.home.SlidesItem
import com.baddelni.baddelni.account.setGlideUserImage
import com.baddelni.baddelni.categories.SubCategoryActivity
import com.baddelni.baddelni.categories.pojoProductDetail
import com.baddelni.baddelni.chat.ChatActivity
import com.baddelni.baddelni.packageSection.CreatePostActivity
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.GlobalSharing
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_home_top.view.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ss.com.bannerslider.banners.Banner
import ss.com.bannerslider.banners.RemoteBanner
import ss.com.bannerslider.events.OnBannerClickListener

class HomeFragment : Fragment() {

    val co by lazy { CommonObjects(context!!) }
    lateinit var sliderList: List<SlidesItem>
    lateinit var ads: List<AdsItem>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatBt.visibility = GONE

        checkAppVersion(2.7F)

        if (co.getStringPrams() == AppConstants.GuestUserId) {
            //     quickView.visibility = GONE
            chatBt.visibility = GONE
            notificationsBt.visibility = GONE

            co.putStringPrams(AppConstants.IMG_URL, "")
            co.putStringPrams(AppConstants.PERSON_NAME, getString(R.string.guestUser))
        } else {
            accountData()

            profileImg.setOnClickListener {
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.putExtra("tabLocation", 1)
                startActivity(intent)
            }
        }
        profileImg.setGlideUserImage(co.getStringPrams(AppConstants.IMG_URL))
        username.text = co.getStringPrams(AppConstants.PERSON_NAME)




        fabCreatePost.setOnClickListener {
            if (co.getStringPrams() == AppConstants.GuestUserId) {
                co.showLoginDialog(getString(R.string.dont_have_permission))
            } else
                startActivity(Intent(context, CreatePostActivity::class.java))
        }

        chatBt.setOnClickListener {
            val intent = Intent(activity, ChatActivity::class.java)
            startActivity(intent)
        }

        notificationsBt.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("tabLocation", 3)
            startActivity(intent)
        }
        settingsBt.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("tabLocation", 4)
            startActivity(intent)
        }
        categoriesBt.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("tabLocation", 2)
            startActivity(intent)
        }



        quickInclude.apply {
            title.text = context.getString(R.string.my_ads)
            showAllText.setOnClickListener {
                (context as MainActivity).shiftFragment(R.id.accountBt)
            }
        }
        nearInclude.title.run {
            text = context.getString(R.string.topInYourCity)
            setTypeface(typeface, BOLD)

        }

        nearInclude.showAllText.setOnClickListener {
            val pList = emptyList<pojoProductDetail>().toMutableList()
            (topCity.adapter as TopCityAdapter).list.forEach { product ->
                val pojo = pojoProductDetail(product.id!!)

                pojo.name = product.name!!
                pojo.description = product.description!!
                pojo.imageUrl = product.mainImage?.img!!

                pojo.userImage = product.user?.img!!
                pojo.userEmail = product.name
                pojo.userName = product.user.name!!
                pojo.userId = product.user.id.toString()
                pojo.isFav = product.fav.isFavorite()

                pList.add(pojo)
            }

            val intent = Intent(context, SubCategoryActivity::class.java)
            intent.putExtra("title", "Top In Your City")
            intent.putExtra("list", pList.toTypedArray())
            intent.putExtra("isShowList", true)
            startActivity(intent)
        }
        latestInclude.title.run {
            text = context.getString(R.string.latestAds)
            setTypeface(typeface, BOLD)
        }
        latestInclude.showAllText.visibility = View.INVISIBLE

        intrestInclude.title.run {
            text = context.getString(R.string.topInYourIntrest)
            setTypeface(typeface, BOLD)

        }
        intrestInclude.showAllText.setOnClickListener {

            val pList = emptyList<pojoProductDetail>().toMutableList()
            (yourIntrest.adapter as TopIntrestAdapter).list.forEach { product ->
                val pojo = pojoProductDetail(product.id!!)

                pojo.name = product.name!!
                pojo.description = product.description!!
                pojo.imageUrl = product.mainImage?.img!!

                pojo.userImage = product.user?.img!!
                pojo.userEmail = product.name
                pojo.userName = product.user.name!!
                pojo.userId = product.user.id.toString()
                pojo.isFav = product.fav.isFavorite()

                pList.add(pojo)
            }


            val intent = Intent(context, SubCategoryActivity::class.java)
            intent.putExtra("title", "Top In Your City")
            intent.putExtra("list", pList.toTypedArray())
            intent.putExtra("isShowList", true)
            startActivity(intent)
        }
        /*   quickFirstView.name.run {
               text = "Create"
               setTextColor(resources.getColor(R.color.gradient2))

           }
           quickFirstView.setOnClickListener { startActivity(Intent(context, CreatePostActivity::class.java)) }
   */


    }

    private fun checkAppVersion(currentVersion: Float) {

        Api.getApi().checkVersion("a", currentVersion).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                val jo = JSONObject(response.body()?.string())

                if (jo.getString("code") != "0") {
                    val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                        when (which) {//Yes button clicked
                            DialogInterface.BUTTON_POSITIVE -> {
                                dialog.dismiss()

                                val appPackageName = context!!.getPackageName(); // package name of the app
                                try {
                                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (anfe: ActivityNotFoundException) {
                                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                            }

                        }
                    }

                    val builder = AlertDialog.Builder(context!!)
                    builder.setTitle(getString(R.string.app_name)).setMessage(
                            getString(R.string.newVersionAvaliable)
                    ).setPositiveButton(context!!.getString(R.string.update), dialogClickListener)
                    //.setNegativeButton(context.getString(R.string.no), dialogClickListener)

                    builder.setCancelable(false)

                    if (context is AppCompatActivity) {
                        if (!(context!! as AppCompatActivity).isFinishing)
                            builder.show()
                    }
                }


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {


            }
        })


    }

    private fun accountData() {
        Api.getApi().getAccountDetails(co.getStringPrams(), co.getAppLanguage().langCode()).enqueue(object : Callback<MyAccount> {

            override fun onResponse(call: Call<MyAccount>?, response: Response<MyAccount>?) {
                val body = response?.body()

                if (body != null) {
                    if (body.code!!.isSuccess()) {
                        body.data?.apply {
                            co.putStringPrams(AppConstants.PERSON_NAME, name)
                            co.putStringPrams(AppConstants.IMG_URL, img)
                            GlobalSharing.postCount = availableProduct
                            profileImg?.setGlideUserImage(co.getStringPrams(AppConstants.IMG_URL))
                            username?.text = co.getStringPrams(AppConstants.PERSON_NAME)

                        }
                    }
                }
            }

            override fun onFailure(call: Call<MyAccount>?, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        getHomeData()
    }

    private fun getHomeData() {

        co.showLoading()

        Api.getApi().getHomeData(co.getStringPrams(), co.getAppLanguage().langCode()).enqueue(object : Callback<HomeResponse> {

            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                val body = response.body()

                body?.apply {
                    if (code!!.isSuccess()) {


                        if (co.getStringPrams() != AppConstants.GuestUserId) {
                            quickRecycler.adapter = QuickAdapter(context!!, myProducts
                                    ?: emptyList())
                        }
                        GlobalSharing.topInYourCity = myCountryProducts
                        quickRecycler.adapter = QuickAdapter(context!!, myProducts!!)
                        topCity.adapter = TopCityAdapter(context!!, myCountryProducts!!)
                        latestRecycler.adapter = LatestAdapter(context!!, latestProducts!!)
                        yourIntrest.adapter = TopIntrestAdapter(context!!, interestedProducts!!)
                        sliderList = slides!!
                        this@HomeFragment.ads = ads!!
                        GlobalSharing.adUrls.addAll(ads)
                        createBanners()
                        createAdsSlider()

                        //      homeSlider.sliderAdapter = HomeSliderAdapter(slides!!)
                    }

                }
                co.hideLoading()
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })

    }

    private fun createAdsSlider() {
        val banners1: ArrayList<Banner> = arrayListOf()

        ads.forEach {
            val remoteBanner = RemoteBanner(it.img)
            banners1.add(remoteBanner)

        }
        adsRecycleSlider.setOnBannerClickListener(object : OnBannerClickListener {
            override fun onClick(position: Int) {
                val slidesItem = ads[position]
                openBrowserWithLink(slidesItem.link ?: "")
            }

        })
        adsRecycleSlider.setBanners(banners1)
    }

    private fun createBanners() {

        val banners: ArrayList<Banner> = arrayListOf()
        GlobalSharing.bannerAdds = sliderList

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
}