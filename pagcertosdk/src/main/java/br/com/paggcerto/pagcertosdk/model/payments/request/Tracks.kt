package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal class Tracks : Serializable {
    @SerializedName("number1")
    @Expose
    var number1: String? = null

    @SerializedName("number2")
    @Expose
    var number2: String? = null

    @SerializedName("number3")
    @Expose
    var number3: String? = null
}