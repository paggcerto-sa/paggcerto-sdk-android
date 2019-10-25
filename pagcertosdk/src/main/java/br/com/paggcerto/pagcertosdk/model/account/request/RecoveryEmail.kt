package br.com.paggcerto.pagcertosdk.model.account.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RecoveryEmail: Serializable {
    @Expose
    @SerializedName("email")
    var email: String? = null

    @Expose
    @SerializedName("appUrl")
    var appUrl: String? = null
}