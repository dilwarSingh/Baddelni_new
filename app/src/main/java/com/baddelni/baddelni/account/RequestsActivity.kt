package com.baddelni.baddelni.account

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.requestProduct.OrdersItem
import com.baddelni.baddelni.account.adapters.AdapterRequests
import com.baddelni.baddelni.account.pojos.pojoRequest
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.request_activity.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RequestsActivity : AppCompatActivity() {

    private val co: CommonObjects by lazy { CommonObjects(this) }

    enum class RequestScreenMode { Request, MyRequest }

    var screenMode = RequestScreenMode.Request
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_activity)

        profileImg.setGlideUserImage(co.getStringPrams(AppConstants.IMG_URL))
        username.text = co.getStringPrams(AppConstants.PERSON_NAME)


        if (intent?.extras != null) {
            val screen = intent.extras?.getString("fromScreen", "request")
            requestCount.text = ""

            screenMode = if (screen == "request") {
                requestCount.text = getString(R.string.request)
                RequestScreenMode.Request
            } else {
                requestCount.text = getString(R.string.myRequest)
                RequestScreenMode.MyRequest
            }

        }

        //       include.newPostBt.setOnClickListener { startActivity(Intent(this, CreatePostActivity::class.java)) }
        backBt.setOnClickListener { onBackPressed() }


    }

    override fun onResume() {
        super.onResume()
        HitApi()
    }

    private fun HitApi() {
        co.showLoading()

        var turnOnClicks = false;

        val order = if (screenMode == RequestScreenMode.Request) {
            turnOnClicks = true
            Api.getApi().orders(co.getStringPrams(), co.getAppLanguage().langCode())
        } else {
            turnOnClicks = false
            Api.getApi().myOrders(co.getStringPrams(), co.getAppLanguage().langCode())
        }



        order.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                co.hideLoading()
                val string = response.body()?.string()
                if (string != null) {
                    val jsonObject = JSONObject(string)

                    if (jsonObject.getString("code") == "0") {
                        val toString =
                                if (screenMode == RequestScreenMode.Request)
                                    jsonObject.getJSONArray("orders").toString()
                                else
                                    jsonObject.getJSONArray("data").toString()
                        print(toString)

                        // val yourJson = jsonObject.get("data")
                        val listType = object : TypeToken<List<OrdersItem>>() {

                        }.type

                        val yourList = Gson().fromJson(toString, listType) as List<OrdersItem>

                        val list = emptyList<pojoRequest>().toMutableList()


                        val reqName: String = if (screenMode == RequestScreenMode.Request) {
                            getString(R.string.request)
                        } else {
                            getString(R.string.myRequest)
                        }
                        requestCount.text = yourList.size.toString() + " " + reqName

                        yourList.forEach {

                            val pojo = pojoRequest(it.orderId!!)

                            if (screenMode == RequestScreenMode.MyRequest) {
                                pojo.phone = it.product1?.phone ?: ""
                                pojo.imageUrl = it.product1?.user?.img ?: ""
                                pojo.loaction = it.product1?.user?.country?.country ?: ""
                                pojo.name = it.product1?.user?.name ?: ""

                            } else {
                                pojo.phone = it.user?.phone ?: ""
                                pojo.imageUrl = it.user?.img ?: ""
                                pojo.loaction = it.user?.country?.country ?: ""
                                pojo.name = it.user?.name ?: ""
                            }
                            pojo.statusText = it.status_text ?: getString(R.string.request)
                            pojo.pId = it.productId ?: 0
                            pojo.img1 = it.product1?.mainImage?.img ?: ""
                            pojo.img2 = it.product2?.img ?: ""
                            pojo.p1Name = it.product1?.name ?: ""
                            pojo.p2Name = it.product2?.name ?: ""
                            pojo.description1 = it.product1?.description ?: ""
                            pojo.description2 = it.product2?.description ?: ""
                            pojo.isFav = it.product1?.fav?.isFavorite() ?: false

                            list.add(pojo)
                        }

                        if (list.isEmpty()) {
                            noData.visibility = VISIBLE
                        } else {
                            noData.visibility = GONE
                            recycler.adapter = AdapterRequests(this@RequestsActivity, list, turnOnClicks)
                        }
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

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    override fun onBackPressed() {

        Log.d(RequestsActivity::class.java.simpleName, "onBackPressed in activity")
        finish()
    }
}

