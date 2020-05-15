package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoriesProduct (

    @SerializedName("code") val code : Int,
    @SerializedName("name") val name : String,
    @SerializedName("group") val group : String
): Serializable