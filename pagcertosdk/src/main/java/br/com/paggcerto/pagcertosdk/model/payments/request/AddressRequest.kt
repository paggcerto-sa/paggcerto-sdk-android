package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class AddressRequest: Serializable {
    @SerializedName("cityCode")
    @Expose
    var cityCode: String? = null
    @SerializedName("district")
    @Expose
    var district: String? = null
    @SerializedName("line1")
    @Expose
    var line1: String? = null
    @SerializedName("line2")
    @Expose
    var line2: String? = null
    @SerializedName("streetNumber")
    @Expose
    var streetNumber: String? = null
    @SerializedName("zipCode")
    @Expose
    var zipCode: String? = null
}