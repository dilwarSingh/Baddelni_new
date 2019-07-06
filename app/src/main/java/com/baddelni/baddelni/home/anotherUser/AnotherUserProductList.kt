package com.baddelni.baddelni.home.anotherUser

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.baddelni.baddelni.R
import com.baddelni.baddelni.Response.MyAccount.MyAccount
import com.baddelni.baddelni.account.pojos.pojoAccountHome
import com.baddelni.baddelni.account.setGlideUserImage
import com.baddelni.baddelni.util.Api.Api
import com.baddelni.baddelni.util.AppConstants
import com.baddelni.baddelni.util.CommonObjects
import kotlinx.android.synthetic.main.activity_another_user_product_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnotherUserProductList : AppCompatActivity() {

    val userId by lazy { intent?.extras?.getString("userId") }
    val co: CommonObjects by lazy { CommonObjects(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another_user_product_list)

        val prams = co.getStringPrams(AppConstants.AVALIABLE_POSTS)
        notoficationCount2.text = "$prams ${getString(R.string.posts)}"
        profileImg2.setGlideUserImage(co.getStringPrams(AppConstants.IMG_URL))
        username2.text = co.getStringPrams(AppConstants.PERSON_NAME)
        backBt2.setOnClickListener { onBackPressed() }

        getAllUserProducts(userId!!)
    }

    private fun getAllUserProducts(userId: String) {

        co.showLoading()
        Api.getApi().getAccountDetails(userId, co.getAppLanguage().langCode())
                .enqueue(object : Callback<MyAccount> {

                    override fun onResponse(call: Call<MyAccount>?, response: Response<MyAccount>?) {
                        val body = response?.body()

                        if (body != null) {
                            if (body.code!!.isSuccess()) {
                                body.data?.apply {


                                    profileImg2.setGlideUserImage(img)
                                    username2.text = name


                                    val productList: MutableList<pojoAccountHome> = arrayListOf()
                                    product.forEach {
                                        val listObj = pojoAccountHome(it!!.id)
                                        listObj.name = it.name!!
                                        listObj.description = it.description!!
                                        listObj.productImage = it.mainImage!!.img!!
                                        listObj.isFav = it.fav.isFavorite()
                                        listObj.expire = it.expire


                                        if (!it.replacements.isNullOrEmpty()) {
                                            if (it.replacements.size > 1) {
                                                listObj.img1 = it.replacements[0]?.img
                                                        ?: ""
                                                listObj.img2 = it.replacements[1]?.img
                                                        ?: ""
                                            } else {
                                                listObj.img2 = it.replacements[0]?.img
                                                        ?: ""
                                            }

                                        }


                                        productList.add(listObj)

                                    }
                                    notoficationCount2.text = "${productList.size} ${getString(R.string.posts)}"
                                    recycler.adapter = AdapterAnotherUserProducts(this@AnotherUserProductList, productList)


                                }
                            }
                            co.hideLoading()
                        }
                    }

                    override fun onFailure(call: Call<MyAccount>?, t: Throwable) {
                        co.myToast(t.message)
                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                        co.hideLoading()
                    }

                })
    }


}
