package br.com.paggcerto.pagcertosdk.model.payments.request

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class BankAccountRequest: Serializable {
    @SerializedName("holderName")
    @Expose
    var holderName: String? = null

    @SerializedName("taxDocument")
    @Expose
    var taxDocument: String? = null

    @SerializedName("bankNumber")
    @Expose
    var bankNumber: String? = null

    @SerializedName("accountNumber")
    @Expose
    var accountNumber: String? = null

    @SerializedName("bankBranchNumber")
    @Expose
    var bankBranchNumber: String? = null

    @SerializedName("variation")
    @Expose
    var variation: Any? = null

    @SerializedName("type")
    @Expose
    var type: String? = null
}