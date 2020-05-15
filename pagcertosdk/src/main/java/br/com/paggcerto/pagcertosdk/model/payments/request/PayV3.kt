package br.com.paggcerto.pagcertosdk.model.payments.request

import br.com.paggcerto.pagcertosdk.model.payments.response.Geolocation
import br.com.paggcerto.pagcertosdk.model.payments.response.MobileDevice
import br.com.paggcerto.pagcertosdk.model.payments.response.PaymentDevice
import br.com.paggcerto.pagcertosdk.model.payments.response.Splitters
import br.com.paggcerto.pagcertosdk.model.recurring.request.Customer
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PayV3 {
    @SerializedName("sellingKey")
    @Expose
    var sellingKey: String? = null

    @SerializedName("note")
    @Expose
    var note: String? = null

    @SerializedName("amount")
    @Expose
    var amount: Number? = null

    @SerializedName("antifraud")
    @Expose
    var antiFraud: Boolean = false

    @SerializedName("riskNotAssumed")
    @Expose
    var riskNotAssumed: String? = null

    @SerializedName("cards")
    @Expose
    var cards: List<PayCard> = emptyList()

    @SerializedName("customer")
    @Expose
    var customer: Customer? = null

    @SerializedName("paymentDevice")
    @Expose
    var paymentDevice: PaymentDevice? = null

    @SerializedName("mobileDevice")
    @Expose
    var mobileDevice: MobileDevice? = null

    @SerializedName("geolocation")
    @Expose
    var geolocation: Geolocation? = null

    @SerializedName("shoppingCarts")
    @Expose
    var shoppingCarts: List<ShoppingCarts> = emptyList()

    @SerializedName("splitters")
    @Expose
    var splitters: Splitters? = null
}