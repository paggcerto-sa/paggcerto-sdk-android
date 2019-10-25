package br.com.paggcerto.pagcertosdk.model.payments.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Bills(

    @SerializedName("current") val current: Bill?,
    @SerializedName("next") val next: Bill?,
    @SerializedName("invoices") val invoices: List<Bill> = emptyList(),
    @SerializedName("count") val count: Number
): Serializable