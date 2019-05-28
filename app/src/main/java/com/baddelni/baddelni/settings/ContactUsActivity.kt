package com.baddelni.baddelni.settings

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Patterns
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.ContactUsResponse
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.activity_contact_us.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactUsActivity : AppCompatActivity() {

    val co: CommonObjects by lazy { CommonObjects(this) }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        backBt.setOnClickListener {
            finish()
        }
        send.setOnClickListener {
            if (isDataValid())
                Api.getApi().contactUs(name.text.toString(), email.text.toString(), subject.text.toString(), message.text.toString()).enqueue(object : Callback<ContactUsResponse> {

                    override fun onResponse(call: Call<ContactUsResponse>, response: Response<ContactUsResponse>) {
                        val body = response.body()
                        if (body?.code!!.isSuccess()) {
                            finish()
                        }
                        body.apply {
                            co.showToastDialog(detail = body.msg, yesNo = null)
                        }
                        co.hideLoading()
                    }

                    override fun onFailure(call: Call<ContactUsResponse>, t: Throwable) {
                        co.myToast(t.message)
                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                        co.hideLoading()
                    }

                })
        }
    }


    private fun isDataValid(): Boolean {
        if (name.text.isEmpty() || name.text.length < 3) {
            co.showToastDialog(detail = getString(R.string.enterValidName), yesNo = null)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
            co.showToastDialog(detail = getString(R.string.enterValidEmail), yesNo = null)
            return false
        } else if (subject.text.isEmpty()) {
            co.showToastDialog(detail = getString(R.string.enterValidSubject), yesNo = null)
            return false
        } else if (message.text.isEmpty()) {
            co.showToastDialog(detail = getString(R.string.enterValidMessage), yesNo = null)
            return false
        }
        return true
    }
}