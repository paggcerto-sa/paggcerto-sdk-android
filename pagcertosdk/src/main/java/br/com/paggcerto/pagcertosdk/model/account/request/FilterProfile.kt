package br.com.paggcerto.pagcertosdk.model.account.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class FilterProfile: Serializable {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("index")
    @Expose
    var index: Int? = null

    @SerializedName("length")
    @Expose
    var length: Int? = null
}