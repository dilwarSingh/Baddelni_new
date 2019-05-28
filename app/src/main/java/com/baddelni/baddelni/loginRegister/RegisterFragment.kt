package com.baddelni.baddelni.loginRegister

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.Countries.Countries
import com.baddelni.baddelni.Response.Countries.CountriesItem
import com.baddelni.baddelni.Response.Setting
import com.baddelni.baddelni.Response.Settings
import com.baddelni.baddelni.settings.SettingsExplain
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import kotlinx.android.synthetic.main.fragment_register.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {

    private val co: CommonObjects by lazy { CommonObjects(context!!) }
    private var countryId = 0
    private lateinit var countryList: List<CountriesItem>
    var setting: Setting? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginText.setOnClickListener { (context as LoginRegisterActivity).changeContainerFragment(LoginFragment()) }
        backBt.setOnClickListener { (context as LoginRegisterActivity).changeContainerFragment(LoginFragment()) }
/*
        male.setOnClickListener { male.isChecked = true; female.isChecked = false; }
        female.setOnClickListener { female.isChecked = true; male.isChecked = false; }

 */
        signinBt.setOnClickListener { hitCreateProfileApi() }
        getCountyData()



        genderTv.setOnClickListener {
            MaterialDialog
                    .Builder(context!!)
                    .itemsCallbackSingleChoice(0, object : MaterialDialog.ListCallbackSingleChoice {
                        override fun onSelection(dialog: MaterialDialog?, itemView: View?, postion: Int, text: CharSequence?): Boolean {
                            genderTv.text = if (postion == 0) getString(R.string.male) else getString(R.string.female)
                            return true
                        }
                    })
                    .items(getString(R.string.male), getString(R.string.female))
                    .build()
                    .show()
        }


        /*   termOfUse.setOnClickListener {
               openScreenWithText(setting?.terms)
           }

           privacyText.setOnClickListener {
               openScreenWithText(setting?.privacy)
           }*/

    }

    private fun hitCreateProfileApi() {

        if (text.text.toString().isEmpty()) {
            co.myToast("Enter Name")
            return
        } else if (email.text.toString().isEmpty()) {
            co.myToast("Enter Email")
            return
        } else if (country.text.toString().isEmpty()) {
            co.myToast("select country")
            return
        } else if (phone.text.toString().isEmpty()) {
            co.myToast("Enter phone")
            return
        } else if (password.text.toString().isEmpty()) {
            co.myToast("Enter password")
            return
        } else if (genderTv.text.toString().isEmpty()) {
            co.myToast("Enter gender")
            return
        }




        co.showLoading()

        if (co.getStringPrams(AppConstants.TOKEN).isBlank()) {
            val taskToken = FirebaseInstanceId.getInstance().instanceId
            taskToken.addOnCompleteListener(context as LoginRegisterActivity, object : OnCompleteListener<InstanceIdResult> {
                override fun onComplete(result: Task<InstanceIdResult>) {
                    val myToken = result.result?.token ?: ""
                    co.putStringPrams(AppConstants.TOKEN, myToken)
                    hitCreateProfileApi()
                }
            })
            return
        }

        val frag: Fragment = TermsOfUseFragment()
        frag.arguments = createProfileBundle()

        (context as LoginRegisterActivity).changeContainerFragment(frag)

        co.hideLoading()

        /*     f.Api.getApi().createAccount(createProfileBody()).enqueue(object : Callback<RegisterUser> {

                 override fun onResponse(call: Call<RegisterUser>, response: Response<RegisterUser>) {
                     val body = response.body()

                     body?.apply {
                         if (code!!.isSuccess()) {
                             co.putStringPrams(AppConstants.USER_ID, body.data?.id.toString())
                             //   co.putBool(AppConstants.LOGGED_IN, true)
                             context?.startActivity(Intent(context, InterestsActivity::class.java))
                             //     co.myToast(msgEn!!)
                         } else {
                             co.myToast(error!!)
                         }
                     }
                     co.hideLoading()
                 }

                 override fun onFailure(call: Call<RegisterUser>, t: Throwable) {
                     co.myToast(t.message)
                     Log.e("ResponseFailure: ", t.message)
                     t.printStackTrace()
                     co.hideLoading()
                 }

             })*/


    }

    private fun createProfileBody(): MultipartBody {

        val builder = MultipartBody.Builder()

        builder.setType(MultipartBody.FORM)
                .addFormDataPart("name", text.text.toString().trim())
                .addFormDataPart("email", email.text.toString().trim())
                .addFormDataPart("phone", phone.text.toString().trim())
                .addFormDataPart("country_id", countryId.toString())
                .addFormDataPart("password", password.text.toString().trim())
                .addFormDataPart("trans", co.getAppLanguage().langCode())
                .addFormDataPart("token", co.getStringPrams(AppConstants.TOKEN))
                .addFormDataPart("device", "a")

        if (genderTv.text.toString().isNotEmpty())
            builder.addFormDataPart("gender", if (genderTv.text.toString() == getString(R.string.female)) "0" else "1")


        return builder.build()

    }

    private fun createProfileBundle(): Bundle {

        return Bundle().apply {

            putString("name", text.text.toString().trim())
            putString("email", email.text.toString().trim())
            putString("phone", phone.text.toString().trim())
            putString("country_id", countryId.toString())
            putString("password", password.text.toString().trim())
            putString("trans", co.getAppLanguage().langCode())
            putString("token", co.getStringPrams(AppConstants.TOKEN))
            putString("device", "a")

            if (genderTv.text.toString().isNotEmpty())
                putString("gender", if (genderTv.text.toString() == getString(R.string.female)) "0" else "1")

        }

    }

    private fun getCountyData() {


        co.showLoading()

        Api.getApi().getCountries(co.getAppLanguage().langCode()).enqueue(object : Callback<Countries> {

            override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                val body = response.body()

                body?.apply {
                    if (code!!.isSuccess()) {
                        this@RegisterFragment.countryList = countryList!!
                    }
                }

                country.setOnClickListener {
                    MaterialDialog
                            .Builder(context!!)
                            .itemsCallbackSingleChoice(0, object : MaterialDialog.ListCallbackSingleChoice {
                                override fun onSelection(dialog: MaterialDialog?, itemView: View?, postion: Int, text: CharSequence?): Boolean {
                                    country.text = countryList[postion].country
                                    countryId = countryList[postion].id!!
                                    return true
                                }
                            })
                            .items(countryList.map { it.country })
                            .build()
                            .show()
                }

                getAllSettings()
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

        Api.getApi().getSettings(co.getAppLanguage().langCode()).enqueue(object : Callback<Settings> {

            override fun onResponse(call: Call<Settings>, response: Response<Settings>) {
                co.hideLoading()

                val body = response.body()
                setting = body?.setting

            }

            override fun onFailure(call: Call<Settings>, t: Throwable) {
                co.myToast(t.message)
                Log.e("ResponseFailure: ", t.message)
                t.printStackTrace()
                co.hideLoading()
            }

        })

    }

    private fun openScreenWithText(text: String?) {
        val intent = Intent(context, SettingsExplain::class.java)
        intent.putExtra("text", text)
        startActivity(intent)
    }
}
