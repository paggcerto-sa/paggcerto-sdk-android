package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class FilterTransactionRecord (
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String
): Serializable {
    @SerializedName("types") var types: List<String> = emptyList()
    @SerializedName("status") var status: List<String> = emptyList()
    @SerializedName("cardBrands") var cardBrands: String? = null
    @SerializedName("sellingChannels") var sellingChannels: List<String> = emptyList()
    @SerializedName("sellingKey") var sellingKey: String? = null
    @SerializedName("nsu") var nsu: Int? = null
    @SerializedName("paymentId") var paymentId: Int? = null
    @SerializedName("description") var description: String? = null
    @SerializedName("customerName") var customerName: String? = null
    @SerializedName("customerTaxDocument") var customerTaxDocument: String? = null
    @SerializedName("index") var index: Int = 0
    @SerializedName("length") var length: Int = 30
}