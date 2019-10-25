package br.com.paggcerto.pagcertosdk.model.warnings.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UnreadMessages(

    @SerializedName("messages") val messages: List<String> = emptyList()
): Serializable