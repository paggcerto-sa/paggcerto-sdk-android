package br.com.paggcerto.pagcertosdk.model.table

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

internal class CapkTable {

    @SerializedName("Capk")
    @Expose
    var capk: List<Capk>? = null

}
