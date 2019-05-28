package com.baddelni.baddelni.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.Countries.Countries
import com.baddelni.baddelni.Response.Countries.CountriesItem
import com.baddelni.baddelni.Response.Setting
import com.baddelni.baddelni.Response.Settings
import com.baddelni.baddelni.loginRegister.GuestLoginActivity
import com.baddelni.baddelni.loginRegister.LoginRegisterActivity
import com.baddelni.baddelni.packageSection.BuyPackageActivity
import com.baddelni.baddelni.util.*
import com.baddelni.baddelni.util.Api.Api
import kotlinx.android.synthetic.main.fragment_settings.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SettingsFragment : Fragment() {
    val co: CommonObjects by lazy { CommonObjects(context!!) }
    lateinit var mySettings: Setting
    var isScreenCreated = false
    private lateinit var countryList: List<CountriesItem>
    private var countryId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buyPosts.setOnClickListener { startActivity(Intent(context!!, BuyPackageActivity::class.java)) }

        backBt.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("tabLocation", 0)
            startActivity(intent)
        }

        getAllSettings()
        getCountyData()


        if (co.isGuestUser()) {
            logoutBt.text = getString(R.string.login)
        }

        logoutBt.setOnClickListener {
            if (co.isGuestUser()) {
                co.showYesNoDialog(getString(R.string.login), getString(R.string.sureLogin), object : YesNoInterface {
                    override fun onClickYes() {
                        if (isScreenCreated) {
                            val intent = Intent(context!!, LoginRegisterActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            co.putBool(AppConstants.LOGGED_IN, false)
                            startActivity(intent)
                            (context as AppCompatActivity).finish()
                        }
                    }
                })
            } else {
                co.showYesNoDialog(getString(R.string.logout), getString(R.string.sureLogout), object : YesNoInterface {
                    override fun onClickYes() {
                        if (isScreenCreated) {
                            val intent = Intent(context!!, GuestLoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            co.putBool(AppConstants.LOGGED_IN, false)
                            startActivity(intent)
                            (context as AppCompatActivity).finish()
                        }
                    }

                })
            }
        }


        changeLanguage.setOnClickListener {
            co.showLanguageDialog(object : LangListener {
                override fun onClickEnglish() {
                    val appLanguage = AppLanguage.ENGLISH
                    co.setAppLanguage(appLanguage)
                    LocaleHelper.setLocale(context, appLanguage.langCode())
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("tabLocation", 4)
                    startActivity(intent)
                }

                override fun onClickHindi() {
                    val appLanguage = AppLanguage.HINDI
                    co.setAppLanguage(appLanguage)
                    LocaleHelper.setLocale(context, appLanguage.langCode())
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("tabLocation", 4)
                    startActivity(intent)
                }

                override fun onClickArabic() {
                    val appLanguage = AppLanguage.ARABIC
                    co.setAppLanguage(appLanguage)
                    LocaleHelper.setLocale(context, appLanguage.langCode())
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("tabLocation", 4)
                    startActivity(intent)
                }
            })
        }

        advertise.setOnClickListener {
            openBrowserWithLink("https://baddelni.com/advertise")
        }
        privacyPolicy.setOnClickListener {
            openScreenWithText(mySettings.privacy)
        }
        tnc.setOnClickListener {
            openScreenWithText(mySettings.terms)
        }
        contactUs.setOnClickListener {
            val intent = Intent(context, ContactUsActivity::class.java)
            context?.startActivity(intent)
        }
        /*  advertise.setOnClickListener {
                  openScreenWithText(mySettings.advice)
          }*/
        aboutApp.setOnClickListener {
            openScreenWithText(mySettings.about)
        }
        helpCenter.setOnClickListener {
            openScreenWithText(mySettings.help)
        }
    }


    private fun saveCountry(countryId: Int) {
        co.showLoading()
        Api.getApi().changeCountry(co.getStringPrams(), countryId.toString(), co.getAppLanguage().langCode())
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        val jo = JSONObject(response.body()?.string())
                        if (jo.getString("code") == "0") {
                            co.showToastDialog(getString(R.string.app_name), getString(R.string.updateSuccessful), object : YesNoInterface {
                                override fun onClickYes() {
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    intent.putExtra("tabLocation", 0)
                                    startActivity(intent)
                                    (context as AppCompatActivity).finish()
                                }

                            })


                        }
                        co.hideLoading()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        co.myToast(t.message)
                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                        co.hideLoading()
                    }
                })
    }


    private fun getCountyData() {
        Api.getApi().getCountries(co.getAppLanguage().langCode()).enqueue(object : Callback<Countries> {
            override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                val body = response.body()
                body?.apply {
                    if (code!!.isSuccess()) {
                        this@SettingsFragment.countryList = countryList!!

                        changeCountry.setOnClickListener {
                            MaterialDialog
                                    .Builder(context!!)
                                    .itemsCallbackSingleChoice(0, object : MaterialDialog.ListCallbackSingleChoice {
                                        override fun onSelection(dialog: MaterialDialog?, itemView: View?, postion: Int, text: CharSequence?): Boolean {
                                            //  country.text = countryList[postion].country
                                            countryId = countryList[postion].id!!
                                            saveCountry(countryId)
                                            return true
                                        }
                                    })
                                    .items(countryList.map { it.country })
                                    .build()
                                    .show()
                        }
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

    private fun getAllSettings() {
        co.showLoading()

        Api.getApi().getSettings(co.getAppLanguage().langCode()).enqueue(object : Callback<Settings> {

            override fun onResponse(call: Call<Settings>, response: Response<Settings>) {
                val body = response.body()
                mySettings = body?.setting!!

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

    override fun onResume() {
        super.onResume()
        isScreenCreated = true
    }

    private fun openBrowserWithLink(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun openScreenWithText(text: String?) {
        val intent = Intent(context, SettingsExplain::class.java)
        intent.putExtra("text", text)
        startActivity(intent)
    }


}