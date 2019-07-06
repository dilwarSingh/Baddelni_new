package com.baddelni.baddelni.account

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.MyAccount.MyAccount
import com.baddelni.baddelni.account.adapters.AdapterAccountHome
import com.baddelni.baddelni.account.pojos.pojoAccountHome
import com.baddelni.baddelni.account.pojos.pojoCats
import com.baddelni.baddelni.chat.ChatActivity
import com.baddelni.baddelni.packageSection.CreatePostActivity
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.GlobalSharing
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.layout_account.*
import kotlinx.android.synthetic.main.layout_account.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ss.com.bannerslider.banners.Banner
import ss.com.bannerslider.banners.RemoteBanner
import ss.com.bannerslider.events.OnBannerClickListener
import java.io.File

class AccountHomeFragment : Fragment() {

    lateinit var activity: AppCompatActivity
    val co: CommonObjects by lazy { CommonObjects(context!!) }
    val favCount: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    private fun createAdsSlider() {
        val banners1: ArrayList<Banner> = arrayListOf()

        GlobalSharing.adUrls.forEach {
            val remoteBanner = RemoteBanner(it.img)
            banners1.add(remoteBanner)

        }
        addView.setOnBannerClickListener(object : OnBannerClickListener {
            override fun onClick(position: Int) {
                val slidesItem = GlobalSharing.adUrls[position]
                openBrowserWithLink(slidesItem.link ?: "")
            }

        })
        addView.setBanners(banners1)
    }

    private fun openBrowserWithLink(url: String) {
        if (url.length < 5) {
            co.showToastDialog(detail = getString(R.string.malformedLink), yesNo = null)
            return
        }
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity = context as AppCompatActivity
        chatBt.visibility = GONE

        if (co.getStringPrams() == AppConstants.GuestUserId) {
            chatBt.visibility = GONE
            accountBt2.visibility = GONE
        }


        createAdsSlider()

        profileSection.chatBt.setOnClickListener {
            val intent = Intent(activity, ChatActivity::class.java)
            startActivity(intent)
        }
        profileSection.backBt.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("tabLocation", 0)
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
        fabCreatePost.setOnClickListener {
            if (co.getStringPrams() == AppConstants.GuestUserId) {
                co.showLoginDialog(getString(R.string.dont_have_permission))
            } else
                startActivity(Intent(context, CreatePostActivity::class.java))
        }


        profileSection.requestLL.setOnClickListener {
            val intent = Intent(activity, RequestsActivity::class.java)
            intent.putExtra("fromScreen", "request")
            intent.putExtra("requestCount", profileSection.requests.text)
            startActivity(intent)
        }
        profileSection.myrequestLL.setOnClickListener {
            val intent = Intent(activity, RequestsActivity::class.java)
            intent.putExtra("requestCount", profileSection.requests.text)
            intent.putExtra("fromScreen", "myRequest")
            startActivity(intent)
        }
        //favCount      profileSection.avaliablePostLL.setOnClickListener { startActivity(Intent(activity, BuyPackageActivity::class.java)) }
        profileSection.likesLL.setOnClickListener {
            val intent = Intent(activity, FavoritesActivity::class.java)
            intent.putExtra("favCount", profileSection.likes.text)
            startActivity(intent)
        }
        newPostBt.setOnClickListener {
            if (co.getStringPrams() == AppConstants.GuestUserId) {
                co.showLoginDialog(getString(R.string.dont_have_permission))
            } else
                startActivity(Intent(activity, CreatePostActivity::class.java))
        }

        favCount.observe(this, Observer {
            if (it != null) {
                profileSection.likes.text = it.toString()

            }
        })

    }

    override fun onResume() {
        super.onResume()

        profileSection.avaliableBost.text = ""
        profileSection.requests.text = ""
        profileSection.myrequests.text = ""
        profileSection.likes.text = ""

        accountData()

    }

    private fun accountData() {
        co.showLoading()
        Api.getApi().getAccountDetails(co.getStringPrams(), co.getAppLanguage().langCode()).enqueue(object : Callback<MyAccount> {

            override fun onResponse(call: Call<MyAccount>?, response: Response<MyAccount>?) {
                val body = response?.body()

                if (body != null) {
                    if (body.code!!.isSuccess()) {
                        body.data?.apply {

                            co.putStringPrams(AppConstants.AVALIABLE_POSTS, availableProduct.toString())
                            profileSection.avaliableBost.text = availableProduct.toString()
                            profileSection.requests.text = countRequests.toString()
                            //   profileSection.myrequests.text = countRequests.toString()
                            profileSection.likes.text = countFavourites.toString()
                            favCount.value = countFavourites


                            co.putStringPrams(AppConstants.PERSON_NAME, name)
                            co.putStringPrams(AppConstants.IMG_URL, img)

                            profileSection.text.text = name
                            profileSection.profileImage.setGlideImageNetworkPath(img)
                            GlobalSharing.count_myrequest = count_myrequest


                            profileSection.myrequests.text = count_myrequest.toString()


                            profileSection.accountBt2.setOnClickListener {
                                val intent = Intent(activity, EditProfileActivity::class.java)

                                intent.putExtra("name", name)
                                intent.putExtra("email", email)
                                intent.putExtra("countryId", country?.id)
                                intent.putExtra("countryName", country?.country)
                                intent.putExtra("phone", phone)
                                intent.putExtra("imageUrl", img)
                                intent.putExtra("description", about)


                                val dataList = emptyList<pojoCats>().toMutableList()

                                category.forEach { data ->
                                    val pojoCats = pojoCats(data.id.toString())
                                    pojoCats.name = data.category
                                    pojoCats.imageUrl = data.img

                                    dataList.add(pojoCats)
                                }
                                intent.putExtra("cats", dataList.toTypedArray())



                                startActivity(intent)
                            }



                            if (!product.isNullOrEmpty()) {

                                val productList: MutableList<pojoAccountHome> = arrayListOf()
                                product.forEach {
                                    val listObj = pojoAccountHome(it!!.id)
                                    listObj.name = it.name!!
                                    listObj.description = it.description!!
                                    listObj.productImage = it.mainImage!!.img!!
                                    listObj.isFav = it.fav.isFavorite()
                                    listObj.expire = it.expire
                                    listObj.timeStramp = it.timestamp


                                    if (!it.replacements.isNullOrEmpty()) {
                                        if (it.replacements.size > 1) {
                                            listObj.img1 = it.replacements[0]?.img
                                                    ?: ""
                                            listObj.img2 = it.replacements[1]?.img
                                                    ?: ""
                                        } else {
                                            listObj.img2 = it.replacements[0]?.img
                                                    ?: ""
                                        }

                                    }


                                    productList.add(listObj)

                                }

                                recycler.adapter = AdapterAccountHome(activity, productList, favCount)
                                noData.visibility = View.GONE


                            } else {
                                noData.visibility = View.VISIBLE
                            }

                        }
                    }
                    co.hideLoading()
                }
            }

            override fun onFailure(call: Call<MyAccount>?, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })
    }
}

fun ImageView.setGlideImageNetworkPath(imagePath: String, makeRound: Boolean = true) {
    val ro = RequestOptions()//.override(800, 800)
    if (makeRound)
        ro.circleCrop()
    else ro.centerCrop()
    ro.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//    ro.error(R.drawable.loading_img)
    ro.placeholder(R.drawable.app_logo_white)

    Glide.with(this)
            .applyDefaultRequestOptions(ro)
            .load(imagePath)

            .into(this)

}

fun ImageView.setGlideUserImage(imagePath: String, makeRound: Boolean = true) {
    val ro = RequestOptions()
    if (makeRound)
        ro.circleCrop()
    else ro.centerCrop()
    ro.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//    ro.error(R.drawable.loading_img)
    ro.placeholder(R.drawable.male_selected)

    Glide.with(this)
            .applyDefaultRequestOptions(ro)
            .load(imagePath)

            .into(this)

}


fun ImageView.setGlideImage(bitmap: Bitmap, makeRound: Boolean = true) {
    val ro = RequestOptions()
    if (makeRound) ro.circleCrop() else ro.centerCrop()
    ro.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    ro.placeholder(R.drawable.app_logo_white)

    Glide.with(this)
            .applyDefaultRequestOptions(ro)
            .load(bitmap)
            .into(this)

}


fun ImageView.setGlideImage(file: File, makeRound: Boolean = false) {
    val ro = RequestOptions()
    if (makeRound) ro.circleCrop()
    ro.diskCacheStrategy(DiskCacheStrategy.RESOURCE)


    Glide.with(this)
            .applyDefaultRequestOptions(ro)
            .load(file)
            .into(this)

}
