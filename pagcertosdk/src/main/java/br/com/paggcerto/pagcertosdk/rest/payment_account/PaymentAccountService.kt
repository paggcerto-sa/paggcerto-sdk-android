package br.com.paggcerto.pagcertosdk.rest.payment_account

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

internal interface PaymentAccountService {
    @GET("balance")
    fun getBilling(): Call<String>

    @GET("cashout-request/minimum-amount")
    fun minimumAmount(): Call<String>

    @POST("splitter-balance")
    fun splitterBalance(@Body requestBody: RequestBody): Call<String>

    @GET("settings")
    fun getSettings(): Call<String>

    @PUT("settings/transfer-days")
    fun setSettings(@Body settings: RequestBody): Call<String>

    @PUT("settings/splitters/transfer-days")
    fun setSplitterSettings(@Body requestCashOut: RequestBody): Call<String>

    @GET("settings/splitter/{id}")
    fun getSplitterSettings(@Path("id") idSplitter: String): Call<String>

    @POST("cashout-request")
    fun requestCashOut(@Body requestCashOut: RequestBody): Call<String>

    @GET("cashout-request")
    fun listCashOut(@QueryMap filter: Map<String, String>): Call<String>

    @POST("cashout-request/splitters")
    fun requestCashOutSplitter(@Body requestCashOut: RequestBody): Call<String>

    @GET("cashout-request/splitters")
    fun listCashOutSplitter(@QueryMap filter: Map<String, String>): Call<String>

    @POST("cashout-request/splitters/internal")
    fun transferSplitterCashOut(@Body requestSplitterCashOut: RequestBody): Call<String>

    @GET("statements")
    fun getStatements(@QueryMap filter: Map<String, String>): Call<String>

    @GET("statements/{date}/details")
    fun transactionDetail(@Path("date") date: String, @QueryMap filter: Map<String, String>): Call<String>

    @GET("transfers/{idTransaction}")
    fun transactionLinkDetail(@Path("idTransaction") idTransaction: String): Call<String>
}