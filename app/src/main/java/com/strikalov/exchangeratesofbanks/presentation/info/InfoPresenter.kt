package com.strikalov.exchangeratesofbanks.presentation.info

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

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.checkInit()
    }

    fun onBackPressed() {
        router.exit()
    }

}