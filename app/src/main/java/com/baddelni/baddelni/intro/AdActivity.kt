package com.baddelni.baddelni.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View.VISIBLE
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.singleAds.SingleAdsResponse
import com.baddelni.baddelni.loginRegister.interests.InterestsActivity
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppLanguage
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.activity_intro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdActivity : AppCompatActivity() {

    val co: CommonObjects by lazy { CommonObjects(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)


        val toIntrest = intent?.extras?.getString("to").equals("Interest", true)
        val toMain = intent?.extras?.getString("to").equals("Main", true)
        val countryId = intent?.extras?.getInt("countryId", 3) ?: 3

        val list = listOf(R.drawable.shakehand_bg_white_tint)

        getHomeData(countryId)
        intent.putExtra("countryId", countryId)


        val intent = if (toIntrest) {
            intent = Intent(this@AdActivity, InterestsActivity::class.java)
            intent.putExtra("toScreen", "home")
            intent
        } else {
            intent = Intent(this@AdActivity, MainActivity::class.java)
            intent
        }


        skip.setOnClickListener {
            startActivity(intent)
            finish()
        }


    }

    private fun getHomeData(countryId: Int) {
        co.showLoading()

        Api.getApi().getSingleAds(countryId.toString())
                .enqueue(object : Callback<SingleAdsResponse> {
                    override fun onResponse(call: Call<SingleAdsResponse>, response: Response<SingleAdsResponse>) {

                        val body = response.body()

                        body?.apply {
                            if (code!!.isSuccess()) {
                                if (data != null && data.isNotEmpty())
                                    viewpager.adapter = CustomPagerAdapter(this@AdActivity, null, data[0])
                                else {
                                    skipText.visibility = VISIBLE
                                }
                            }
                        }
                        co.hideLoading()

                    }

                    override fun onFailure(call: Call<SingleAdsResponse>, t: Throwable) {
                        co.myToast(t.message)
                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                        co.hideLoading()
                    }
                })
        /*       Api.getApi().getHomeData(co.getStringPrams(), co.getAppLanguage().langCode()).enqueue(object : Callback<HomeResponse> {

                   override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                       val body = response.body()

                       body?.apply {
                           if (code!!.isSuccess()) {
                               val slidesItem = slides!![0]
                               viewpager.adapter = CustomPagerAdapter(this@AdActivity, null, slidesItem)
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

               })*/

    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, AppLanguage.ARABIC.langCode()))
    }

}
