package com.baddelni.baddelni

import android.app.Application
import android.content.Context
import com.baddelni.baddelni.Response.categories.SingleProductResponse.ReplacementsItem
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.AppLanguage
import com.crashlytics.android.Crashlytics
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import io.fabric.sdk.android.Fabric


class App : Application() {
    companion object {
        var replacementsItem: List<ReplacementsItem>? = null
        var fromSelling = false
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