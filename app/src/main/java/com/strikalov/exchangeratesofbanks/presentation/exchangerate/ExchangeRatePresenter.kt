package com.strikalov.exchangeratesofbanks.presentation.exchangerate

import com.strikalov.exchangeratesofbanks.R
import com.strikalov.exchangeratesofbanks.Screens
import com.strikalov.exchangeratesofbanks.entity.ActionEnum
import com.strikalov.exchangeratesofbanks.entity.CurrencyEnum
import com.strikalov.exchangeratesofbanks.entity.ExchangeRates
import com.strikalov.exchangeratesofbanks.entity.ExtremumEnum
import com.strikalov.exchangeratesofbanks.model.BanksDataRepository
import com.strikalov.exchangeratesofbanks.presentation.BasePresenter
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class ExchangeRatePresenter @Inject constructor(
    private val banksDataRepository: BanksDataRepository,
    private val router: Router
) : BasePresenter<ExchangeRateView>() {

    private var currentExchangeRatesList = listOf<ExchangeRates.ExchangeRate>()
    private var isSortingControllerOpen = false

    private fun selectorDollarSale(exchangeRate : ExchangeRates.ExchangeRate): Double = exchangeRate.dollarSale
    private fun selectorDollarPurchase(exchangeRate : ExchangeRates.ExchangeRate): Double = exchangeRate.dollarPurchase
    private fun selectorEuroSale(exchangeRate : ExchangeRates.ExchangeRate): Double = exchangeRate.euroSale
    private fun selectorEuroPurchase(exchangeRate : ExchangeRates.ExchangeRate): Double = exchangeRate.euroPurchase
    private fun selectorById(exchangeRate : ExchangeRates.ExchangeRate): Int = exchangeRate.idBank

    private var currencySort : CurrencyEnum = CurrencyEnum.NONE
    private var actionSort : ActionEnum = ActionEnum.NONE
    private var extremumSort : ExtremumEnum = ExtremumEnum.NONE

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        downloadExchangeRates()
    }

    private fun downloadExchangeRates() {
        banksDataRepository.getExchangeRates()
            .doOnSubscribe { viewState.showProgressBar() }
            .doOnSuccess { viewState.hideProgressBar() }
            .doOnError { viewState.hideProgressBar() }
            .subscribe(
                {
                    downloadExchangeRatesSuccess(it)
                },
                {
                    downloadExchangeRatesError(it)
                }
            ).addToCompositeDisposable()
    }

    private fun downloadExchangeRatesSuccess(exchangeRates: ExchangeRates) {
        currentExchangeRatesList = exchangeRates.exchangeRatesList
        showRecyclerView()
    }

    private fun downloadExchangeRatesError(throwable: Throwable) {
        Timber.tag("MyTag").i("downloadExchangeRates() error : $throwable")
        viewState.showSnackBarMessage(R.string.network_connection_error)
    }

    private fun showRecyclerView() {
        if (currencySort != CurrencyEnum.NONE) {
            sortCurrentExchangeRatesList()
        } else {
            currentExchangeRatesList = currentExchangeRatesList.sortedBy { selectorById(it) }
        }

        viewState.updateExchangeRates(currentExchangeRatesList)
        viewState.showBankSorting()
        viewState.showRecyclerView()
    }

    private fun sortCurrentExchangeRatesList() {
        currentExchangeRatesList = when (currencySort) {
            CurrencyEnum.DOLLAR -> {
                when (actionSort) {
                    ActionEnum.SALE -> {
                        when (extremumSort) {
                            ExtremumEnum.MIN -> {
                                currentExchangeRatesList.sortedBy {selectorDollarSale(it)}
                            }
                            ExtremumEnum.MAX -> {
                                currentExchangeRatesList.sortedByDescending{selectorDollarSale(it)}
                            }
                            ExtremumEnum.NONE -> {
                                currentExchangeRatesList
                            }
                        }
                    }
                    ActionEnum.PURCHASE -> {
                        when (extremumSort) {
                            ExtremumEnum.MIN -> {
                                currentExchangeRatesList.sortedBy {selectorDollarPurchase(it)}
                            }
                            ExtremumEnum.MAX -> {
                                currentExchangeRatesList.sortedByDescending{selectorDollarPurchase(it)}
                            }
                            ExtremumEnum.NONE -> {
                                currentExchangeRatesList
                            }
                        }
                    }
                    ActionEnum.NONE -> {
                        currentExchangeRatesList
                    }
                }
            }
            CurrencyEnum.EURO -> {
                when (actionSort) {
                    ActionEnum.SALE -> {
                        when (extremumSort) {
                            ExtremumEnum.MIN -> {
                                currentExchangeRatesList.sortedBy {selectorEuroSale(it)}
                            }
                            ExtremumEnum.MAX -> {
                                currentExchangeRatesList.sortedByDescending{selectorEuroSale(it)}
                            }
                            ExtremumEnum.NONE -> {
                                currentExchangeRatesList
                            }
                        }
                    }
                    ActionEnum.PURCHASE -> {
                        when (extremumSort) {
                            ExtremumEnum.MIN -> {
                                currentExchangeRatesList.sortedBy {selectorEuroPurchase(it)}
                            }
                            ExtremumEnum.MAX -> {
                                currentExchangeRatesList.sortedByDescending{selectorEuroPurchase(it)}
                            }
                            ExtremumEnum.NONE -> {
                                currentExchangeRatesList
                            }
                        }
                    }
                    ActionEnum.NONE -> {
                        currentExchangeRatesList
                    }
                }
            }
            CurrencyEnum.NONE -> {
                currentExchangeRatesList
            }
        }
    }

    fun onCalculatorClick(exchangeRate: ExchangeRates.ExchangeRate) {
        Timber.tag("MyTag").i("onCalculatorClick : $exchangeRate")
    }

    fun onInfoClick(exchangeRate: ExchangeRates.ExchangeRate) {
        Timber.tag("MyTag").i("onInfoClick : $exchangeRate")
        router.navigateTo(Screens.InfoScreen(exchangeRate.idBank))
    }

    fun onLocationClick(exchangeRate: ExchangeRates.ExchangeRate) {
        Timber.tag("MyTag").i("onLocationClick : $exchangeRate")
    }

    fun onBackPressed() {
        router.exit()
    }

    fun onScrollUpFabButtonClick() {
        viewState.scrollRecyclerViewToPosition(0)
    }

    fun onRefreshExchangeRate() {
        viewState.hideRecyclerView()
        viewState.hideBankSorting()
        downloadExchangeRates()
    }

    fun onSortingControllerButtonClick() {
        if (isSortingControllerOpen) {
            isSortingControllerOpen = false
            viewState.closeSortingController()
        } else {
            isSortingControllerOpen = true
            viewState.openSortingController()
        }
    }

    fun onSortButtonClick(currencyEnum : CurrencyEnum, actionEnum : ActionEnum, extremumEnum : ExtremumEnum) {
        Timber.tag("ExchangeRatePresenter").i("onSortButtonClick() : $currencyEnum, $actionEnum $extremumEnum")
        currencySort = currencyEnum
        actionSort = actionEnum
        extremumSort = extremumEnum
        showRecyclerView()
    }

    fun onCancelSortButtonClick() {
        Timber.tag("ExchangeRatePresenter").i("onCancelSortButtonClick()")
        currencySort = CurrencyEnum.NONE
        actionSort = ActionEnum.NONE
        extremumSort = ExtremumEnum.NONE
        viewState.resetSortRadioButtons()
        showRecyclerView()
    }

}