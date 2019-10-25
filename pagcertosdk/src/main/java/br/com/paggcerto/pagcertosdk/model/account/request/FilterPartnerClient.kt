package br.com.paggcerto.pagcertosdk.model.account.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class FilterPartnerClient: Serializable {
    open class FilterProfile: Serializable {
        @SerializedName("fullName")
        @Expose
        var fullName: String? = null

        @SerializedName("taxDocument")
        @Expose
        var taxDocument: String? = null

        @SerializedName("index")
        @Expose
        var index: Int? = null

        @SerializedName("length")
        @Expose
        var length: Int? = null
    }
}