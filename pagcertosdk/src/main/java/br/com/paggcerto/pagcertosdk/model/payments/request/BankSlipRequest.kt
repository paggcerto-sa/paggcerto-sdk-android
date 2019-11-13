package br.com.paggcerto.pagcertosdk.model.payments.request

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class BankSlipRequest: Serializable {
    @SerializedName("sellingKey")
    @Expose
    var sellingKey: String? = null

    @SerializedName("dueDate")
    @Expose
    var dueDate: String? = null

    @SerializedName("amount")
    @Expose
    var amount: Double? = null

    @SerializedName("discount")
    @Expose
    var discount: Double? = null

    @SerializedName("fines")
    @Expose
    var fines: Double? = null

    @SerializedName("interest")
    @Expose
    var interest: Double? = null

    @SerializedName("discountDays")
    @Expose
    var discountDays: Int? = null

    @SerializedName("secondBankSlip")
    @Expose
    var secondBankSlip: Int? = null

    @SerializedName("acceptedUntil")
    @Expose
    var acceptedUntil: Int? = null

    @SerializedName("instructions")
    @Expose
    var instructions: String? = null
}