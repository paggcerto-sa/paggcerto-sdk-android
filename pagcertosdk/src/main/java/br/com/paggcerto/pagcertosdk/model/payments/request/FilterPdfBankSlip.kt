package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FilterPdfBankSlip(
    @SerializedName("ids") val ids: List<String>
): Serializable