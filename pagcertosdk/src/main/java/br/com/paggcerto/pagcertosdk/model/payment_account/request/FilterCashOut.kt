package br.com.paggcerto.pagcertosdk.model.payment_account.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class FilterCashOut: Serializable {
    @SerializedName("splitterId")
    @Expose
    var splitterId: String? = null

     @SerializedName("startDate")
    @Expose
    var startDate: String? = null

    @SerializedName("endDate")
    @Expose
    var endDate: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("index")
    @Expose
    var index: Int? = null

    @SerializedName("length")
    @Expose
    var length: Int? = null
}