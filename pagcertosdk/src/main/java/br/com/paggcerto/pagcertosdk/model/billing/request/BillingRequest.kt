package br.com.paggcerto.pagcertosdk.model.billing.request

import br.com.paggcerto.pagcertosdk.model.billing.response.BillingDetails
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BillingRequest: Serializable {

        @SerializedName("fullName")
        @Expose
        var fullName: String? = null

        @SerializedName("document")
        @Expose
        var document: String? = null

        @SerializedName("phoneNumber")
        @Expose
        var phoneNumber: String? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("payment")
        @Expose
        var payment: String? = null

        @SerializedName("fixedInstallments")
        @Expose
        var fixedInstallments: Boolean? = null

        @SerializedName("maximumInstallments")
        @Expose
        var maximumInstallments: Number? = null

        @SerializedName("discountCard")
        @Expose
        var discountCard: Number? = null

        @SerializedName("discountBankSlip")
        @Expose
        var discountBankSlip: Number? = null

        @SerializedName("dueDate")
        @Expose
        var dueDate: String? = null

        @SerializedName("billingDetails")
        @Expose
        var billingDetails: List<BillingDetails> = emptyList()
}
