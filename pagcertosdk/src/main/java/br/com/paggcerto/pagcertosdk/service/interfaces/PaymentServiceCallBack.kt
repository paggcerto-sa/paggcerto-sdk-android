package br.com.paggcerto.pagcertosdk.service.interfaces

import br.com.paggcerto.pagcertosdk.model.payments.response.Payment

private interface PaymentServiceCallBack {
    fun onSuccess(payment: Payment)

    fun onError(message: String)
}