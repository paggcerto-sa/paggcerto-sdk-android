package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SendReceipt: Serializable {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null
}