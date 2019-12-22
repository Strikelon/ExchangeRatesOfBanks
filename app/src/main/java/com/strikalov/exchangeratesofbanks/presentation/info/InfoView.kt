package com.strikalov.exchangeratesofbanks.presentation.info

import androidx.annotation.StringRes
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface InfoView : MvpView {

    fun showBankName(bankName: String)

    fun showBankLogo(bankLogoLink: String)

    fun showProgressBar()

    fun hideProgressBar()

    fun showInfoPanel(site: String, phoneNumber: String, address: String)

    fun hideInfoPanel()

    @StateStrategyType(SkipStrategy::class)
    fun showSnackBarMessage(@StringRes messageId : Int)
}