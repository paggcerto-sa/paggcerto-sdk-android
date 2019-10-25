package br.com.paggcerto.pagcertosdk.rest.payment.service

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

internal interface PaymentService {

    @GET("bins")
    fun getBins(): Call<String>

    @GET("plans/transfer-days")
    fun getTransferDays(): Call<String>

    @GET("all")
    fun getHistoryPayments(@QueryMap filter: Map<String, String>): Call<String>

    @GET("find/{paymentId}")
    fun getPaymentDetail(@Path("paymentId") paymentId: String): Call<String>

    @POST("card-transactions/send-receipt/{nsu}")
    fun sendReceipt(@Path("nsu") nsu: String, @Body receiptObject: RequestBody): Call<String>

    @GET("bank-slips/pdf/{paymentId}")
    fun getBankSlipsPDF(@Path("paymentId") paymentId: String): Call<ResponseBody>

    @POST("card-transactions/cancel/{nsu}")
    fun cancelCardTransaction(@Path("nsu") nsu: String): Call<String>

    @POST("cancel/{paymentId}")
    fun cancelPayment(@Path("paymentId") paymentId: String): Call<String>

    @POST("pay/cards")
    fun payWithCard(@Body payObject: RequestBody): Call<String>

    @POST("pay/cards/{id}")
    fun continuePayment(@Path("id") id: String, @Body payment: RequestBody): Call<String>

    @PUT("pay/cards/capture/{paymentId}")
    fun capturePayment(@Path("paymentId") paymentId: String, @Body capturePayment: RequestBody): Call<String>

    @POST("pay/bank-slips")
    fun payBankSlips(@Body payObject: RequestBody): Call<String>

    @POST("finalize/{paymentId}")
    fun finishPayment(@Path("paymentId") paymentId: String, @Body jsonObject: RequestBody): Call<String>

    @POST("bank-slips/cancel/{number}")
    fun cancelBankSlip(@Path("number") number: String, @Body jsonObject: RequestBody): Call<String>

    @POST("bank-slips/replace/{number}")
    fun replaceBankSlips(@Path("number") bankSlipsNumber: String, @Body jsonObject: RequestBody): Call<String>

    @POST("pay/simulate")
    fun simulatePay(@Body payObject: RequestBody): Call<String>

    @POST("cards")
    fun createCard(@Body cardsObject: RequestBody): Call<String>

    @GET("cards")
    fun cards(@QueryMap filter: Map<String, String>): Call<String>

    @GET("cards/{id}")
    fun findCard(@Path("id") idCard: String): Call<String>

    @DELETE("cards/{id}")
    fun removeCard(@Path("id") idCard: String): Call<String>

    @POST("splitters")
    fun registerSplitter(@Body splitObject: RequestBody): Call<String>

    @PUT("splitters/{id}")
    fun updateSplitter(@Path("id") id:String, @Body splitObject: RequestBody): Call<String>

    @GET("splitters")
    fun listSplitter(@QueryMap filter: Map<String, String>): Call<String>

    @GET("splitters/find/{id}")
    fun getSplitter(@Path("id") id: String): Call<String>

    @GET("transfers/splitters")
    fun getTransferSplitters(@QueryMap filter: Map<String, String>): Call<String>

    @GET("card-transactions/{nsu}")
    fun getCardTransactions(@Path("nsu") nsu: String, @QueryMap filter: Map<String, String>): Call<String>

    @GET("bank-slips/{bankSlipNumber}")
    fun getBankSlipTransactions(@Path("bankSlipNumber") bankSlipNumber: String): Call<String>

    @GET("other-transfers/{id}")
    fun getOtherTransactions(@Path("id") id: String): Call<String>

    @GET("transactions/future")
    fun getFutureTransaction(@QueryMap filter: Map<String, String>) : Call<String>

    @GET("transactions/future/{date}/details")
    fun getFutureTransactionDetail(@Path("date") date: String, @QueryMap filter: Map<String, String>): Call<String>

    @GET("invoices")
    fun getBills(): Call<String>

    @GET("fees/default")
    fun getDefaultFee(@QueryMap filter: Map<String, String>): Call<String>

    @GET("fees")
    fun getFee(): Call<String>

    @POST("partner/fees/bank-slips")
    fun registerBankSlipFee(@Body holders: RequestBody): Call<String>

    @PUT("partner/fees/bank-slips")
    fun updateBankSlipFee(@Body holders: RequestBody): Call<String>

    @HTTP(method = "DELETE", path = "partner/fees/bank-slips", hasBody = true)
    fun deleteBankSlipFee(@Body holders: RequestBody): Call<String>

    @PUT("partner/fees/bank-slips/set-default")
    fun updateBankSlipFeeBase(@Body amountFee: RequestBody): Call<String>

    @POST("partner/fees/cards")
    fun registerCardFee(@Body holder: RequestBody): Call<String>

    @PUT("partner/fees/card-brand-fee")
    fun updateCardFeeBase(@Body brandFee: RequestBody): Call<String>

    @GET("partner/transfers/period")
    fun getCommission(@QueryMap filter: Map<String, String>): Call<String>

    @POST("bank-slips/second/{sellerId}/{id}")
    fun duplicateBankSlip(@Path("sellerId") sellerId: String, @Path("id") id: String): Call<String>

    @GET("bank-slips/zip")
    fun getBankSlipsPDFListZip(@QueryMap filter: Map<String, String>): Call<ResponseBody>

    @GET("bank-slips/pdf")
    fun getBankSlipsPDFList(@QueryMap filter: Map<String, String>): Call<ResponseBody>

    @POST("bank-slips/send/single/{bankSlipId}")
    fun sendSingleBankSlipByEmail(@Path("bankSlipId") bankSlipId: String, @Body requestBody: RequestBody): Call<String>

    @POST("bank-slips/send/{paymentId}")
    fun sendMultipleBankSlipByEmail(@Path("paymentId") paymentId: String, @Body requestBody: RequestBody): Call<String>

    @GET("checkout/bank-slips/single/{id}")
    fun checkoutBankSlipDetail(@Path("id") bankSlipId: String): Call<String>

    @GET("checkout/bank-slips/single/{id}/pdf")
    fun checkoutBankSlipPdf(@Path("id") bankSlipId: String): Call<ResponseBody>

    @GET("checkout/bank-slips/{paymentId}")
    fun checkoutBankSlipListDetail(@Path("paymentId") paymentId: String): Call<String>

    @GET("checkout/bank-slips/{paymentId}/pdf")
    fun checkoutBankSlipListPdf(@Path("paymentId") paymentId: String): Call<ResponseBody>

    @GET("bank-slips")
    fun getBankSlips(@QueryMap filter: Map<String, String>): Call<String>

    @GET("statistics/hourly")
    fun paymentsPerHourly(): Call<String>

    @GET("statistics/weekly")
    fun paymentsPerWeekly(): Call<String>

    @GET("statistics/monthly")
    fun paymentsPerMonthly(): Call<String>

    @GET("statistics/yearly")
    fun paymentsPerYearly(): Call<String>

    @GET("statistics/payment-methods")
    fun statisticsPaymentMethods(): Call<String>

    @GET("statistics/bank-slips-current-month")
    fun paymentsPerBankSlipsCurrentMonth(): Call<String>

    @GET("financial/bank-transfer-fees")
    fun bankTransferFees(): Call<String>

    @GET("anticipations")
    fun anticipationHistory(@QueryMap filter: Map<String, String>): Call<String>

    @GET("anticipations/card-transactions-available")
    fun anticipatedTransactions(@QueryMap filter: Map<String, String>): Call<String>

    @POST("anticipations/new-request/selected-card-transactions")
    fun newAnticipationRequest(@Body transactionsObject: RequestBody): Call<String>

    @GET("anticipations/{anticipationId}/card-transactions")
    fun anticipationTransactions(@Path("anticipationId") anticipationId: String, @QueryMap filter: Map<String, String>): Call<String>

    @POST("anticipations/accept-term")
    fun acceptTerms(): Call<String>

    @GET("anticipations/can-anticipate")
    fun canAnticipate(): Call<String>

    @POST("anticipations/new-request/all-card-transactions")
    fun anticipateAllTransactions(): Call<String>

    @GET("anticipations/in-progress")
    fun anticipationInProgress(): Call<String>

    @GET("anticipations/{anticipationId}")
    fun anticipationDetail(@Path("anticipationId") anticipationId: String): Call<String>

    @GET("anticipations/in-progress/card-transactions")
    fun anticipationInProgress(@QueryMap filter: Map<String, String>): Call<String>

    @GET("splitter/transactions/future")
    fun futureTransferSplitter(@QueryMap filter: Map<String, String>): Call<String>

    @GET("splitter/transactions/future/{date}/details")
    fun futureTransferSplitterDetail(@Path("date") date: String, @QueryMap filter: Map<String, String>): Call<String>

    @POST("mock/bank-slips/pay")
    fun simulateBankSlipPay(@Body listBankSlips: RequestBody): Call<String>
}