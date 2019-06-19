package com.baddelni.baddelni.util.Api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class Api {

    companion object {

        fun getApi(): ApiAddress {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(logging)

            httpClient.readTimeout(0, TimeUnit.SECONDS)
            httpClient.connectTimeout(0, TimeUnit.SECONDS)
            httpClient.callTimeout(0, TimeUnit.SECONDS)
            httpClient.writeTimeout(0, TimeUnit.SECONDS)

            val retrofit = Retrofit.Builder()
                    //.baseUrl("https://baddelni.com/developbaddelni/public/api/")
                          .baseUrl("https://baddelni.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()

            return retrofit.create(ApiAddress::class.java)
        }

    }
}
