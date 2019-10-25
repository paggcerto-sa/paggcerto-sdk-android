package br.com.paggcerto.pagcertosdk.service.interfaces

import br.com.paggcerto.pagcertosdk.model.payments.request.PayCard

interface PinpadServiceCallBack {
    fun onSuccess(card: PayCard, online: Boolean)

    fun onError(message: String)
}