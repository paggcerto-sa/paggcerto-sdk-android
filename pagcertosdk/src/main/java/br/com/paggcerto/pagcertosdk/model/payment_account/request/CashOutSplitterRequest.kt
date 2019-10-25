package br.com.paggcerto.pagcertosdk.model.payment_account.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CashOutSplitterRequest(
    @SerializedName("splitterId") val splitterId : String,
    @SerializedName("amount") val amount : Double): Serializable {
    @SerializedName("note") var note : String? = null
}