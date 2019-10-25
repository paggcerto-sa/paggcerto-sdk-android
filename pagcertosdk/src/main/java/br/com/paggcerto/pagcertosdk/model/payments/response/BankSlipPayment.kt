package br.com.paggcerto.pagcertosdk.model.payments.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
Copyright (c) 2019 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class BankSlipPayment (

	@SerializedName("sellingKey") val sellingKey : String,
	@SerializedName("id") val id : String,
	@SerializedName("method") val method : String,
	@SerializedName("status") val status : String,
	@SerializedName("createdAt") val createdAt : String,
	@SerializedName("completedAt") val completedAt : String?,
	@SerializedName("canceledAt") val canceledAt : String?,
	@SerializedName("note") val note : String,
	@SerializedName("firstDelayDate") val firstDelayDate : String?,
	@SerializedName("nextDueDate") val nextDueDate : String?,
	@SerializedName("amount") val amount : Number,
	@SerializedName("amountPaid") val amountPaid : Number,
	@SerializedName("cancelable") val cancelable : Boolean,
	@SerializedName("cardBrand") val cardBrand : String?,
	@SerializedName("installments") val installments : Number,
	@SerializedName("installmentValue") val installmentValue : Number,
	@SerializedName("installmentNumber") val installmentNumber : Number,
	@SerializedName("payerName") val payerName : String?,
	@SerializedName("payerTaxDocument") val payerTaxDocument : String?
): Serializable