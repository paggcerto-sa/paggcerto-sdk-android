package br.com.paggcerto.pagcertosdk.model.payments.response

import br.com.paggcerto.pagcertosdk.model.payment_account.response.Timeline
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
Copyright (c) 2019 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class CardTransactionLink (

    @SerializedName("nsu") val nsu : Number,
    @SerializedName("paymentId") val paymentId : String,
    @SerializedName("sellingKey") val sellingKey : String,
    @SerializedName("cardBrand") val cardBrand : String,
    @SerializedName("cardFinal") val cardFinal : Number,
    @SerializedName("credit") val credit : Boolean,
    @SerializedName("online") val online : Boolean,
    @SerializedName("anticipated") val anticipated : Boolean,
    @SerializedName("preCapture") val preCapture : Boolean,
    @SerializedName("amountPaid") val amountPaid : Number,
    @SerializedName("installmentsNumber") val installmentsNumber : Number,
    @SerializedName("installmentValue") val installmentValue : Number,
    @SerializedName("installments") val installments : List<Installment>,
    @SerializedName("capturedAt") val capturedAt : String,
    @SerializedName("dueCaptureDate") val dueCaptureDate : String?,
    @SerializedName("canceledAt") val canceledAt : String?,
    @SerializedName("contestedOn") val contestedOn : String?,
    @SerializedName("reprovedAt") val reprovedAt : String?,
    @SerializedName("paidAt") val paidAt : String,
    @SerializedName("status") val status : String,
    @SerializedName("cardFee") val cardFee : Number,
    @SerializedName("anticipationFee") val anticipationFee : Number,
    @SerializedName("cardBrandFee") val cardBrandFee : Number,
    @SerializedName("cardBrandFeeDiscount") val cardBrandFeeDiscount : Number,
    @SerializedName("appliedFees") val appliedFees : Number,
    @SerializedName("cardBrandFeePagcerto") val cardBrandFeePagcerto : String?,
    @SerializedName("anticipationFeePagcerto") val anticipationFeePagcerto : String?,
    @SerializedName("timeline") val timeline : List<Timeline>,
    @SerializedName("issuer") val issuer : Issuer
): Serializable