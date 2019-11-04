package com.strikalov.exchangeratesofbanks.model

import com.strikalov.exchangeratesofbanks.model.server.Api
import com.strikalov.exchangeratesofbanks.system.SchedulersProvider
import javax.inject.Inject

class BanksDataRepository @Inject constructor(
    private val api: Api,
    private val schedulers: SchedulersProvider
){

    fun getExchangeRates() = api.getExchangeRates()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun getBankInfo(idBank: Int) = api.getBankInfo(idBank)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun getBankOffices(idBank: Int) = api.getBankOffices(idBank)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
}