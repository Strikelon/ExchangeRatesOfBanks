package com.strikalov.exchangeratesofbanks.presentation.exchangerate

import androidx.annotation.StringRes
import com.strikalov.exchangeratesofbanks.entity.ExchangeRates
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType


@StateStrategyType(AddToEndSingleStrategy::class)
interface ExchangeRateView : MvpView {

    fun showProgressBar()

    fun hideProgressBar()

    fun showRecyclerView()

    fun hideRecyclerView()

    fun showBankSorting()

    fun hideBankSorting()

    fun updateExchangeRates(data: List<ExchangeRates.ExchangeRate>)

    @StateStrategyType(SkipStrategy::class)
    fun showSnackBarMessage(@StringRes messageId : Int)

    fun scrollRecyclerViewToPosition(position: Int)

    fun openSortingController()

    fun closeSortingController()

    @StateStrategyType(SkipStrategy::class)
    fun resetSortRadioButtons()
}