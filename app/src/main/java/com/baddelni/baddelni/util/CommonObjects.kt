package com.baddelni.baddelni.util

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.baddelni.baddelni.R
import com.baddelni.baddelni.account.adapters.AdapterAccountHome
import com.baddelni.baddelni.account.pojos.pojoAccountHome
import com.baddelni.baddelni.loginRegister.GuestLoginActivity
import com.baddelni.baddelni.util.Api.Api
import com.kaopiz.kprogresshud.KProgressHUD
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommonObjects(private val context: Context) {
    private var editor: SharedPreferences? = null
    private var hud: KProgressHUD? = null
    val co: CommonObjects by lazy { CommonObjects(context) }

    private val prefrances: SharedPreferences
        get() = context.getSharedPreferences("baddelniPrefs", MODE_PRIVATE)

    fun checkInternetConnection(): Boolean {
        try {

            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            val isConnected = info != null && info.isConnectedOrConnecting

            if (!isConnected)
                myToast(context.getString(com.baddelni.baddelni.R.string.internetError))

            return isConnected
        } catch (e: NullPointerException) {
            Log.e("interNet Error", e.message)
            e.printStackTrace()
        }

        return false
    }

    fun putStringPrams(key: String, value: String) {
        prefrances.edit().putString(key, value).commit()
    }

    fun putBool(key: String, value: Boolean) {
        prefrances.edit().putBoolean(key, value).commit()
    }

    fun getBool(key: String): Boolean {
        return prefrances.getBoolean(key, false)
    }

    fun isGuestUser(): Boolean {
        return getStringPrams() == AppConstants.GuestUserId;
    }

    fun getStringPrams(key: String = AppConstants.USER_ID): String {

        //      if (key == AppConstants.USER_ID) return "28"

        return prefrances.getString(key, AppConstants.GuestUserId)!!
    }

    fun getDefaultAppLanguage() = prefrances.getString("AppLanguage", null)

    fun getAppLanguage(): AppLanguage {
        val valueOf = AppLanguage.valueOf(prefrances.getString("AppLanguage", AppLanguage.ARABIC.name)!!)
        //Log.d("AppLanguage: ", valueOf.langCode())
        return valueOf
    }

    fun setAppLanguage(lang: AppLanguage) {
        prefrances.edit().putString("AppLanguage", lang.name).apply()
    }

    fun myToast(Message: String?) {
        // Toast.makeText(context, Message, Toast.LENGTH_SHORT).show()
        showToastDialog(detail = Message ?: "", yesNo = null)
    }

    fun showLoading(message: String = context.getString(R.string.pleaseWait)) {
        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                //   .setLabel(message)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
        if (hud != null) {
            try {
                hud!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun hideLoading() {
        if (hud != null && hud!!.isShowing) {
            try {
                hud!!.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun showKeyboard(editText: EditText) {
        val imm: InputMethodManager = (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
    }


    fun hideSoftKeyboard() {
        val appCompatActivity = context as AppCompatActivity
        if (appCompatActivity.currentFocus != null) {
            val inputMethodManager: InputMethodManager = appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(appCompatActivity.currentFocus?.windowToken, 0)
        }
    }

    fun shareText(pId: Int, productName: String, productDescription: String) {

        val intent2 = Intent(Intent.ACTION_SEND)
        intent2.type = "text/plain"
        intent2.putExtra(Intent.EXTRA_TEXT, "$productName \n" +
                "$productDescription\n" +
                "https://www.baddelni.com/product/$pId")
        val createChooser = Intent.createChooser(intent2, context.getString(R.string.shareVia))
        startActivity(context, createChooser, null)


    }

    fun MarkProductFav(product_id: String, favCount: MutableLiveData<Int>?, likeL: LikeListener? = null) {
        showLoading()

        Api.getApi().makeFav(getStringPrams(), product_id, getAppLanguage().langCode())
                .enqueue(object : Callback<ResponseBody> {

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        val body = response.body()
                        hideLoading()

                        val jsonObject = JSONObject(body?.string())


                        if (jsonObject.getString("code") == "0") {
                            val act = jsonObject.getString("action")

                            if (act == "Liked") {
                                if (favCount != null) {
                                    favCount.postValue(favCount.value?.plus(1))
                                }
                                if (likeL != null) {
                                    likeL.liked()

                                }
                            } else {
                                if (favCount != null) {
                                    favCount.postValue(favCount.value?.minus(1))
                                }
                                if (likeL != null) {
                                    likeL.unLiked()
                                }
                            }


                        }

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        myToast(t.message)
                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                        hideLoading()
                    }

                })

    }

    fun DeleteProduct(product_id: String, adapterAccountHome: AdapterAccountHome, adapterPosition: Int, list: MutableList<pojoAccountHome>) {
        showLoading()

        Api.getApi().deleteProduct(product_id, getAppLanguage().langCode())
                .enqueue(object : Callback<ResponseBody> {

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        val body = response.body()
                        hideLoading()

                        val jsonObject = JSONObject(body?.string())


                        if (jsonObject.getString("code") == "0") {
                            adapterAccountHome.notifyItemRemoved(adapterPosition)
                            list.removeAt(adapterPosition)
                        } else {
                            co.myToast(jsonObject.getString("msg"))
                        }

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        myToast(t.message)
                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                        hideLoading()
                    }

                })

    }


    fun RePublishProduct(product_id: String, adapterAccountHome: AdapterAccountHome, adapterPosition: Int, list: MutableList<pojoAccountHome>) {
        showLoading()

        Api.getApi().repubProduct(product_id, getStringPrams(), getAppLanguage().langCode())
                .enqueue(object : Callback<ResponseBody> {

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        val body = response.body()
                        hideLoading()

                        val jsonObject = JSONObject(body?.string())


                        if (jsonObject.getString("code") == "0") {
                            co.myToast(jsonObject.getString("msg"))
                        } else {
                            co.myToast(jsonObject.getString("msg"))
                        }

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        myToast(t.message)
                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                        hideLoading()
                    }

                })

    }

    fun showYesNoDialog(title: String, detail: String, yesNo: YesNoInterface): Unit {

        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    dialog.dismiss()
                    yesNo.onClickYes()
                }

                DialogInterface.BUTTON_NEGATIVE -> {
                    dialog.dismiss()
                    yesNo.onClickNo()
                }
            }//Yes button clicked
            //No button clicked
        }

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title).setMessage(detail).setPositiveButton(context.getString(R.string.ok), dialogClickListener)
                .setNegativeButton(context.getString(R.string.no), dialogClickListener)
                .show()


    }

    fun showToastDialog(title: String = context.getString(R.string.baddelni), detail: String, yesNo: YesNoInterface?): Unit {

        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {//Yes button clicked
                DialogInterface.BUTTON_POSITIVE -> {
                    yesNo?.onClickYes()
                    dialog.dismiss()
                }
            }
            //No button clicked
        }

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title).setMessage(detail).setPositiveButton(context.getString(R.string.ok), dialogClickListener)
        //.setNegativeButton(context.getString(R.string.no), dialogClickListener)


        if (context is AppCompatActivity) {
            if (!context.isFinishing)
                builder.show()
        }

    }

    fun showLoginDialog(detail: String) {
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {//Yes button clicked
                DialogInterface.BUTTON_POSITIVE -> {
                    dialog.dismiss()
                    val intent = Intent(context, GuestLoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    co.putBool(AppConstants.LOGGED_IN, false)
                    context.startActivity(intent)
                    (context as AppCompatActivity).finish()
                }
            }
            //No button clicked
        }

        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.baddelni))
                .setMessage(detail)
                .setPositiveButton(context.getString(R.string.login), dialogClickListener)

        if (context is AppCompatActivity) {
            if (!context.isFinishing)
                builder.show()
        }

    }

    fun showLanguageDialog(lang: LangListener): Unit {


        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    dialog.dismiss()
                    lang.onClickEnglish()
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    dialog.dismiss()
                    lang.onClickHindi()
                }

                DialogInterface.BUTTON_NEUTRAL -> {
                    dialog.dismiss()
                    lang.onClickArabic()
                }
            }//Yes button clicked
            //No button clicked
        }

        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.language)).setMessage(context.getString(R.string.choosePrefredLang))
                .setPositiveButton(context.getString(R.string.english), dialogClickListener)
                .setNegativeButton(context.getString(R.string.hindi), dialogClickListener)
                .setNeutralButton(context.getString(R.string.arabic), dialogClickListener).show()


    }

    fun fireCallIntent(no: String?): Unit {
        val uri = "tel:" + no?.trim()
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(uri)
        context.startActivity(intent)
    }
}
