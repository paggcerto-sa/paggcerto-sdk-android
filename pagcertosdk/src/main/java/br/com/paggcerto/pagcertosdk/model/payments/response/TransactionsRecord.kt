package br.com.paggcerto.pagcertosdk.model.payments.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TransactionsRecord (

    @SerializedName("date") var date : String,
    @SerializedName("records") val records : List<Record>
): Serializable