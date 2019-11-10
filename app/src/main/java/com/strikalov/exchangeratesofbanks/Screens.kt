package com.strikalov.exchangeratesofbanks

import androidx.fragment.app.Fragment
import com.strikalov.exchangeratesofbanks.ui.exchangerate.ExchangeRateFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object ExchangeRateScreen : SupportAppScreen() {
        override fun getFragment() = ExchangeRateFragment()
    }

}