package br.com.paggcerto.pagcertosdk.rest.recurring

import br.com.paggcerto.pagcertosdk.model.account.response.Token
import br.com.paggcerto.pagcertosdk.util.Util
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

internal object RecurringClient {
    private const val timeOut = 30

    fun getClient(token: Token?): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + token?.token)
                    .build()

                chain.proceed(newRequest)
            }

            .readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .build()

        return Retrofit
            .Builder()
            .baseUrl(Util.RECURRING_API_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
}