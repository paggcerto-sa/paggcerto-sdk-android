package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Simulation: Serializable {
    @SerializedName("amount")
    @Expose
    var amount: Number? = null

    @SerializedName("customerPaysFee")
    @Expose
    var customerPaysFee: Boolean? = null

    @SerializedName("installments")
    @Expose
    var installments: Number? = null

    @SerializedName("credit")
    @Expose
    var credit: Boolean? = null

    @SerializedName("pinpad")
    @Expose
    var pinpad: Boolean? = null

    @SerializedName("cardBrand")
    @Expose
    var cardBrand: String? = null
}