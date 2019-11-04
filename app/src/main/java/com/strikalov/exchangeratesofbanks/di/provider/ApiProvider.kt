package com.strikalov.exchangeratesofbanks.di.provider

import com.strikalov.exchangeratesofbanks.BuildConfig
import com.strikalov.exchangeratesofbanks.model.Api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class ApiProvider @Inject constructor(
    private val okHttpClient: OkHttpClient
) : Provider<Api> {

    override fun get() : Api =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_BASE_URL)
            .build()
            .create(Api::class.java)
}