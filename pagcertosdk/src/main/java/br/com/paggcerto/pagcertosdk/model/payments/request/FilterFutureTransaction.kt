package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class FilterFutureTransaction: Serializable {

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("splitterId")
    @Expose
    var splitterId: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("startDate")
    @Expose
    var startDate: String? = null

    @SerializedName("endDate")
    @Expose
    var endDate: String? = null

    @SerializedName("index")
    @Expose
    var index: Int? = null

    @SerializedName("length")
    @Expose
    var length: Int? = null

}