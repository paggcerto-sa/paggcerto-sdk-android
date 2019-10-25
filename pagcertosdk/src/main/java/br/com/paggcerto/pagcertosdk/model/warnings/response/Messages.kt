package br.com.paggcerto.pagcertosdk.model.warnings.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Messages(

    @SerializedName("messages") val messages: List<Message> = emptyList(),
    @SerializedName("count") val count: Int
): Serializable