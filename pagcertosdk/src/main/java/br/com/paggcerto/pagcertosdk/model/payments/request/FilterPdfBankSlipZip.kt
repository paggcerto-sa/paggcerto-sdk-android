package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FilterPdfBankSlipZip(
    @SerializedName("payments") val paymentsId: List<String>
): Serializable