package com.baddelni.baddelni.account


import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.FavResponse.FavResponse
import com.baddelni.baddelni.account.adapters.AdapterFavorites
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.fragment_notifications.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavoritesActivity : AppCompatActivity() {
    val co: CommonObjects by lazy { CommonObjects(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_notifications)

        profileImg.setGlideUserImage(co.getStringPrams(AppConstants.IMG_URL))
        username.text = co.getStringPrams(AppConstants.PERSON_NAME)


        if (intent?.extras != null) {
            val favCount = intent.extras?.getString("favCount", "0")
            notoficationCount.text = getString(R.string.favoriteWithCount, favCount)
        }


        backBt.setOnClickListener { finish() }
   //     Title.text = getString(R.string.favorites)

        hitFavApi()
    }

    private fun hitFavApi() {
        co.showLoading()

        Api.getApi().favList(co.getStringPrams(), co.getAppLanguage().langCode()).enqueue(object : Callback<FavResponse> {

            override fun onResponse(call: Call<FavResponse>, response: Response<FavResponse>) {
                val body = response.body()

                body?.apply {

                    if (code!!.isSuccess()) {
                        val list = data ?: emptyList()
                        recycler.adapter = AdapterFavorites(this@FavoritesActivity, list)

                        notoficationCount.text = getString(R.string.favoriteWithCount, list.size.toString())

                    }

                }
                co.hideLoading()
            }

            override fun onFailure(call: Call<FavResponse>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })


    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }
}