package br.com.paggcerto.pagcertosdk.model.billing.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
Copyright (c) 2019 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Billing (

    @SerializedName("sellingKey") val sellingKey : String?,
    @SerializedName("id") val id : String,
    @SerializedName("created") val created : String,
    @SerializedName("updated") val updated : String?,
    @SerializedName("fullName") val fullName : String,
    @SerializedName("document") val document : Number,
    @SerializedName("phoneNumber") val phoneNumber : Number,
    @SerializedName("email") val email : String,
    @SerializedName("url") val url : String,
    @SerializedName("payment") val payment : String,
    @SerializedName("status") val status : String,
    @SerializedName("fixedInstallments") val fixedInstallments : Boolean,
    @SerializedName("maximumInstallments") val maximumInstallments : Number,
    @SerializedName("discountCard") val discountCard : Number,
    @SerializedName("totalDiscountCard") val totalDiscountCard : Number,
    @SerializedName("discountBankSlip") val discountBankSlip : Number,
    @SerializedName("totalDiscountBankSlip") val totalDiscountBankSlip : Number,
    @SerializedName("amount") val amount : Number,
    @SerializedName("dueDate") val dueDate : String,
    @SerializedName("smsSent") val smsSent : Number,
    @SerializedName("billingDetails") val billingDetails : List<BillingDetails>,
    @SerializedName("billingEvents") val billingEvents : List<BillingEvents>
): Serializable