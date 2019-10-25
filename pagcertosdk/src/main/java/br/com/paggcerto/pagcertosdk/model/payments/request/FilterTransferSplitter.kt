package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class FilterTransferSplitter: FilterTransfer(), Serializable {
    @SerializedName("splittersId")
    @Expose
    var splittersId: List<String> = emptyList()

    @SerializedName("status")
    @Expose
    var status: String? = null
}