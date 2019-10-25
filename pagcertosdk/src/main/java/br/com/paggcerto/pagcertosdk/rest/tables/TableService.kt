package br.com.paggcerto.pagcertosdk.rest.tables

import br.com.paggcerto.pagcertosdk.model.table.Tables
import retrofit2.Call
import retrofit2.http.GET

internal interface TableService {
    @GET("tabelas")
    fun listTables(): Call<Tables>
}