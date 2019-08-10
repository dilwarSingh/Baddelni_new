package com.baddelni.baddelni.util.Api

import com.baddelni.baddelni.Response.ContactUsResponse
import com.baddelni.baddelni.Response.FavResponse.FavResponse
import com.baddelni.baddelni.Response.MyAccount.MyAccount
import com.baddelni.baddelni.Response.Notification.NotificationResponse
import com.baddelni.baddelni.Response.PayPackages.PackageResponse
import com.baddelni.baddelni.Response.RegisterUser
import com.baddelni.baddelni.Response.Settings
import com.baddelni.baddelni.Response.categories.SingleProductResponse.SingleProductResponse
import com.baddelni.baddelni.Response.categories.categoriesNew.CategoriesResponse
import com.baddelni.baddelni.Response.categories.product.CategoryProducts
import com.baddelni.baddelni.Response.home.HomeResponse
import com.baddelni.baddelni.Response.loginResponse.LoginResponse
import com.baddelni.baddelni.Response.sellingItems.SelllingItemResponse
import com.baddelni.baddelni.Response.singleAds.SingleAdsResponse
import com.baddelni.baddelni.categories.response.CreateChatResponse
import com.baddelni.baddelni.chat.response.chatUsersResponse.ChatUserResponse
import com.baddelni.baddelni.home.searchActivity.response.SearchResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiAddress {


    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email: String,
              @Field("password") password: String,
              @Field("trans") lang: String,
              @Field("token") token: String,
              @Field("device") device: String): Call<LoginResponse>

    @FormUrlEncoded
    @POST("social_register")
    fun socialRegister(@Field("type") type: String,
                       @Field("social_id") social_id: String,
                       @Field("name") name: String,
                       @Field("email") email: String,
                       @Field("token") token: String,
                       @Field("device") device: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("my_account")
    fun getAccountDetails(@Field("user_id") user_id: String, @Field("trans") lang: String): Call<MyAccount>

    @FormUrlEncoded
    @POST("home1")
    fun getHomeData(@Field("user_id") user_id: String, @Field("trans") lang: String): Call<HomeResponse>

    @FormUrlEncoded
    @POST("single_ads")
    fun getSingleAds(@Field("country_id") country_id: String): Call<SingleAdsResponse>

    @FormUrlEncoded
    @POST("selling_items")
    fun getSellingItems(@Field("user_id") user_id: String): Call<SelllingItemResponse>

    @FormUrlEncoded
    @POST("seeallhome")//user_id,trans,product_type=toplatest or topcountry or topintersted
    fun getSeeAllProducts(@Field("user_id") user_id: String,
                          @Field("trans") trans: String,
                          @Field("product_type") product_type: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("get_chats")
    fun getChatUserList(@Field("user_id") user_id: String): Call<ChatUserResponse>

    @FormUrlEncoded
    @POST("create_chat_id")//product_id, sender_id, receiver_id, trans
    fun createChatId(
            @Field("product_id") product_id: String,
            @Field("sender_id") sender_id: String,
            @Field("receiver_id") receiver_id: String,
            @Field("trans") trans: String
    ): Call<CreateChatResponse>

    @FormUrlEncoded
    @POST("categories")
    fun getCategoriesAndSubCats(@Field("user_id") user_id: String, @Field("trans") lang: String): Call<CategoriesResponse>

    @FormUrlEncoded
    @POST("categories")
    fun getCategoriesAndSelling(@Field("user_id") user_id: String, @Field("trans") lang: String, @Field("selling") selling: String): Call<CategoriesResponse>

    @FormUrlEncoded
    @POST("category")
    fun getCategoriesProduct(@Field("user_id") user_id: String, @Field("category_id") category_id: Int, @Field("trans") lang: String): Call<CategoryProducts>

    @FormUrlEncoded
    @POST("productview")
    fun markProductView(@Field("user_id") user_id: String, @Field("product_id") product_id: Int): Call<ResponseBody>

    @FormUrlEncoded
    @POST("setting")
    fun getSettings(@Field("trans") lang: String): Call<Settings>

    @FormUrlEncoded
    @POST("check_version")
    fun checkVersion(@Field("device_type") device_type: String, @Field("app_version") app_version: Float): Call<ResponseBody>

    @FormUrlEncoded
    @POST("countries")
    fun getCountries(@Field("trans") lang: String): Call<com.baddelni.baddelni.Response.Countries.Countries>

    @POST("update_my_account")
    fun updateAccount(@Body profileData: RequestBody): Call<ResponseBody>

    @POST("subupload_image")
    fun subImageUpload(@Body profileData: RequestBody): Call<ResponseBody>

    @POST("store_product")
    fun createPost(@Body createPost: RequestBody): Call<ResponseBody>

    @POST("update_product")
    fun updatePost(@Body updatedPost: RequestBody): Call<ResponseBody>

    @POST("make_order")
    fun makeOrder(@Body createPost: RequestBody): Call<ResponseBody>

    @POST("register")
    fun createAccount(@Body userData: RequestBody): Call<RegisterUser>

    @FormUrlEncoded
    @POST("interests")
    fun changeIntrest(@Field("user_id") user_id: String, @Field("categories") categories: String, @Field("trans") lang: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("update_user_country")
    fun changeCountry(@Field("user_id") user_id: String, @Field("country_id") country_id: String, @Field("trans") lang: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("single_product")
    fun singleProduct(@Field("user_id") user_id: String,@Field("product_id") product_id: Int, @Field("trans") lang: String): Call<SingleProductResponse>

    @FormUrlEncoded
    @POST("status_order")
    fun statusOrder(@Field("user_id") user_id: String,
                    @Field("order_id") order_id: Int,
                    @Field("status") status: Int, @Field("trans") lang: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("orders")
    fun orders(@Field("user_id") user_id: String, @Field("trans") lang: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("my_orders")
    fun myOrders(@Field("user_id") user_id: String, @Field("trans") lang: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("verify")
    fun verify(@Field("email") email: String, @Field("trans") lang: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("reset_password")
    fun reset_password(@Field("email") email: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("favourite_product")
    fun makeFav(@Field("user_id") user_id: String, @Field("product_id") product_id: String, @Field("trans") trans: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("my_favourite")
    fun favList(@Field("user_id") user_id: String, @Field("trans") trans: String): Call<FavResponse>

    @FormUrlEncoded
    @POST("notifications")
    fun getNotifications(@Field("user_id") user_id: String, @Field("trans") trans: String): Call<NotificationResponse>

    @FormUrlEncoded
    @POST("packages")
    fun getPagckages(@Field("trans") trans: String): Call<PackageResponse>

    @FormUrlEncoded
    @POST("payment")
    fun getPayment(@Field("package_id") package_id: String, @Field("user_id") user_id: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("contacts")
    fun contactUs(@Field("name") name: String,
                  @Field("email") email: String,
                  @Field("subject") subject: String,
                  @Field("message") message: String): Call<ContactUsResponse>

    @FormUrlEncoded
    @POST("delete_product")
    fun deleteProduct(@Field("product_id") product_id: String,
                      @Field("trans") trans: String): Call<ResponseBody>


    @FormUrlEncoded
    @POST("republish_product")
    fun repubProduct(
            @Field("product_id") product_id: String,
            @Field("user_id") user_id: String,
            @Field("trans") trans: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("testhome")
    fun productSearch(//user_id,search,filter(0,1), trans
            @Field("user_id") user_id: String,
            @Field("search") search: String,
            @Field("filter") filter: String,
            @Field("trans") trans: String
    ): Call<SearchResponse>

    @FormUrlEncoded
    @POST("testhome")
    fun productSearch(//user_id,search,filter(0,1), trans
            @Field("user_id") user_id: String,
            @Field("search") search: String,
            @Field("trans") trans: String
    ): Call<SearchResponse>



}