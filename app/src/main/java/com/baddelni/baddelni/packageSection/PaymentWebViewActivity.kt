package com.baddelni.baddelni.packageSection

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.baddelni.baddelni.R
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.YesNoInterface
import kotlinx.android.synthetic.main.activity_my_web_view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentWebViewActivity : AppCompatActivity() {

    private val co: CommonObjects by lazy { CommonObjects(this) }

    var htmlText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_web_view)

        backBt.setOnClickListener { finish() }

        webView.settings.apply {
            javaScriptEnabled = true
            pluginState = WebSettings.PluginState.ON_DEMAND

        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                co.hideLoading()

                if (url.equals("https://baddelni.com/response", true)) {
                    co.showToastDialog(detail = "Payment Successful", yesNo = object : YesNoInterface {
                        override fun onClickYes() {
                            this@PaymentWebViewActivity.finish()
                        }
                    })
                }
            }
        }


        val selectedPackage = intent?.extras?.getInt("payId")
        getPayments(selectedPackage.toString())

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    private fun getPayments(selectedPackage: String?) {
        co.showLoading()

        Api.getApi().getPayment(selectedPackage!!, co.getStringPrams())
                .enqueue(object : Callback<ResponseBody> {

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        val jsonResponse = response.body()?.string()

                        print(jsonResponse)
                        htmlText = jsonResponse

                        webView.loadUrl(htmlText)

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
