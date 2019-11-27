package com.strikalov.exchangeratesofbanks

import androidx.fragment.app.Fragment
import com.strikalov.exchangeratesofbanks.ui.exchangerate.ExchangeRateFragment
import com.strikalov.exchangeratesofbanks.ui.info.InfoFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object ExchangeRateScreen : SupportAppScreen() {
        override fun getFragment() = ExchangeRateFragment()
    }

    data class InfoScreen(val idBank: Int, val bankName: String, val bankLogoLink: String) : SupportAppScreen() {
        override fun getFragment() = InfoFragment.newInstance(idBank, bankName, bankLogoLink)
    }

}