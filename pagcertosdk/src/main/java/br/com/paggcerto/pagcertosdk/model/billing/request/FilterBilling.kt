package br.com.paggcerto.pagcertosdk.model.billing.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class FilterBilling: Serializable{
    @SerializedName("status")
    @Expose
    var status: List<String> = emptyList()

    @SerializedName("payment")
    @Expose
    var payment: List<String> = emptyList()

    @SerializedName("startDate")
    @Expose
    var startDate: String? = null

    @SerializedName("endDate")
    @Expose
    var endDate: String? = null

    @SerializedName("document")
    @Expose
    var document: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("index")
    @Expose
    var index: Int? = null

    @SerializedName("length")
    @Expose
    var length: Int? = null
}