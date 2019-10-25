package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class FilterSplitter: Serializable {

    @SerializedName("splitterId")
    @Expose
    var splitterId: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("nsu")
    @Expose
    var nsu: Number? = null

    @SerializedName("startDate")
    @Expose
    var startDate: String? = null

    @SerializedName("endDate")
    @Expose
    var endDate: String? = null

    @SerializedName("index")
    @Expose
    var index: Number? = null

    @SerializedName("length")
    @Expose
    var length: Number? = null
}