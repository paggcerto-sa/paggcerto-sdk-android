package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class FilterFee: Serializable {
    @SerializedName("daysPinpad")
    @Expose
    var daysPinpad: Int = 0

    @SerializedName("daysOnline")
    @Expose
    var daysOnline: Int = 32

    @SerializedName("anticipated")
    @Expose
    var anticipated: Boolean = false
}