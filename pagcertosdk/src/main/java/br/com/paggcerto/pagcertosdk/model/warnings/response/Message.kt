package br.com.paggcerto.pagcertosdk.model.warnings.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Message (

    @SerializedName("idMessage") val idMessage: String,
    @SerializedName("idGroup") val idGroup: String?,
    @SerializedName("idRecipient") val idRecipient: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("recipientMessage") val recipientMessage: RecipientMessage?
): Serializable