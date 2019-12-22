package com.strikalov.exchangeratesofbanks.model.server

import com.strikalov.exchangeratesofbanks.entity.BankInfo
import com.strikalov.exchangeratesofbanks.entity.BankOffices
import com.strikalov.exchangeratesofbanks.entity.ExchangeRates
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("getExchangeRates")
    fun getExchangeRates(): Single<ExchangeRates>

    @GET("getBankInfo/{id_bank}")
    fun getBankInfo(@Path("id_bank") idBank: Int): Single<BankInfo>

    @GET("getBankOffices/{id_bank}")
    fun getBankOffices(@Path("id_bank") idBank: Int): Single<BankOffices>
}