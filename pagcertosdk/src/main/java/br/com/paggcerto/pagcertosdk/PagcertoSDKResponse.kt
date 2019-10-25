package br.com.paggcerto.pagcertosdk

interface PagcertoSDKResponse {
    fun onResult(result: Boolean, message: String)
}