package br.com.paggcerto.pagcertosdk.rest.payment.service

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

internal interface PaymentV3Service {
//    @GET("transactions")
//    fun transactions(
//        @Query("startDate") startDate: String,
//        @Query("endDate") endDate: String,
//        @Query("status") statusList: List<String>,
//        @Query("types") typesList: List<String>,
//        @QueryMap filter: Map<String, String>
//    ): Call<String>

    @GET("transactions")
    fun transactions(@QueryMap filter: Map<String, String>): Call<String>

    @POST("pay/cards")
    fun payWithCardOnline(@Body payObject: RequestBody): Call<String>

    @GET("categories-product")
    fun categoriesProduct(): Call<String>
}