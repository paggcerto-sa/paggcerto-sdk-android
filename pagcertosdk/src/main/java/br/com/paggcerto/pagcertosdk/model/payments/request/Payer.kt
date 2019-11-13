package br.com.paggcerto.pagcertosdk.model.payments.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import com.google.gson.annotations.Expose

class Payer: Serializable{
	@SerializedName("sellingKey")
	@Expose
	var sellingKey: String? = null
	@SerializedName("name")
	@Expose
	var name: String? = null
	@SerializedName("email")
	@Expose
	var email: String? = null
	@SerializedName("mobile")
	@Expose
	var mobile: String? = null
	@SerializedName("taxDocument")
	@Expose
	var taxDocument: String? = null
	@SerializedName("address")
	@Expose
	var address: AddressRequest? = null
	@SerializedName("bankSlips")
	@Expose
	var bankSlips: List<BankSlipRequest> = emptyList()
}