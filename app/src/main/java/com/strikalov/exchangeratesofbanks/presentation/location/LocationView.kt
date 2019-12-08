package com.strikalov.exchangeratesofbanks.presentation.location

import androidx.annotation.StringRes
import com.strikalov.exchangeratesofbanks.entity.BankOffices
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface LocationView : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun showSnackBarMessage(@StringRes messageId : Int)

    fun showProgressBar(show: Boolean)

    fun showBankOffices(bankOfficesList: List<BankOffices.BankOffice>)

    fun setTitle(bankName: String)
}