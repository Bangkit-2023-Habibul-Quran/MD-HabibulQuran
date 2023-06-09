package com.everybodv.habibulquran.data.remote.retrofit

import androidx.viewbinding.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuranPredictConfig {
    companion object {
        fun getQuranPredictService(): QuranPredictApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(Interceptor { chain ->
                    val request: Request = chain.request().newBuilder().addHeader("Accept-Encoding", "identity").build()
                    chain.proceed(request)
                })
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://habibultes-k3rttur3ia-et.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(QuranPredictApiService::class.java)
        }
    }
}