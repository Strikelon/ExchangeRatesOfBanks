package com.strikalov.exchangeratesofbanks.ui.exchangerate.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.strikalov.exchangeratesofbanks.entity.ExchangeRates

class BankExchangeRateController(
    var onCalculatorClickListener: (ExchangeRates.ExchangeRate) -> Unit,
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
                onCalculatorButtonClick { onCalculatorClickListener.invoke(exchangeRate) }
                onInfoButtonClick { onInfoClickListener.invoke(exchangeRate) }
                onLocationButtonClick { onLocationClickListener.invoke(exchangeRate) }
            }
        }

    }
}