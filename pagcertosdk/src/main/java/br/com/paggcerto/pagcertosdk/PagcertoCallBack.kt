package br.com.paggcerto.pagcertosdk

interface PagcertoCallBack<T> {
    fun onSuccess(obj: T)

    fun onError(code: Int, message: String)
}