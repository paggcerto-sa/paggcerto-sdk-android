package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class FilterSplitterTransaction: Serializable {

    @SerializedName("splitterId")
    @Expose
    var splitterId: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("index")
    @Expose
    var index: Number? = null

    @SerializedName("length")
    @Expose
    var length: Number? = null
}