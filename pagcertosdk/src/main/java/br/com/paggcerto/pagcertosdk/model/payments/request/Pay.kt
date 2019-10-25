package br.com.paggcerto.pagcertosdk.model.payments.request

import br.com.paggcerto.pagcertosdk.model.payments.response.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pay: Serializable {
    @SerializedName("sellingKey")
    @Expose
    var sellingKey: String? = null

    @SerializedName("amount")
    @Expose
    var amount: Number? = null

    @SerializedName("cards")
    @Expose
    var cards: List<PayCard> = emptyList()

    @SerializedName("paymentDevice")
    @Expose
    var paymentDevice: PaymentDevice? = null

    @SerializedName("mobileDevice")
    @Expose
    var mobileDevice: MobileDevice? = null

    @SerializedName("geolocation")
    @Expose
    var geolocation: Geolocation? = null

    @SerializedName("splitters")
    @Expose
    var splitters: Splitters? = null
}