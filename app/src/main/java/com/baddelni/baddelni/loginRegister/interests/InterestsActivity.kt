package com.baddelni.baddelni.loginRegister.interests

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.categories.Category
import com.baddelni.baddelni.Response.categories.categoriesNew.CategoriesResponse
import com.baddelni.baddelni.account.pojos.pojoCats
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.activity_interests.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InterestsActivity : AppCompatActivity() {

    val co by lazy { CommonObjects(this) }
    lateinit var list: MutableList<IntrestsPojo>

    enum class ToScreen { HOME, LOGIN, FINISH_THIS }

    var toScreen = ToScreen.LOGIN

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)

        val screen = intent?.extras?.getString("toScreen", "login")

        if (screen == "home") toScreen = ToScreen.HOME
        else if (screen == "finish") toScreen = ToScreen.FINISH_THIS

        getData()
        doneBt.setOnClickListener {
            hitSaveApi()
        }
    }

    private fun hitSaveApi() {

        var data = ""
        list.forEach {
            if (it.status) {
                data += ",${it.catId}"
            }
        }
        if (data.isEmpty()) {
            co.showToastDialog(detail = getString(R.string.selectYourIntrestFirst), yesNo = null)
            return
        }

        co.showLoading()
        Api.getApi().changeIntrest(co.getStringPrams(AppConstants.USER_ID), data.substring(1, data.length), co.getAppLanguage().langCode())
                .enqueue(object : Callback<ResponseBody> {

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        co.hideLoading()

                        val string = response.body()?.string()
                        val jo = JSONObject(string)

                        if (jo.getString("code") == "0") {

                            if (toScreen == ToScreen.FINISH_THIS) {

                                val listData = emptyList<pojoCats>().toMutableList()
                                list.forEach {
                                    if (it.status) {
                                        val pojoCats = pojoCats(it.catId.toString())
                                        pojoCats.name = it.catName
                                        pojoCats.imageUrl = it.imageUrl

                                        listData.add(pojoCats)

                                    }
                                }

                                val intent = Intent()
                                intent.putExtra("data", listData.toTypedArray())
                                setResult(Activity.RESULT_OK, intent)
                                finish()
                                return
                            }

                            val fbId: String? = jo.getJSONObject("data").getString("facebook_id")
                            val gId: String? = jo.getJSONObject("data").getString("google_id")


                            when {
                                fbId != "null" -> {
                                    startActivity(Intent(this@InterestsActivity, MainActivity::class.java))
                                    finish()
                                }
                                gId != "null" -> {
                                    startActivity(Intent(this@InterestsActivity, MainActivity::class.java))
                                    finish()
                                }
                                else -> {
                                    startActivity(Intent(this@InterestsActivity, MainActivity::class.java))
                                    finish()
                                    /*    co.showToastDialog(detail = getString(R.string.pleaseVerifyAccount), yesNo = object : YesNoInterface {
                                            override fun onClickYes() {
                                                startActivity(Intent(this@InterestsActivity, LoginRegisterActivity::class.java))
                                                finish()
                                            }
                                        })*/

                                }
                            }

                        } else
                            co.showToastDialog(detail = jo.getString("msg"), yesNo = null)
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        co.myToast(t.message)
                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                        co.hideLoading()
                    }

                })
    }

    private fun getData() {

        co.showLoading()
        Api.getApi().getCategoriesAndSubCats(co.getAppLanguage().langCode()).enqueue(object : Callback<CategoriesResponse> {

            override fun onResponse(call: Call<CategoriesResponse>, response: Response<CategoriesResponse>) {
                val body = response.body()

                body?.apply {
                    if (code!!.isSuccess()) {

                        list = emptyList<IntrestsPojo>().toMutableList()
                        categories?.forEach {
                            val pojo = IntrestsPojo(it.id)
                            pojo.catName = it.category.toString()
                            pojo.imageUrl = it.img.toString()

                            list.add(pojo)
                        }

                        interestRecycler.adapter = AdapterInterests(list, this@InterestsActivity)


                    }

                }
                co.hideLoading()
            }

            override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })
    }
}