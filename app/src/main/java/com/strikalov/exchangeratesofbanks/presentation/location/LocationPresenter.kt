package com.strikalov.exchangeratesofbanks.presentation.location

import com.strikalov.exchangeratesofbanks.R
import com.strikalov.exchangeratesofbanks.entity.BankOffices
import com.strikalov.exchangeratesofbanks.model.BanksDataRepository
import com.strikalov.exchangeratesofbanks.presentation.BasePresenter
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class LocationPresenter @Inject constructor(
    private val banksDataRepository: BanksDataRepository,
    private val router: Router
): BasePresenter<LocationView>() {

    private var bankId: Int? = null

    fun onMapReady(idBank: Int) {
        Timber.tag("MyTag").i("[LocationPresenter] onMapReady id : $idBank")
        bankId = idBank
        viewState.showProgressBar(true)
        fetchBankOffices()
    }

    private fun fetchBankOffices() {
        bankId?.let { bankId ->
            banksDataRepository.getBankOffices(bankId)
                .doOnSuccess { viewState.showProgressBar(false) }
                .doOnError { viewState.showProgressBar(false) }
                .subscribe(
                    {
                        onFetchBankOfficesSuccess(it)
                    },
                    {
                        onFetchBankOfficesError(it)
                    }
                )
        }
    }

    private fun onFetchBankOfficesSuccess(bankOffices: BankOffices) {
        viewState.showBankOffices(bankOffices.bankOfficesList)
    }

    private fun onFetchBankOfficesError(throwable: Throwable) {
        viewState.showSnackBarMessage(R.string.network_connection_error)
    }


    fun onBackPressed() {
        router.exit()
    }

    fun onGetBankName(bankName: String) {
        viewState.setTitle(bankName)
    }
}