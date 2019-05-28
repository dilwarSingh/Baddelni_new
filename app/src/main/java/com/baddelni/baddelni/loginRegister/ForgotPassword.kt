package com.baddelni.baddelni.loginRegister

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.baddelni.baddelni.R
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.activity_forgot_password.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPassword : AppCompatActivity() {
    val co: CommonObjects by lazy { CommonObjects(this) }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        save.setOnClickListener {
            hitForgotPasswordApi()
        }
        back.setOnClickListener { finish() }

    }

    private fun hitForgotPasswordApi() {
        co.showLoading()
        Api.getApi().reset_password(email = email.text.toString()).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                co.hideLoading()
                val string = response?.body()?.string()

                if (string != null) {
                    val jsonObject = JSONObject(string)
                    if (jsonObject.getString("code") == "0") {
                        co.showToastDialog(detail = getString(R.string.success), yesNo = null)
                    } else {
                        co.showToastDialog(detail = getString(R.string.error), yesNo = null)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })
    }
}
