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


data class BankSlipTransactionLink (

	@SerializedName("id") val id : String,
	@SerializedName("number") val number : Number,
	@SerializedName("paymentId") val paymentId : String,
	@SerializedName("sellingKey") val sellingKey : String,
	@SerializedName("payerName") val payerName : String,
	@SerializedName("payerEmail") val payerEmail : String,
	@SerializedName("payerTaxDocument") val payerTaxDocument : String,
	@SerializedName("dueDate") val dueDate : String,
	@SerializedName("discountLimitDate") val discountLimitDate : String,
	@SerializedName("discountValue") val discountValue : Number,
	@SerializedName("discount") val discount : Number,
	@SerializedName("bankSlipFee") val bankSlipFee : Number,
	@SerializedName("secondBankSlip") val secondBankSlip : Number,
	@SerializedName("fines") val fines : Number,
	@SerializedName("interest") val interest : Number,
	@SerializedName("createdAt") val createdAt : String,
	@SerializedName("autoCanceledAt") val autoCanceledAt : String,
	@SerializedName("payableUntil") val payableUntil : String,
	@SerializedName("paidAt") val paidAt : String?,
	@SerializedName("canceledAt") val canceledAt : String?,
	@SerializedName("cancelationNote") val cancelationNote : String?,
	@SerializedName("amount") val amount : Number,
	@SerializedName("amountPaid") val amountPaid : Number,
	@SerializedName("installmentNumber") val installmentNumber : Number,
	@SerializedName("status") val status : String,
	@SerializedName("barcode") val barcode : String,
	@SerializedName("instructions") val instructions : String?,
	@SerializedName("address") val address : Address,
	@SerializedName("timeline") val timeline : List<Timeline>,
	@SerializedName("issuer") val issuer : Issuer,
	@SerializedName("link") val link : String
): Serializable