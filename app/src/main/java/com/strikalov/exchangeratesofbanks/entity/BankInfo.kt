package com.strikalov.exchangeratesofbanks.entity

import com.google.gson.annotations.SerializedName

data class BankInfo (
    @SerializedName("id_bank") val idBank: Int,
    @SerializedName("bank_name") val bankName: String,
    @SerializedName("bank_logo") val bankLogoLink: String,
    @SerializedName("bank_site") val bankSiteLink: String,
    @SerializedName("bank_phone") val bankPhone: String,
    @SerializedName("bank_address") val bankAddress: String
)