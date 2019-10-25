package br.com.paggcerto.pagcertosdk.rest.tables

import br.com.paggcerto.pagcertosdk.util.Util
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal object TableClient{

    private const val timeOut = 10

    fun getClient(): Retrofit{
        val okHttpClient = OkHttpClient.Builder()
                .readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
                .connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
                .build()

        return Retrofit.Builder()
                .baseUrl(Util.baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}