package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class FilterAnticipation: Serializable{
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
    var index: Number? = null

    @SerializedName("length")
    @Expose
    var length: Number? = null
}