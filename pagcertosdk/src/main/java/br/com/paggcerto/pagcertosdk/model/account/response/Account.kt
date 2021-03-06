package br.com.paggcerto.pagcertosdk.model.account.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
Copyright (c) 2019 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Account (

	@SerializedName("active") val active : Boolean,
	@SerializedName("saleOnline") val saleOnline : Boolean,
	@SerializedName("approved") val approved : Boolean,
	@SerializedName("reproved") val reproved : Boolean,
	@SerializedName("freeTrial") val freeTrial : Boolean,
	@SerializedName("balanceBlocked") val balanceBlocked : Boolean,
	@SerializedName("amountBlocked") val amountBlocked : Number?,
	@SerializedName("anticipatedTransfer") val anticipatedTransfer : Boolean,
	@SerializedName("oldAnticipationPlan") val oldAnticipationPlan : Boolean,
	@SerializedName("isOriginPartner") val isOriginPartner : Boolean,
	@SerializedName("partnerId") val partnerId : String?,
	@SerializedName("logo") val logo : String,
	@SerializedName("vanBanese") val vanBanese : Number,
	@SerializedName("commercialName") val commercialName : String?,
	@SerializedName("softDescriptor") val softDescriptor : String,
	@SerializedName("blockedPaymentMethod") val blockedPaymentMethod : String?,
	@SerializedName("transferPlan") val transferPlan : TransferPlan,
	@SerializedName("maxCardTransactionValueAllowed") val maxCardTransactionValueAllowed : Number?

): Serializable