package br.com.paggcerto.pagcertosdk.rest.warnings

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

internal interface WarningsService {
    @GET("holder/message")
    fun messages(@QueryMap filter: Map<String, String>): Call<String>

    @POST("holder/recipient-message")
    fun unreadMessage(@Body payObject: RequestBody): Call<String>
}