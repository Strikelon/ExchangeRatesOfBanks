package com.strikalov.exchangeratesofbanks.ui.exchangerate.epoxy

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.strikalov.exchangeratesofbanks.entity.ExchangeRates

class BankExchangeRateController(
    var onCalculatorClickListener: (ExchangeRates.ExchangeRate, View) -> Unit,
    var onInfoClickListener: (ExchangeRates.ExchangeRate) -> Unit,
    var onLocationClickListener: (ExchangeRates.ExchangeRate) -> Unit
) : TypedEpoxyController<List<ExchangeRates.ExchangeRate>>() {

    override fun buildModels(data: List<ExchangeRates.ExchangeRate>) {

        for( exchangeRate in data) {
            bankExchangeRateView {
                id(exchangeRate.idBank)
                bankName(exchangeRate.bankName)
                saleUsdVal(exchangeRate.dollarSale.toString())
                saleEurVal(exchangeRate.euroSale.toString())
                purchaseUsdVal(exchangeRate.dollarPurchase.toString())
                purchaseEurVal(exchangeRate.euroPurchase.toString())
                logoUrl(exchangeRate.bankLogoLink)
                onCalculatorButtonClick { view ->
                    onCalculatorClickListener.invoke(exchangeRate, view) }
                onInfoButtonClick { onInfoClickListener.invoke(exchangeRate) }
                onLocationButtonClick { onLocationClickListener.invoke(exchangeRate) }
            }
        }

    }
}