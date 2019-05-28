package com.baddelni.baddelni.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.Log
import android.view.View
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.Settings
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.activity_tips.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TipsActivity : AppCompatActivity() {

    val co: CommonObjects by lazy { CommonObjects(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tips)



        acceptBt.setOnClickListener {
            congoGroup.visibility = View.VISIBLE
            Handler().postDelayed({
                runOnUiThread {
                    val intent = Intent(this@TipsActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.putExtra("tabLocation", 1)
                    startActivity(intent)
                    finish()
                    //congoGroup.visibility = GONE
                }
            }, 2000)

        }
        getAllSettings()
    }

    private fun getAllSettings() {
        co.showLoading()

        Api.getApi().getSettings(co.getAppLanguage().langCode()).enqueue(object : Callback<Settings> {

            override fun onResponse(call: Call<Settings>, response: Response<Settings>) {
                val body = response.body()
                help.text = Html.fromHtml(body?.setting?.advice)
                co.hideLoading()
            }

            override fun onFailure(call: Call<Settings>, t: Throwable) {
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