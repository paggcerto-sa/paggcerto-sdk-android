package br.com.paggcerto.pagcertosdk.model.warnings.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RecipientMessage (

    @SerializedName("idRecipient") val idRecipient: String,
    @SerializedName("idMessage") val idMessage: String,
    @SerializedName("readAt") val readAt: String,
    @SerializedName("hiddenAt") val hiddenAt: String?
): Serializable