package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ShoppingCarts (

    @SerializedName("code") val code : String?,
    @SerializedName("sku") val sku : String?,
    @SerializedName("category") val category : Int,
    @SerializedName("name") val name : String?,
    @SerializedName("description") val description : String?,
    @SerializedName("unitCost") val unitCost : Double,
    @SerializedName("discount") val discount : Int?,
    @SerializedName("quantity") val quantity : Int,
    @SerializedName("createdAt") val createdAt : String?
): Serializable