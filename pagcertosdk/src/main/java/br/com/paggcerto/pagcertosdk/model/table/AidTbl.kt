package br.com.paggcerto.pagcertosdk.model.table

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

internal class AidTbl {

    @SerializedName("Aid")
    @Expose
    var aid: List<Aid>? = null

}