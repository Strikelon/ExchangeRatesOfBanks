package com.strikalov.exchangeratesofbanks.presentation.exchangerate

import com.strikalov.exchangeratesofbanks.R
import com.strikalov.exchangeratesofbanks.entity.ExchangeRates
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
                    currentExchangeRatesList = it.exchangeRatesList
                    viewState.updateExchangeRates(it.exchangeRatesList)
                    viewState.showRecyclerView()
                },
                {
                    Timber.tag("MyTag").i("downloadExchangeRates() error : $it")
                    viewState.showSnackBarMessage(R.string.network_connection_error)
                }
            ).addToCompositeDisposable()
    }

    fun onCalculatorClick(exchangeRate: ExchangeRates.ExchangeRate) {
        Timber.tag("MyTag").i("onCalculatorClick : $exchangeRate")
    }

    fun onInfoClick(exchangeRate: ExchangeRates.ExchangeRate) {
        Timber.tag("MyTag").i("onInfoClick : $exchangeRate")
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

}