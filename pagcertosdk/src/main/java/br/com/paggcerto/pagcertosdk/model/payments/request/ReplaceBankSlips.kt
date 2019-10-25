package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ReplaceBankSlips: Serializable {
    @SerializedName("discount")
    @Expose
    var discount: Number? = null

    @SerializedName("fines")
    @Expose
    var fines: Number? = null

    @SerializedName("interest")
    @Expose
    var interest: Number? = null

    @SerializedName("discountDays")
    @Expose
    var discountDays: Number? = null

    @SerializedName("acceptedUntil")
    @Expose
    var acceptedUntil: Number? = null

    @SerializedName("dueDate")
    @Expose
    var dueDate: String? = null

    @SerializedName("instructions")
    @Expose
    var instructions: String? = null
}