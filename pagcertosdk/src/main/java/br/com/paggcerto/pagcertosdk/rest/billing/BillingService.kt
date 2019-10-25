package br.com.paggcerto.pagcertosdk.rest.billing

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

internal interface BillingService {
    @POST(".")
    fun createBilling(@Body dataObject: RequestBody): Call<String>

    @GET(".")
    fun listBillings(@QueryMap filter: Map<String, String>): Call<String>

    @GET("{idBilling}")
    fun getBilling(@Path("idBilling") idBilling: String): Call<String>

    @DELETE("cancel/{idBilling}")
    fun cancelBilling(@Path("idBilling") idBilling: String): Call<String>
}