package com.baddelni.baddelni.notifications

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.Notification.NotificationResponse
import com.baddelni.baddelni.account.setGlideImageNetworkPath
import com.baddelni.baddelni.account.setGlideUserImage
import com.baddelni.baddelni.packageSection.CreatePostActivity
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.fragment_notifications.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragment : Fragment() {
    val co: CommonObjects by lazy { CommonObjects(context!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileImg.setGlideUserImage(co.getStringPrams(AppConstants.IMG_URL))
        username.text = co.getStringPrams(AppConstants.PERSON_NAME)

        backBt.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("tabLocation", 0)
            startActivity(intent)
        }

        fabCreatePost.setOnClickListener {
            if (co.getStringPrams() == AppConstants.GuestUserId) {
                co.showLoginDialog(getString(R.string.dont_have_permission))
            } else
                startActivity(Intent(context, CreatePostActivity::class.java))
        }

        /*  include.newPostText.text = getString(R.string.deleteAll)
          include.text.text = getString(R.string.notifications)

          include.newPostBt.visibility = View.INVISIBLE
          include.newPostText.setOnClickListener {
              recycler.adapter = AdapterNotifications(context!!, emptyList())
              include.count.text = "0"

          }
  */
        /* recycler.adapter = AdapterNotifications(context!!, data
                 ?: emptyList<DataItem>())*/

        getNotifications()
    }

    private fun getNotifications() {
        co.showLoading()

        Api.getApi().getNotifications(co.getStringPrams(), co.getAppLanguage().langCode())
                .enqueue(object : Callback<NotificationResponse> {

                    override fun onResponse(call: Call<NotificationResponse>, response: Response<NotificationResponse>) {
                        val body = response.body()

                        body?.apply {

                            if (code!!.isSuccess()) {
                                val list = notifications ?: emptyList()

                                notoficationCount.text = getString(R.string.notificationWithCount, list.size.toString())

                                if (list.isEmpty()) {
                                    noNotification.visibility = VISIBLE
                                } else {
                                    recycler.adapter = AdapterNotifications(context!!, list)
                                    noNotification.visibility = GONE

                                }
                            }
                        }
                        co.hideLoading()
                    }

                    override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                        co.myToast(t.message)
                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                        co.hideLoading()
                    }

                })


    }
}