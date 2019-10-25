package br.com.paggcerto.pagcertosdk.rest.payment_account

import br.com.paggcerto.pagcertosdk.PagcertoCallBack
import br.com.paggcerto.pagcertosdk.PagcertoSDK
import br.com.paggcerto.pagcertosdk.model.payment_account.request.*
import br.com.paggcerto.pagcertosdk.model.payment_account.response.*
import br.com.paggcerto.pagcertosdk.util.JSONUtils
import br.com.paggcerto.pagcertosdk.util.Util.printError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentAccountNetwork {
    private val appService = PaymentAccountClient.getClient(PagcertoSDK.token)

    private val connectionError = "Não foi possível conectar ao servidor Paggcerto. Tente novamente."

    private val error401 = "Usuário não autenticado (credenciais incorretas ou token inválido)"
    private val error403 = "Usuário autenticado, porém sem permissão (acesso negado)"
    private val unknownError = "Erro inesperado."

    private val gson = Gson()

    fun getBalance(callBack: PagcertoCallBack<Balance>){
        val call = appService.create(PaymentAccountService::class.java).getBilling()
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), Balance::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun minimumAmount(callBack: PagcertoCallBack<Number>){
        val call = appService.create(PaymentAccountService::class.java).minimumAmount()
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), MinimumAmount::class.java).amount)
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun splitterBalance(idList: List<String>, callBack: PagcertoCallBack<BalanceList>){
        val json = gson.toJson(IdList(idList))
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call = appService.create(PaymentAccountService::class.java).splitterBalance(dataObject)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), BalanceList::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun getSettings(callBack: PagcertoCallBack<SettingsResponse>){
        val call = appService.create(PaymentAccountService::class.java).getSettings()
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), SettingsResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun setSettings(settingsRequest: SettingsRequest, callBack: PagcertoCallBack<SettingsResponse>){
        val json = gson.toJson(settingsRequest)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentAccountService::class.java).setSettings(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()){
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), SettingsResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun setSplitterSettings(settingsRequestList: List<SettingsRequest>, callBack: PagcertoCallBack<List<SettingsResponse>>){
        val json = gson.toJson(SettingsRequestList(settingsRequestList))
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentAccountService::class.java).setSplitterSettings(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()){
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), SettingsResponseList::class.java).splitters)
                    400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun getSplitterSettings(idSplitter: String, callBack: PagcertoCallBack<SettingsResponse>){

        val call: Call<String> = appService.create(PaymentAccountService::class.java).getSplitterSettings(idSplitter)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()){
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), SettingsResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun requestCashOut(cashOutRequest: CashOutRequest, callBack: PagcertoCallBack<CashOutResponse>){
        val json = gson.toJson(cashOutRequest)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentAccountService::class.java).requestCashOut(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()){
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), CashOutResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun listCashOut(filterCashOut: FilterCashOut, callBack: PagcertoCallBack<CashOutList>){
        try {
            var json = gson.toJson(filterCashOut)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(PaymentAccountService::class.java).listCashOut(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when(response.code()) {
                        200 -> callBack.onSuccess(gson.fromJson(response.body(), CashOutList::class.java))
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
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }

    fun requestCashOutSplitter(cashOutRequest: CashOutSplitterRequest, callBack: PagcertoCallBack<CashOutResponse>){
        val json = gson.toJson(cashOutRequest)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentAccountService::class.java).requestCashOutSplitter(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()){
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), CashOutResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun listCashOutSplitter(filterCashOut: FilterCashOut, callBack: PagcertoCallBack<CashOutList>){
        try {
            var json = gson.toJson(filterCashOut)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(PaymentAccountService::class.java).listCashOutSplitter(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when(response.code()) {
                        200 -> callBack.onSuccess(gson.fromJson(response.body(), CashOutList::class.java))
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
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }

    fun transferSplitterCashOut(splitterCashOutRequest: SplitterCashOutRequest, callBack: PagcertoCallBack<CashOutResponse>){
        val json = gson.toJson(splitterCashOutRequest)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(PaymentAccountService::class.java).transferSplitterCashOut(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()){
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), CashOutResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun getStatements(filterStatement: FilterStatement, callBack: PagcertoCallBack<Statement>){
        try {
            var json = gson.toJson(filterStatement)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(PaymentAccountService::class.java).getStatements(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when(response.code()) {
                        200 -> callBack.onSuccess(gson.fromJson(response.body(), Statement::class.java))
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
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }

    fun transactionDetail(date: String, filterStatementDetail: FilterStatementDetail, callBack: PagcertoCallBack<TransactionsDetailList>){
        var json = gson.toJson(filterStatementDetail)
        json = JSONUtils.removeArrays(JSONObject(json)).toString()
        val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<java.util.HashMap<String, String>>() {}.type )

        val call: Call<String> = appService.create(PaymentAccountService::class.java).transactionDetail(date, retMap)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when (response.code()){
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), TransactionsDetailList::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    401 -> callBack.onError(response.code(), error401)
                    422 -> callBack.onError(response.code(), printError(response))
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun transactionLinkDetail(idTransaction: String, callBack: PagcertoCallBack<TransactionLinkDetail>){
        val call: Call<String> = appService.create(PaymentAccountService::class.java).transactionLinkDetail(idTransaction)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when (response.code()){
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), TransactionLinkDetail::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
                    401 -> callBack.onError(response.code(), error401)
                    422 -> callBack.onError(response.code(), printError(response))
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }
}