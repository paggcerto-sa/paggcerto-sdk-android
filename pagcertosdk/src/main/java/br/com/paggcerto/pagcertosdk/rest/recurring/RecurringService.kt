package br.com.paggcerto.pagcertosdk.rest.recurring

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RecurringService {
    @POST("settings")
    fun registerSettings(@Body recurringSetting: RequestBody): Call<String>

    @PUT("settings")
    fun updateSettings(@Body recurringSetting: RequestBody): Call<String>

    @POST("contracts")
    fun registerContract(@Body contract: RequestBody): Call<String>

    @PUT("contracts/{contractId}")
    fun updateContract(@Path("contractId") contractId: String, @Body contract: RequestBody): Call<String>

    @GET("contracts")
    fun getContracts(@QueryMap filter: Map<String, String>): Call<String>

    @GET("contracts/{contractId}")
    fun searchContract(@Path("contractId") contractId: String): Call<String>

    @PUT("contracts/cancel/{contractId}")
    fun cancelContract(@Path("contractId") contractId: String, @Body reason: RequestBody): Call<String>

    @PUT("contracts/pause/{contractId}")
    fun pauseContract(@Path("contractId") contractId: String, @Body reason: RequestBody): Call<String>

    @PUT("contracts/unblock/{contractId}")
    fun unlockContract(@Path("contractId") contractId: String): Call<String>

    @POST("contracts/{contractId}/addendum")
    fun addendumContract(@Path("contractId") contractId: String, @Body addendum: RequestBody): Call<String>

    @DELETE("contracts/{contractId}/addendum/{addendumId}")
    fun deleteAddendumContract(@Path("contractId") contractId: String, @Path("addendumId") addendumId: String): Call<String>

    @GET("invoices")
    fun invoices(@QueryMap filter: Map<String, String>): Call<String>

    @GET("invoices/{invoiceId}")
    fun searchInvoice(@Path("invoiceId") invoiceId: String): Call<String>

    @POST("invoices/manual-pay/{invoiceId}")
    fun confirmInvoicePayment(@Path("invoiceId") invoiceId: String, @Body description: RequestBody): Call<String>
}