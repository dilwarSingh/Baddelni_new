package com.baddelni.baddelni.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.baddelni.baddelni.MainActivity
import com.baddelni.baddelni.R
import com.baddelni.baddelni.account.pojos.pojoRequest
import com.baddelni.baddelni.categories.ImageListSliderActivity
import com.baddelni.baddelni.settings.LocaleHelper
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import com.baddelni.baddelni.util.YesNoInterface
import kotlinx.android.synthetic.main.exchange_detail.*
import kotlinx.android.synthetic.main.layout_product_view.view.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExchangeDetail : AppCompatActivity() {

    private val co: CommonObjects by lazy { CommonObjects(this) }
    var orderId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exchange_detail)

        profileImage.setGlideUserImage(co.getStringPrams(AppConstants.IMG_URL))
        userName.text = co.getStringPrams(AppConstants.PERSON_NAME)


        declineBt.setOnClickListener {

            co.showYesNoDialog(getString(R.string.decline), getString(R.string.sureDecline), object : YesNoInterface {
                override fun onClickYes() {
                    confirmScreen(1)
                }
            })


        }
        imageView16.setOnClickListener { co.fireCallIntent(phoneNo.text.toString()) }

        backBt.setOnClickListener { finish() }
        acceptBt.setOnClickListener { confirmScreen(2) }

     //   include.text.text = ""
        intent.extras?.apply {
            val data = getSerializable("data") as pojoRequest

            profileImg.setGlideImageNetworkPath(data.imageUrl)
            username.text = data.name
            location.text = data.loaction
            product1.productImage.setGlideImageNetworkPath(data.img1,false)
            product2.productImage.setGlideImageNetworkPath(data.img2,false)
            orderId = data.orderId
            phoneNo.text = data.phone

            product1.productName.text = data.p1Name
            product2.productName.text = data.p2Name

            product1.productDescription.text = data.description1
            product2.productDescription.text = data.description2


            product1.productImage.setOnClickListener {
                val intent = Intent(this@ExchangeDetail, ImageListSliderActivity::class.java)
                val arr = arrayListOf(data.img1)
                intent.putExtra("imageList", arr.toTypedArray())
                startActivity(intent)
            }
            product2.productImage.setOnClickListener {
                val intent = Intent(this@ExchangeDetail, ImageListSliderActivity::class.java)
                val arr = arrayListOf(data.img1)
                intent.putExtra("imageList", arr.toTypedArray())
                startActivity(intent)
            }


        }

    }

    private fun confirmScreen(status: Int) {

        co.showLoading()
        Api.getApi().statusOrder(co.getStringPrams(), orderId, status, co.getAppLanguage().langCode()).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                co.hideLoading()

                val string = response.body()?.string()
                val jo = JSONObject(string)

                if (jo.getString("code") == "0") {
                    if (status == 2) {
                        startActivity(Intent(this@ExchangeDetail, TipsActivity::class.java))
                    } else {
                        val intent = Intent(this@ExchangeDetail, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        intent.putExtra("tabLocation", 1)
                        startActivity(intent)
                    }
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

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

}