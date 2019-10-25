package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PayCard : Serializable{
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("holderName")
    @Expose
    var holderName: String? = null

    @SerializedName("number")
    @Expose
    var number: String? = null

    @SerializedName("expirationMonth")
    @Expose
    var expirationMonth: Int? = null

    @SerializedName("expirationYear")
    @Expose
    var expirationYear: Int? = null

    @SerializedName("brand")
    @Expose
    var brand: String? = null

    @SerializedName("credit")
    @Expose
    var credit: Boolean = true

    @SerializedName("amountPaid")
    @Expose
    var amountPaid: Double? = null

    @SerializedName("installments")
    @Expose
    var installments: Int? = 1

    @SerializedName("metadata")
    @Expose
    internal var metadata: Metadata? = null

    @SerializedName("securityCode")
    @Expose
    var securityCode: String? = null

    @SerializedName("sellingKey")
    @Expose
    var sellingKey: String? = null
}