package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.SerializedName

data class CategoriesProduct (

    @SerializedName("code") val code : Int,
    @SerializedName("name") val name : String,
    @SerializedName("group") val group : String
)