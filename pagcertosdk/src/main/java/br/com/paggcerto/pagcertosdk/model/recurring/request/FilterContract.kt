package br.com.paggcerto.pagcertosdk.model.recurring.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class FilterContract: Serializable{

    @SerializedName("startDate")
    @Expose
    var startDate: String? = null

    @SerializedName("endDate")
    @Expose
    var endDate: String? = null

    @SerializedName("serviceKey")
    @Expose
    var serviceKey: String? = null

    @SerializedName("serviceDescription")
    @Expose
    var serviceDescription: String? = null

    @SerializedName("customerName")
    @Expose
    var customerName: String? = null

    @SerializedName("customerTaxDocument")
    @Expose
    var customerTaxDocument: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("paymentMethod")
    @Expose
    var paymentMethod: String? = null

    @SerializedName("createdAtOrAfter")
    @Expose
    var createdAtOrAfter: String? = null

    @SerializedName("createdAtOrBefore")
    @Expose
    var createdAtOrBefore: String? = null

    @SerializedName("expiredAtOrAfter")
    @Expose
    var expiredAtOrAfter: String? = null

    @SerializedName("contractId")
    @Expose
    var contractId: String? = null

    @SerializedName("expiredAtOrBefore")
    @Expose
    var expiredAtOrBefore: String? = null

    @SerializedName("active")
    @Expose
    var active: Boolean? = null

    @SerializedName("paused")
    @Expose
    var paused: Boolean? = null

    @SerializedName("canceled")
    @Expose
    var canceled: Boolean? = null

    @SerializedName("defaulted")
    @Expose
    var defaulted: Boolean? = null

    @SerializedName("index")
    @Expose
    var index: Int? = null

    @SerializedName("length")
    @Expose
    var length: Int? = null
}