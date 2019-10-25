package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class FilterComission: Serializable {
    @SerializedName("month")
    @Expose
    private var month: String? = null

    @SerializedName("year")
    @Expose
    private var year: String? = null
}