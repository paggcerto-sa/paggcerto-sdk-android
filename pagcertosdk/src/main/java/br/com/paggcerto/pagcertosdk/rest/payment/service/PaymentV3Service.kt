package br.com.paggcerto.pagcertosdk.rest.payment.service

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
}