package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BankSlipsPay: Serializable {
    @SerializedName("splitters")
    @Expose
    var splitters: List<CapturePaymentSplitter> = emptyList()

    @SerializedName("note")
    @Expose
    var note: String? = null

    @SerializedName("payers")
    @Expose
    var payers: List<Payer> = emptyList()
}