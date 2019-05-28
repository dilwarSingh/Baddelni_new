package com.baddelni.baddelni.packageSection

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.PayPackages.PackageResponse
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.activity_buy_package.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuyPackageActivity : AppCompatActivity() {
    private val co: CommonObjects by lazy { CommonObjects(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_package)

        backBt.setOnClickListener { finish() }
        getAllPackages()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    private fun getAllPackages() {
        co.showLoading()

        Api.getApi().getPagckages(co.getAppLanguage().langCode())
                .enqueue(object : Callback<PackageResponse> {

                    override fun onResponse(call: Call<PackageResponse>, response: Response<PackageResponse>) {
                        val body = response.body()

                        body?.apply {
                            if (code!!.isSuccess()) {

                                recycler.adapter = AdapterBuyPackage(this@BuyPackageActivity, packages
                                        ?: emptyList())


                            }
                        }
                        co.hideLoading()
                    }

                    override fun onFailure(call: Call<PackageResponse>, t: Throwable) {
                        co.myToast(t.message)
                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                        co.hideLoading()
                    }

                })

    }

}