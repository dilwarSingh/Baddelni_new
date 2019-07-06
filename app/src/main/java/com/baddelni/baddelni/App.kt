package com.baddelni.baddelni

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.baddelni.baddelni.Response.categories.SingleProductResponse.ReplacementsItem
import com.baddelni.baddelni.Response.home.HomeResponse
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.AppLanguage
import com.crashlytics.android.Crashlytics
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import io.fabric.sdk.android.Fabric
import java.util.*


class App : Application() {
    companion object {

        val homeData = MutableLiveData<HomeResponse>()

        var replacementsItem: List<ReplacementsItem>? = null
        var fromSelling = false
        var showUserProducts = true


        fun getTimeDetail(context: Context, createdAt: Long): String {

            val diff = Date().time - (createdAt * 1000)

            val diffSeconds = diff / 1000 % 60
            val mints = diff / (60 * 1000) % 60
            val hours = diff / (60 * 60 * 1000) % 24
            val days = diff / (24 * 60 * 60 * 1000)


            val time = when {
                days > 0 -> "$days ${context.getString(R.string.days)}"
                hours > 0 -> "$hours ${context.getString(R.string.hours)}"
                else -> "$mints ${context.getString(R.string.minutes)}"
            }

            return time.toLowerCase()
        }
    }

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)
        Fabric.with(this, Crashlytics())
        AppEventsLogger.activateApp(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, AppLanguage.ARABIC.langCode()))
    }


    /* val TAG = "tag"
     fun printHashKey(pContext: Context) {
         Log.d("AppLog", "key:" + FacebookSdk.getApplicationSignature(this));
         try {
             val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
             for (signature in info.signatures) {
                 val md = MessageDigest.getInstance("SHA")
                 md.update(signature.toByteArray())
                 val hashKey = String(Base64.encode(md.digest(), 0))
                 Log.i(TAG, "printHashKey() Hash Key: $hashKey")
             }
         } catch (e: NoSuchAlgorithmException) {
             Log.e(TAG, "printHashKey()", e)
         } catch (e: Exception) {
             Log.e(TAG, "printHashKey()", e)
         }

     }*/
}