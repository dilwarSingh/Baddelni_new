package com.baddelni.baddelni.loginRegister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.Countries.Countries
import com.baddelni.baddelni.Response.Countries.CountriesItem
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.AppLanguage
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.LangListener
import kotlinx.android.synthetic.main.activity_guest_login.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GuestLoginActivity : AppCompatActivity() {
    val co: CommonObjects by lazy { CommonObjects(this) }
    private lateinit var countryList: List<CountriesItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (co.getDefaultAppLanguage() == null) {
            val appLanguage = AppLanguage.ARABIC
            co.setAppLanguage(appLanguage)
            LocaleHelper.setLocale(this, appLanguage.langCode())
        }
        if (co.getBool(AppConstants.LOGGED_IN)) {
            startActivity(Intent(this@GuestLoginActivity, MainActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_guest_login)

        setOnClicks()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, AppLanguage.ARABIC.langCode()))
    }

    private fun setOnClicks() {

        emailBt.setOnClickListener {
            startActivity(Intent(this@GuestLoginActivity, LoginRegisterActivity::class.java))
        }
        guestBt.setOnClickListener {
            co.putStringPrams(AppConstants.USER_ID, AppConstants.GuestUserId)
            getCountyData()

        }

        languageBt.setOnClickListener {

            co.showLanguageDialog(object : LangListener {
                override fun onClickEnglish() {
                    val appLanguage = AppLanguage.ENGLISH
                    co.setAppLanguage(appLanguage)

                    LocaleHelper.setLocale(this@GuestLoginActivity, appLanguage.langCode())

                    val intent = Intent(this@GuestLoginActivity, GuestLoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

                override fun onClickHindi() {
                    val appLanguage = AppLanguage.HINDI
                    co.setAppLanguage(appLanguage)

                    LocaleHelper.setLocale(this@GuestLoginActivity, appLanguage.langCode())

                    val intent = Intent(this@GuestLoginActivity, GuestLoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                    finish()
                }

                override fun onClickArabic() {
                    val appLanguage = AppLanguage.ARABIC
                    co.setAppLanguage(appLanguage)

                    LocaleHelper.setLocale(this@GuestLoginActivity, appLanguage.langCode())

                    val intent = Intent(this@GuestLoginActivity, GuestLoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            })
        }

    }

    private fun getCountyData() {


        co.showLoading()

        Api.getApi().getCountries(co.getAppLanguage().langCode()).enqueue(object : Callback<Countries> {

            override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                val body = response.body()

                body?.apply {
                    if (code!!.isSuccess()) {
                        this@GuestLoginActivity.countryList = countryList!!
                        showLocationDialog()
                    }
                }


            }

            override fun onFailure(call: Call<Countries>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })

    }

    private fun showLocationDialog() {

        MaterialDialog
                .Builder(this)
                .itemsCallbackSingleChoice(0, object : MaterialDialog.ListCallbackSingleChoice {
                    override fun onSelection(dialog: MaterialDialog?, itemView: View?, postion: Int, text: CharSequence?): Boolean {
                        val countryId = countryList[postion].id!!

                        saveCountry(countryId)

                        return true
                    }
                })
                .items(countryList.map { it.country })
                .build()
                .show()


    }

    private fun saveCountry(countryId: Int) {
        co.showLoading()
        Api.getApi().changeCountry(co.getStringPrams(), countryId.toString(), co.getAppLanguage().langCode())
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                        val jo = JSONObject(response.body()?.string())

                        if (jo.getString("code") == "0") {
                            co.putStringPrams(AppConstants.USER_ID, AppConstants.GuestUserId)
                            co.putBool(AppConstants.LOGGED_IN, true)
                            co.putStringPrams(AppConstants.AVALIABLE_POSTS, "0")
                            startActivity(Intent(this@GuestLoginActivity, MainActivity::class.java))
                            finish()
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
