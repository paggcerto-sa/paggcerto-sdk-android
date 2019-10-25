package br.com.paggcerto.pagcertosdk.rest.tables

import br.com.paggcerto.pagcertosdk.model.table.Tables

internal class TablesNetwork{

    private val appService = TableClient.getClient().create(TableService::class.java)

    private val nameNetwork = "TablesNetwork"

    fun get(): Tables?{
        val call = appService.listTables()

        try{
            val response = call.execute()
            if(response.isSuccessful){
                return response.body()
            }

        }catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
}