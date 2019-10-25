package br.com.paggcerto.pagcertosdk.rest.payment

import br.com.paggcerto.pagcertosdk.PagcertoCallBack
import br.com.paggcerto.pagcertosdk.PagcertoSDK
import br.com.paggcerto.pagcertosdk.model.payments.request.*
import br.com.paggcerto.pagcertosdk.model.payments.response.*
import br.com.paggcerto.pagcertosdk.rest.payment.client.PaymentClient
import br.com.paggcerto.pagcertosdk.rest.payment.client.PaymentV3Client
import br.com.paggcerto.pagcertosdk.rest.payment.service.PaymentService
import br.com.paggcerto.pagcertosdk.rest.payment.service.PaymentV3Service
import br.com.paggcerto.pagcertosdk.util.JSONUtils
import br.com.paggcerto.pagcertosdk.util.Util.printError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.json.JSONException

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class PaymentNetwork  {
    private val appService = PaymentClient.getClient(PagcertoSDK.token)

    private val connectionError = "Não foi possível conectar ao servidor Paggcerto. Tente novamente."

    private val error401 = "Usuário não autenticado (credenciais incorretas ou token inválido)"
    private val error403 = "Usuário autenticado, porém sem permissão (acesso negado)"
    private val unknownError = "Erro inesperado."

    private val gson = Gson()

    fun getBin(callBack: PagcertoCallBack<List<Bin>>){
        val call = appService.create(PaymentService::class.java).getBins()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> try{
                        val binList = gson.fromJson(response.body(), BinsList::class.java)
                        callBack.onSuccess(binList.bins)
                    }catch (e: Exception){
                        e.printStackTrace()
                        callBack.onError(-1, "Erro ao processar bandeiras")
                    }
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun payWithCard(pay: Pay, callBack: PagcertoCallBack<Payment>){

        val json = gson.toJson(pay)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call = appService.create(PaymentService::class.java).payWithCard(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Payment::class.java))
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun getTransferDays(callBack: PagcertoCallBack<TransferDays>){
        val call = appService.create(PaymentService::class.java).getTransferDays()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), TransferDays::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun payments(filterHistoryPayment: FilterHistoryPayment, callBack: PagcertoCallBack<HistoryPayment>){

        try {
            var json = gson.toJson(filterHistoryPayment)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()

            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call = appService.create(PaymentService::class.java).getHistoryPayments(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), HistoryPayment::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })

        }catch (e: Exception){
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun findPayment(paymentId: String, callBack: PagcertoCallBack<Payment>){
        val call: Call<String> = appService.create(PaymentService::class.java).getPaymentDetail(paymentId)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun sendReceipt(nsu: String, sendReceipt: SendReceipt, callBack: PagcertoCallBack<Boolean>){
        val json = gson.toJson(sendReceipt)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).sendReceipt(nsu, dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 204 -> callBack.onSuccess(true)
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun pdfBankSlipPayment(paymentId: String, callBack: PagcertoCallBack<ByteArray?>){
        val call: Call<ResponseBody> = appService.create(PaymentService::class.java).getBankSlipsPDF(paymentId)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(response.body()?.bytes())
                    response.code() == 422 -> {
                        var errorKey = ""
                        try {
                            errorKey = JSONObject(response.errorBody()?.string()).getString("error")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            callBack.onError(response.code(), errorKey)
                        }
                    }
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun cancelCardTransaction(nsu: String, callBack: PagcertoCallBack<Payment>){
        val call: Call<String> = appService.create(PaymentService::class.java).cancelCardTransaction(nsu)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun cancelPayment(paymentId: String, callBack: PagcertoCallBack<Payment>){
        val call: Call<String> = appService.create(PaymentService::class.java).cancelPayment(paymentId)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun bankSlipPay(bankSlipsPay: BankSlipsPay, callBack: PagcertoCallBack<BankSlipPayment>){
        val json = gson.toJson(bankSlipsPay)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).payBankSlips(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), BankSlipPayment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun finalizePayment(paymentId: String, note: String, callBack: PagcertoCallBack<Payment>){
        val json = "{\"note\":\"$note\"}"
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).finishPayment(paymentId, dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun cancelBankSlip(number: String, cancellationReason: String, callBack: PagcertoCallBack<Payment>){
        val json = "{\"cancellationNote\":\"$cancellationReason\"}"
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).cancelBankSlip(number,  dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun replaceBankslip(number: String, replaceBankSlips: ReplaceBankSlips, callBack: PagcertoCallBack<Payment>){
        val json = gson.toJson(replaceBankSlips)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).replaceBankSlips(number, dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun historyAnticipation(filterAnticipation: FilterAnticipation, callBack: PagcertoCallBack<AnticipationHistory>){

        try {
            var json = gson.toJson(filterAnticipation)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(PaymentService::class.java).anticipationHistory(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), AnticipationHistory::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun cardTransactionsAvailable(filterTransaction: FilterTransaction, callBack: PagcertoCallBack<AnticipableTransaction>){
        try {
            var json = gson.toJson(filterTransaction)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(PaymentService::class.java).anticipatedTransactions(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), AnticipableTransaction::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun requestAnticipation(transactionsToAnticipate: TransactionsToAnticipate, callBack: PagcertoCallBack<Anticipation>){
        val json = gson.toJson(transactionsToAnticipate)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).newAnticipationRequest(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Anticipation::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun anticipationTransactions(anticipationId: String, filterTransaction: FilterTransaction, callBack: PagcertoCallBack<AnticipableTransaction>){
        try {
            var json = gson.toJson(filterTransaction)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(PaymentService::class.java).anticipationTransactions(anticipationId, retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), AnticipableTransaction::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun simulate(simulation: Simulation, callBack: PagcertoCallBack<SimulationResult>){
        val json = gson.toJson(simulation)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).simulatePay(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), SimulationResult::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })

    }

    fun createCard(card: CardRequest, callBack: PagcertoCallBack<CardResponse>){
        val json = gson.toJson(card)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).createCard(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), CardResponse::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun cards(filterCards: FilterCards, callBack: PagcertoCallBack<CardResponseList>){
        try {
            var json = gson.toJson(filterCards)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(PaymentService::class.java).cards(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), CardResponseList::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun findCard(idCard: String, callBack: PagcertoCallBack<CardResponse>){
        val call: Call<String> = appService.create(PaymentService::class.java).findCard(idCard)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), CardResponse::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun deleteCard(idCard: String, callBack: PagcertoCallBack<CardResponse>){
        val call: Call<String> = appService.create(PaymentService::class.java).removeCard(idCard)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), CardResponse::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun createSplitter(splitter: SplitterRequest, callBack: PagcertoCallBack<SplitterResponse>){
        val json = gson.toJson(splitter)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).registerSplitter(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), SplitterResponse::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun updateSplitter(id: String, splitter: SplitterRequest, callBack: PagcertoCallBack<SplitterResponse>){
        val json = gson.toJson(splitter)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).updateSplitter(id, dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), SplitterResponse::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun splitters(filterSplitter: FilterSplitter, callBack: PagcertoCallBack<SplitterResponseList>){
        try {
            var json = gson.toJson(filterSplitter)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()

            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call = appService.create(PaymentService::class.java).listSplitter(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), SplitterResponseList::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })

        }catch (e: Exception){
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun findSplitter(id: String, callBack: PagcertoCallBack<SplitterResponse>){
        val call = appService.create(PaymentService::class.java).getSplitter(id)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), SplitterResponse::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun getTransfersSplitter(filterTransferSplitter: FilterTransferSplitter, callBack: PagcertoCallBack<TransfersSplit>){
        try {
            var json = gson.toJson(filterTransferSplitter)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()

            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call = appService.create(PaymentService::class.java).getTransferSplitters(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), TransfersSplit::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })

        }catch (e: Exception){
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun getCardTransactions(nsu: String, installmentNumber: Int, callBack: PagcertoCallBack<CardTransactionLink>){
        val map = HashMap<String, String>()
        map["installment"] = installmentNumber.toString()

        val call = appService.create(PaymentService::class.java).getCardTransactions(nsu, map)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), CardTransactionLink::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun getBankSlipTransaction(bankSlipNumber: String, callBack: PagcertoCallBack<BankSlipTransactionLink>){
        val call = appService.create(PaymentService::class.java).getBankSlipTransactions(bankSlipNumber)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), BankSlipTransactionLink::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun getOtherTransaction(id: String, callBack: PagcertoCallBack<OtherTransactionLink>){
        val call = appService.create(PaymentService::class.java).getOtherTransactions(id)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), OtherTransactionLink::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun futureTransaction(filterFutureTransaction: FilterFutureTransaction, callBack: PagcertoCallBack<FutureTransaction>){
        try {
            var json = gson.toJson(filterFutureTransaction)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()

            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call = appService.create(PaymentService::class.java).getFutureTransaction(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), FutureTransaction::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })

        }catch (e: Exception){
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun getFutureTransactionDetail(date: String, filterFutureTransaction: FilterFutureTransaction, callBack: PagcertoCallBack<TransactionsList>){
        try {
            var json = gson.toJson(filterFutureTransaction)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(PaymentService::class.java).getFutureTransactionDetail(date, retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), TransactionsList::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun getBills(callBack: PagcertoCallBack<Bills>){
        val call = appService.create(PaymentService::class.java).getBills()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Bills::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun transactions(filterStatement: FilterTransactionRecord, callBack: PagcertoCallBack<TransactionResponse>){
        val json = JSONUtils.removeArrays(JSONObject(gson.toJson(filterStatement))).toString()
        val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

        val appService = PaymentV3Client.getClient(PagcertoSDK.token)
        val call: Call<String> = appService.create(PaymentV3Service::class.java).transactions(retMap)
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), TransactionResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun defaultFees(filterFee: FilterFee, callBack: PagcertoCallBack<Fee>){
        try {
            var json = gson.toJson(filterFee)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()

            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call = appService.create(PaymentService::class.java).getDefaultFee(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Fee::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })

        }catch (e: Exception){
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun fees(callBack: PagcertoCallBack<Fee>){
        val call = appService.create(PaymentService::class.java).getFee()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Fee::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun registerBankSlipFee(holdersBankSlipList: HoldersBankSlipList, callBack: PagcertoCallBack<Boolean>){
        val json = gson.toJson(holdersBankSlipList)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).registerBankSlipFee(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 204 -> callBack.onSuccess(true)
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun updateBankSlipFee(holdersBankSlipList: HoldersBankSlipList, callBack: PagcertoCallBack<Boolean>){
        val json = gson.toJson(holdersBankSlipList)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).updateBankSlipFee(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 204 -> callBack.onSuccess(true)
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun deleteBankSlipFee(holderIdList: HolderIdList, callBack: PagcertoCallBack<Boolean>){
        val json = gson.toJson(holderIdList)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).deleteBankSlipFee(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 204 -> callBack.onSuccess(true)
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun updateBankSlipFeeBase(amountTax: Double, callBack: PagcertoCallBack<FeeBase>){
        val json = "{ \"amountTax\": $amountTax }"
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).updateBankSlipFeeBase(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), FeeBase::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun registerCardFee(holderCard: HolderCard, callBack: PagcertoCallBack<Boolean>){
        val json = gson.toJson(holderCard)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).registerCardFee(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 204 -> callBack.onSuccess(true)
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun updateCardFeeBase(brandFees: BrandFees, callBack: PagcertoCallBack<BrandFees>){
        val json = gson.toJson(brandFees)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).updateCardFeeBase(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), BrandFees::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun getCommission(filterCommission: FilterComission, callBack: PagcertoCallBack<Commission>){
        try {
            var json = gson.toJson(filterCommission)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(PaymentService::class.java).getCommission(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Commission::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun continuePayment(paymentId: String, pay: Pay, callBack: PagcertoCallBack<Payment>){
        val json = gson.toJson(pay)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).continuePayment(paymentId, dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun capturePayment(paymentId: String, capturePayment: CapturePayment, callBack: PagcertoCallBack<Payment>){
        val json = gson.toJson(capturePayment)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).capturePayment(paymentId, dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun duplicateBankSlip(sellerId: String, bankSlipId: String, callBack: PagcertoCallBack<Payment>){
        val call: Call<String> = appService.create(PaymentService::class.java).duplicateBankSlip(sellerId, bankSlipId)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun getBankSlipPdfListZip(filterPdfBankSlip: List<String>, callBack: PagcertoCallBack<ByteArray?>){
        try {
            var json = gson.toJson(FilterPdfBankSlipZip(filterPdfBankSlip))
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<ResponseBody> = appService.create(PaymentService::class.java).getBankSlipsPDFListZip(retMap)
            call.enqueue(object : Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(response.body()?.bytes())
                        response.code() == 422 -> {
                            var errorKey = ""
                            try {
                                errorKey = JSONObject(response.errorBody()?.string()).getString("error")
                            } catch (e: Exception) {
                                e.printStackTrace()
                            } finally {
                                callBack.onError(response.code(), errorKey)
                            }
                        }
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun getBankSlipPdfList(filterPdfBankSlip: List<String>, callBack: PagcertoCallBack<ByteArray?>){
        try {
            var json = gson.toJson(FilterPdfBankSlip(filterPdfBankSlip))
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<ResponseBody> = appService.create(PaymentService::class.java).getBankSlipsPDFList(retMap)
            call.enqueue(object : Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(response.body()?.bytes())
                        response.code() == 422 -> {
                            var errorKey = ""
                            try {
                                errorKey = JSONObject(response.errorBody()?.string()).getString("error")
                            } catch (e: Exception) {
                                e.printStackTrace()
                            } finally {
                                callBack.onError(response.code(), errorKey)
                            }
                        }
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun sendSingleBankSlipByEmail(bankSlipId: String, email: String, callBack: PagcertoCallBack<Boolean>){
        val json =  "{\n" +
                "  \"email\": \"$email\"\n" +
                "}"
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).sendSingleBankSlipByEmail(bankSlipId, dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 204 -> callBack.onSuccess(true)
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun sendMultipleBankSlipByEmail(paymentId: String,  email: String, callBack: PagcertoCallBack<Boolean>){
        val json =  "{\n" +
                "  \"email\": \"$email\"\n" +
                "}"
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        val call: Call<String> = appService.create(PaymentService::class.java).sendMultipleBankSlipByEmail(paymentId, dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 204 -> callBack.onSuccess(true)
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun checkoutBankSlipDetail(bankSlipId: String, callBack: PagcertoCallBack<CheckoutBankSlip>){
        val call: Call<String> = appService.create(PaymentService::class.java).checkoutBankSlipDetail(bankSlipId)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), CheckoutBankSlip::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun checkoutBankSlipPdf(bankSlipId: String, callBack: PagcertoCallBack<ByteArray?>){
        try {
            val call: Call<ResponseBody> = appService.create(PaymentService::class.java).checkoutBankSlipPdf(bankSlipId)
            call.enqueue(object : Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(response.body()?.bytes())
                        response.code() == 422 -> {
                            var errorKey = ""
                            try {
                                errorKey = JSONObject(response.errorBody()?.string()).getString("error")
                            } catch (e: Exception) {
                                e.printStackTrace()
                            } finally {
                                callBack.onError(response.code(), errorKey)
                            }
                        }
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun checkoutBankSlipListDetail(paymentId: String, callBack: PagcertoCallBack<CheckoutBankSlipList>){
        val call: Call<String> = appService.create(PaymentService::class.java).checkoutBankSlipListDetail(paymentId)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), CheckoutBankSlipList::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun checkoutBankSlipListPdf(paymentId: String, callBack: PagcertoCallBack<ByteArray?>){
        try {
            val call: Call<ResponseBody> = appService.create(PaymentService::class.java).checkoutBankSlipListPdf(paymentId)
            call.enqueue(object : Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(response.body()?.bytes())
                        response.code() == 422 -> {
                            var errorKey = ""
                            try {
                                errorKey = JSONObject(response.errorBody()?.string()).getString("error")
                            } catch (e: Exception) {
                                e.printStackTrace()
                            } finally {
                                callBack.onError(response.code(), errorKey)
                            }
                        }
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun getBankSlips(filterBankSlips: FilterBankSlips, callBack: PagcertoCallBack<BankSlipsList>){
        try {
            var json = gson.toJson(filterBankSlips)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(PaymentService::class.java).getBankSlips(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), BankSlipsList::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun paymentsPerHourly(callBack: PagcertoCallBack<StatisticList>){
        val call: Call<String> = appService.create(PaymentService::class.java).paymentsPerHourly()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), StatisticList::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun paymentsPerWeekly(callBack: PagcertoCallBack<StatisticList>){
        val call: Call<String> = appService.create(PaymentService::class.java).paymentsPerWeekly()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), StatisticList::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun paymentsPerMonthly(callBack: PagcertoCallBack<StatisticList>){
        val call: Call<String> = appService.create(PaymentService::class.java).paymentsPerMonthly()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), StatisticList::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun paymentsPerYearly(callBack: PagcertoCallBack<StatisticList>){
        val call: Call<String> = appService.create(PaymentService::class.java).paymentsPerYearly()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), StatisticList::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun statisticsPaymentMethods(callBack: PagcertoCallBack<StatisticPayment>){
        val call: Call<String> = appService.create(PaymentService::class.java).statisticsPaymentMethods()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), StatisticPayment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun paymentsPerBankSlipsCurrentMonth(callBack: PagcertoCallBack<BankSlipPercentages>){
        val call: Call<String> = appService.create(PaymentService::class.java).paymentsPerBankSlipsCurrentMonth()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), BankSlipPercentages::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun bankTransferFees(callBack: PagcertoCallBack<TransferFee>){
        val call: Call<String> = appService.create(PaymentService::class.java).bankTransferFees()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), TransferFee::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun acceptTerms(callBack: PagcertoCallBack<Boolean>){
        val call: Call<String> = appService.create(PaymentService::class.java).acceptTerms()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 204 -> callBack.onSuccess(true)
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun canAnticipate(callBack: PagcertoCallBack<CanAnticipate>){
        val call: Call<String> = appService.create(PaymentService::class.java).canAnticipate()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), CanAnticipate::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun anticipateAllTransactions(callBack: PagcertoCallBack<Anticipation>){
        val call: Call<String> = appService.create(PaymentService::class.java).anticipateAllTransactions()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Anticipation::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun anticipationInProgress(callBack: PagcertoCallBack<Anticipation>){
        val call: Call<String> = appService.create(PaymentService::class.java).anticipationInProgress()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Anticipation::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun anticipationDetail(anticipationId: String, callBack: PagcertoCallBack<Anticipation>){
        val call: Call<String> = appService.create(PaymentService::class.java).anticipationDetail(anticipationId)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Anticipation::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun transactionsInProgress(filterTransaction: FilterTransaction, callBack: PagcertoCallBack<RequestedCardTransactionsList>){
        try {
            var json = gson.toJson(filterTransaction)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(PaymentService::class.java).anticipationInProgress(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), RequestedCardTransactionsList::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun futureTransactionSplitter(filterSplitter: FilterSplitterTransaction, callBack: PagcertoCallBack<FutureTransaction>){
        try {
            var json = gson.toJson(filterSplitter)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(PaymentService::class.java).futureTransferSplitter(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), FutureTransaction::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun futureTransactionSplitterDetail(date: String, futureFilterTransaction: FilterSplitterTransaction, callBack: PagcertoCallBack<TransactionsList>){
        try {
            var json = gson.toJson(futureFilterTransaction)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(PaymentService::class.java).futureTransferSplitterDetail(date, retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), TransactionsList::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, unknownError)
        }
    }

    fun simulateBankSlipPay(bankSlipsIdList: BankSlipsIdList, callBack: PagcertoCallBack<Boolean>){
        val json = gson.toJson(bankSlipsIdList)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentService::class.java).simulateBankSlipPay(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 204 -> callBack.onSuccess(true)
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }
}