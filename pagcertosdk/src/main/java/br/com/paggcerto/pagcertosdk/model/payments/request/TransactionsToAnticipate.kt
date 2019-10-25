package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TransactionsToAnticipate: Serializable {
    @SerializedName("cardTransactions")
    @Expose
    var cardTransactions: List<Long> = emptyList()
}