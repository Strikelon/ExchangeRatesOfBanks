package com.strikalov.exchangeratesofbanks.di.provider

import com.strikalov.exchangeratesofbanks.BuildConfig
import com.strikalov.exchangeratesofbanks.model.server.EncodingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Provider

class OkHttpClientProvider @Inject constructor(): Provider<OkHttpClient>{

    override fun get(): OkHttpClient = with(OkHttpClient.Builder()) {
        addNetworkInterceptor(EncodingInterceptor())
        if (BuildConfig.DEBUG) {
            addNetworkInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
        }
        build()
    }

}