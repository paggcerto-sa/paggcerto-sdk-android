package br.com.paggcerto.pagcertosdk.model.payments.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Bill(

    @SerializedName("id") val id: String,
    @SerializedName("amount") val amount : Number,
    @SerializedName("salesQuantity") val salesQuantity: Number,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("dueDate") val dueDate: String,
    @SerializedName("paidAt") val paidAt: String?,
    @SerializedName("billingId") val billingId: String,
    @SerializedName("nextClosingDate") val nextClosingDate: String?
): Serializable