package com.strikalov.exchangeratesofbanks

import com.strikalov.exchangeratesofbanks.ui.exchangerate.ExchangeRateFragment
import com.strikalov.exchangeratesofbanks.ui.info.InfoFragment
import com.strikalov.exchangeratesofbanks.ui.location.LocationFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object ExchangeRateScreen : SupportAppScreen() {
        override fun getFragment() = ExchangeRateFragment()
    }

    data class InfoScreen(val idBank: Int, val bankName: String, val bankLogoLink: String) : SupportAppScreen() {
        override fun getFragment() = InfoFragment.newInstance(idBank, bankName, bankLogoLink)
    }

    data class LocationScreen(val idBank: Int, val bankName: String) : SupportAppScreen() {
        override fun getFragment() = LocationFragment.newInstance(idBank, bankName)
    }
}