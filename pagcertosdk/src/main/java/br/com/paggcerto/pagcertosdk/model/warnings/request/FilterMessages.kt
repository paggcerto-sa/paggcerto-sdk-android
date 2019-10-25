package br.com.paggcerto.pagcertosdk.model.warnings.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class FilterMessages: Serializable {
    @SerializedName("index")
    @Expose
    var index: Int = 0

    @SerializedName("length")
    @Expose
    var length: Int = 0
}