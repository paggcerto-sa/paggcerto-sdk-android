package br.com.paggcerto.pagcertosdk.model.table

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

internal class Tables {

    @SerializedName("AidTbl")
    @Expose
    var aidTbl: AidTbl? = null
    @SerializedName("CapkTable")
    @Expose
    var capkTable: CapkTable? = null

}