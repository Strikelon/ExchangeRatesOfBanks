package com.strikalov.exchangeratesofbanks.entity

import com.google.gson.annotations.SerializedName

data class ExchangeRates (
    @SerializedName("exchange_rates") val exchangeRatesList: List<ExchangeRate>
) {
    data class ExchangeRate(
        @SerializedName("id_bank") val idBank: Int,
        @SerializedName("bank_name") val bankName: String,
        @SerializedName("bank_logo") val bankLogoLink: String,
        @SerializedName("dollar_purchase") val dollarPurchase: Double,
        @SerializedName("dollar_sale") val dollarSale: Double,
        @SerializedName("euro_purchase") val euroPurchase: Double,
        @SerializedName("euro_sale") val euroSale: Double
    )
}