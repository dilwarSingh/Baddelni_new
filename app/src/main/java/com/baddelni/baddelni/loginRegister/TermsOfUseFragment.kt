package com.baddelni.baddelni.loginRegister


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.RegisterUser
import com.baddelni.baddelni.Response.Settings
import com.baddelni.baddelni.loginRegister.interests.InterestsActivity
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.fragment_terms_of_use.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermsOfUseFragment : Fragment() {
    private val co: CommonObjects by lazy { CommonObjects(context!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_terms_of_use, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllSettings();



        signUpBt.setOnClickListener {
            if (agreeCB.isChecked) {
                hitCreateProfileApi()
            } else {
                co.showToastDialog(detail = "Agree to Terms and Condition to proceed", yesNo = null)
            }
        }

        backBt.setOnClickListener {
            val frag: Fragment = LoginFragment()
            (context as LoginRegisterActivity).changeContainerFragment(frag)
        }


    }

    private fun getAllSettings() {
        co.showLoading()

        Api.getApi().getSettings(co.getAppLanguage().langCode()).enqueue(object : Callback<Settings> {

            override fun onResponse(call: Call<Settings>, response: Response<Settings>) {
                val body = response.body()
                body?.setting?.apply {
                    textView3.text = Html.fromHtml(terms ?: "")

                }
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

    private fun hitCreateProfileApi() {
        co.showLoading()

        Api.getApi().createAccount(createProfileBody()).enqueue(object : Callback<RegisterUser> {

            override fun onResponse(call: Call<RegisterUser>, response: Response<RegisterUser>) {
                val body = response.body()

                body?.apply {
                    if (code!!.isSuccess()) {
                        co.putStringPrams(AppConstants.USER_ID, body.data?.id.toString())
                        context?.startActivity(Intent(context, InterestsActivity::class.java))
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

        })


    }

    private fun createProfileBody(): MultipartBody {

        val builder = MultipartBody.Builder()

        builder.setType(MultipartBody.FORM)
                .addFormDataPart("name", arguments?.getString("name")!!)
                .addFormDataPart("email", arguments?.getString("email")!!)
                .addFormDataPart("phone", arguments?.getString("phone")!!)
                .addFormDataPart("country_id", arguments?.getString("country_id")!!)
                .addFormDataPart("password", arguments?.getString("password")!!)
                .addFormDataPart("trans", arguments?.getString("trans")!!)
                .addFormDataPart("token", arguments?.getString("token")!!)
                .addFormDataPart("device", arguments?.getString("device")!!)

        if (arguments?.getString("gender", null) != null)
            builder.addFormDataPart("gender", arguments?.getString("gender")!!)


        return builder.build()

    }
}
