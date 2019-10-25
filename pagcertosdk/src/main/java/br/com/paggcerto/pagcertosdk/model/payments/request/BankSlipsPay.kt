package br.com.paggcerto.pagcertosdk.model.payments.request

import br.com.paggcerto.pagcertosdk.model.payments.response.Payer
import br.com.paggcerto.pagcertosdk.model.payments.response.Splitters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BankSlipsPay: Serializable {
    @SerializedName("splitters")
    @Expose
    var splitters: List<Splitters> = emptyList()

    @SerializedName("note")
    @Expose
    var note: String? = null

    @SerializedName("payers")
    @Expose
    var payers: List<Payer> = emptyList()
}