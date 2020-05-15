package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoriesProductList (

    @SerializedName("categoriesProduct") val categoriesProduct : List<CategoriesProduct>
): Serializable