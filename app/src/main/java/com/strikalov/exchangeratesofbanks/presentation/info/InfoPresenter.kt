package com.strikalov.exchangeratesofbanks.presentation.info

import com.strikalov.exchangeratesofbanks.R
import com.strikalov.exchangeratesofbanks.entity.BankInfo
import com.strikalov.exchangeratesofbanks.model.BanksDataRepository
import com.strikalov.exchangeratesofbanks.presentation.BasePresenter
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class InfoPresenter @Inject constructor(
    private val banksDataRepository: BanksDataRepository,
    private val router: Router
) : BasePresenter<InfoView>() {

    private var idBank: Int? = null
    private var bankName: String? = null
    private var bankLogoLink: String? = null

    fun onGetIdBank(id: Int) {
        idBank = id
        idBank?.let {
            banksDataRepository.getBankInfo(it)
                .doOnSubscribe { viewState.showProgressBar() }
                .doOnSuccess { viewState.hideProgressBar() }
                .doOnError { viewState.hideProgressBar() }
                .subscribe(
                    {
                        onGetBankInfoSuccess(it)
                    },
                    {
                        onGetBankInfoError(it)
                    }
                ).addToCompositeDisposable()
        }
    }

    private fun onGetBankInfoSuccess(bankInfo: BankInfo) {
        viewState.showInfoPanel(bankInfo.bankSiteLink, bankInfo.bankPhone, bankInfo.bankAddress)
    }

    private fun onGetBankInfoError(throwable: Throwable) {
        viewState.showSnackBarMessage(R.string.network_connection_error)
    }


    fun onGetBankName(name: String) {
        bankName = name
        bankName?.let {
            viewState.showBankName(it)
        }
    }

    fun onGetBankLogoLink(link: String) {
        bankLogoLink = link
        bankLogoLink?.let {
            viewState.showBankLogo(it)
        }
    }

    fun onBackPressed() {
        router.exit()
    }

}