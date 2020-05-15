package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.SerializedName

data class CategoriesProductList (

    @SerializedName("categoriesProduct") val categoriesProduct : List<CategoriesProduct>
)