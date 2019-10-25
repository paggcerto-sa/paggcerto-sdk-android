package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal class Metadata : Serializable{
    @SerializedName("sequenceNumber")
    @Expose
    var sequenceNumber: Int? = null

    @SerializedName("emvData")
    @Expose
    var emvData: String? = null

    @SerializedName("tracks")
    @Expose
    var tracks: Tracks? = null

    @SerializedName("pinOnline")
    @Expose
    var pinOnline: Boolean? = null

    @SerializedName("pin")
    @Expose
    var pin: String? = null

    @SerializedName("serial")
    @Expose
    var serial: String? = null

    @SerializedName("chip")
    @Expose
    var chip: Boolean? = null
}