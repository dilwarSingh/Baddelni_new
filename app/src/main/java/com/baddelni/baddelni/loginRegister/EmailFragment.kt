package com.baddelni.baddelni.loginRegister


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.loginResponse.LoginResponse
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import kotlinx.android.synthetic.main.fragment_email.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailFragment : Fragment() {
    val co: CommonObjects by lazy { CommonObjects(context!!) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        termOfUse.setOnClickListener { (context as LoginRegisterActivity).changeContainerFragment(RegisterFragment()) }
        signinBt.setOnClickListener {
            hitLoginApi()
        }
        forgotBt.setOnClickListener {
            context!!.startActivity(Intent(context!!, ForgotPassword::class.java))
        }
        activeBt.setOnClickListener {
            val emailData = email.text.toString().trim()

            if (!emailData.isEmpty()) {
                hitActiveEmail(emailData)
            } else {
                co.showToastDialog(detail = getString(R.string.emailActivationLinkAgain), yesNo = null)
            }
        }

    }

    private fun hitActiveEmail(emailData: String) {
        co.showLoading()
        Api.getApi().verify(emailData, co.getAppLanguage().langCode()).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                co.hideLoading()
                val string = response.body()?.string()

                val jsonObject = JSONObject(string)

                if (jsonObject.getString("code") == "0") {
                    co.showToastDialog(detail = getString(R.string.success), yesNo = null)

                } else {
                    co.showToastDialog(detail = getString(R.string.error), yesNo = null)
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

    private fun hitLoginApi() {

        if (isDataCorrect().not())
            return

        co.showLoading()

        if (co.getStringPrams(AppConstants.TOKEN).isBlank()) {
            val taskToken = FirebaseInstanceId.getInstance().instanceId
            taskToken.addOnCompleteListener(context as LoginRegisterActivity, object : OnCompleteListener<InstanceIdResult> {
                override fun onComplete(result: Task<InstanceIdResult>) {
                    val myToken = result.result?.token ?: ""
                    co.putStringPrams(AppConstants.TOKEN, myToken)
                    hitLoginApi()
                }
            })
            return
        }

        Api.getApi().login(email.text.toString().trim(), password.text.toString().trim(), co.getAppLanguage().langCode()
                , co.getStringPrams(AppConstants.TOKEN), "a")
                .enqueue(object : Callback<LoginResponse> {

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val body = response.body()

                        body?.apply {

                            if (code!!.isSuccess()) {
                                co.putStringPrams(AppConstants.USER_ID, body.data?.id.toString())
                                co.putBool(AppConstants.LOGGED_IN, true)
                                co.putStringPrams(AppConstants.AVALIABLE_POSTS, body.data?.availableProduct.toString())
                                context?.startActivity(Intent(context, MainActivity::class.java))
                                (context as AppCompatActivity).finish()
                            } else {
                                co.myToast(msg)

                            }

                        }
                        co.hideLoading()
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        co.myToast(t.message)
                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                        co.hideLoading()
                    }

                })

    }

    private fun isDataCorrect(): Boolean {

        return true
    }


}

