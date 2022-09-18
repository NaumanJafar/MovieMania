package com.example.moviemania.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object NetworkManager {
    var logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
val httpClient = OkHttpClient.Builder() .apply {
    addInterceptor(logging)
}


    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(httpClient.build())
        .baseUrl("https://api.themoviedb.org")
        .build()
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}