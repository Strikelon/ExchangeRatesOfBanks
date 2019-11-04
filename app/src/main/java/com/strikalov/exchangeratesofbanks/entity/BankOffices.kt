package com.strikalov.exchangeratesofbanks.entity

import com.google.gson.annotations.SerializedName

data class BankOffices (

    @SerializedName("id_bank") val idBank: Int,
    @SerializedName("bank_offices") val bankOfficesList: List<BankOffice>

){
    data class BankOffice (
        @SerializedName("office_address") val officeAddress: String,
        @SerializedName("latitude") val latitude: Double,
        @SerializedName("longitude") val longitude: Double,
        @SerializedName("office_phone") val officePhone: String,
        @SerializedName("working_hours") val workingHoursList: List<WorkingTime>
    )

    data class WorkingTime (
        @SerializedName("working_time") val workingTime: String
    )
}
