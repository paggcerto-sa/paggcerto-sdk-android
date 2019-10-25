package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class FilterCards: Serializable {
    @SerializedName("brands")
    @Expose
    var brands: List<String> = emptyList()

    @SerializedName("ids")
    @Expose
    var ids: List<String> = emptyList()

    @SerializedName("finals")
    @Expose
    var finals: List<String> = emptyList()

    @SerializedName("index")
    @Expose
    var index: Number? = null

    @SerializedName("length")
    @Expose
    var length: Number? = null
}