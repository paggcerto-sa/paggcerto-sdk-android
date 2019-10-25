package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class FilterBankSlips: Serializable {
    @SerializedName("ids")
    @Expose
    var ids: List<String> = emptyList()

    @SerializedName("numbers")
    @Expose
    var numbers: List<String> = emptyList()

    @SerializedName("status")
    @Expose
    var status: List<String> = emptyList()

    @SerializedName("createdFrom")
    @Expose
    var createdFrom: String? = null

    @SerializedName("createdUntil")
    @Expose
    var createdUntil: String? = null

    @SerializedName("paidFrom")
    @Expose
    var paidFrom: String? = null

    @SerializedName("paidUntil")
    @Expose
    var paidUntil: String? = null

    @SerializedName("dueDateFrom")
    @Expose
    var dueDateFrom: String? = null

    @SerializedName("dueDateUntil")
    @Expose
    var dueDateUntil: String? = null

    @SerializedName("index")
    @Expose
    var index: Int? = null

    @SerializedName("length")
    @Expose
    var length: Int? = null
}