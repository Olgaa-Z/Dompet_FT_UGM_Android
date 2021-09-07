package com.teknikugm.dompetft.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientDua {

    private var retrofit: Retrofit? = null

    val instance: Retrofit
        get (){
            if (retrofit == null){
                retrofit = Retrofit.Builder().baseUrl("https://api.dompetft.xyz/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttp())
                    .build()
            }
            return retrofit !!
        }

    fun getOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}