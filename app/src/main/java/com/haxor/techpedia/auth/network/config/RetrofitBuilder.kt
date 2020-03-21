package com.haxor.techpedia.auth.network.config


import android.content.Context

import com.haxor.techpedia.BuildConfig
import com.haxor.techpedia.URL.auth

import java.util.concurrent.TimeUnit

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Yudas Malabi
 */
object RetrofitBuilder {

    fun builder(context: Context): Retrofit {
        val okhttpBuilder = OkHttpClient().newBuilder()
        okhttpBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okhttpBuilder.writeTimeout(60, TimeUnit.SECONDS)
        okhttpBuilder.readTimeout(60, TimeUnit.SECONDS)

        val cacheSize = 10 * 1024 * 1024
        val cache = Cache(context.cacheDir, cacheSize.toLong())
        okhttpBuilder.cache(cache)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okhttpBuilder.addInterceptor(interceptor)
        }

        return Retrofit.Builder()
            .baseUrl(auth)
            .client(okhttpBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}