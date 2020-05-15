package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.SerializedName

data class AddressCard (

    @SerializedName("name") val name : String,
    @SerializedName("line1") val line1 : String,
    @SerializedName("line2") val line2 : String?,
    @SerializedName("zipCode") val zipCode : String,
    @SerializedName("city") val city : String,
    @SerializedName("state") val state : String,
    @SerializedName("country") val country : String
)
