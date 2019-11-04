package com.strikalov.exchangeratesofbanks.model.server

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody

class EncodingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val modifiedBody = ResponseBody.create(mediaType, response.body()!!.bytes())
        val modifiedResponse = response.newBuilder()
            .body(modifiedBody)
            .build()

        return modifiedResponse
    }

}