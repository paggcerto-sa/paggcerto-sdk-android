package br.com.paggcerto.pagcertosdk.model.payment_account.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class FilterStatement: Serializable {
    @SerializedName("startDate")
    @Expose
    var startDate: String? = null

    @SerializedName("endDate")
    @Expose
    var endDate: String? = null

    @SerializedName("movementType")
    @Expose
    var movementType: String? = null

    @SerializedName("index")
    @Expose
    var index: Int? = null

    @SerializedName("length")
    @Expose
    var length: Int? = null
}