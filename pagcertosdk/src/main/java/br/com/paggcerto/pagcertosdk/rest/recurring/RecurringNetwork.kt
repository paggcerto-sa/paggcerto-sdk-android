package br.com.paggcerto.pagcertosdk.rest.recurring

import br.com.paggcerto.pagcertosdk.PagcertoCallBack
import br.com.paggcerto.pagcertosdk.PagcertoSDK
import br.com.paggcerto.pagcertosdk.model.recurring.RecurringSetting
import br.com.paggcerto.pagcertosdk.model.recurring.request.Addendums
import br.com.paggcerto.pagcertosdk.model.recurring.request.ContractRequest
import br.com.paggcerto.pagcertosdk.model.recurring.request.FilterContract
import br.com.paggcerto.pagcertosdk.model.recurring.response.ContractResponse
import br.com.paggcerto.pagcertosdk.model.recurring.response.ContractResponseList
import br.com.paggcerto.pagcertosdk.model.recurring.response.Invoice
import br.com.paggcerto.pagcertosdk.model.recurring.response.InvoicesList
import br.com.paggcerto.pagcertosdk.util.JSONUtils
import br.com.paggcerto.pagcertosdk.util.Util.printError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecurringNetwork {
    private val appService = RecurringClient.getClient(PagcertoSDK.token)

    private val connectionError = "Não foi possível conectar ao servidor Paggcerto. Tente novamente."

    private val error401 = "Usuário não autenticado (credenciais incorretas ou token inválido)"
    private val error403 = "Usuário autenticado, porém sem permissão (acesso negado)"
    private val unknownError = "Erro inesperado."

    private val gson = Gson()

    fun registerSettings(recurringSetting: RecurringSetting, callBack: PagcertoCallBack<RecurringSetting>){
        val json = gson.toJson(recurringSetting)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(RecurringService::class.java).registerSettings(dataObject)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()){
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), RecurringSetting::class.java))
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

    fun updateSettings(recurringSetting: RecurringSetting, callBack: PagcertoCallBack<RecurringSetting>){
        val json = gson.toJson(recurringSetting)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(RecurringService::class.java).updateSettings(dataObject)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()){
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), RecurringSetting::class.java))
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

    fun registerContract(contract: ContractRequest, callBack: PagcertoCallBack<ContractResponse>){
        val json = gson.toJson(contract)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(RecurringService::class.java).registerContract(dataObject)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()){
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), ContractResponse::class.java))
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

    fun updateContract(contractId: String, contract: ContractRequest, callBack: PagcertoCallBack<ContractResponse>){
        val json = gson.toJson(contract)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(RecurringService::class.java).updateContract(contractId, dataObject)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()){
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), ContractResponse::class.java))
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

    fun getContract(filterContract: FilterContract, callBack: PagcertoCallBack<ContractResponseList>){
        try {
            var json = gson.toJson(filterContract)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(RecurringService::class.java).getContracts(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), ContractResponseList::class.java))
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
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }

    fun searchContract(contractId: String, callBack: PagcertoCallBack<ContractResponse>){
        val call: Call<String> = appService.create(RecurringService::class.java).searchContract(contractId)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), ContractResponse::class.java))
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

    fun cancelContract(contractId: String, reason: String, callBack: PagcertoCallBack<ContractResponse>){
        val json = "{\"reason\":\"$reason\"}"
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(RecurringService::class.java).cancelContract(contractId, dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), ContractResponse::class.java))
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

    fun pauseContract(contractId: String, pauseUntil: String, callBack: PagcertoCallBack<ContractResponse>){
        val json = "{\"pauseUntil\":\"$pauseUntil\"}"
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(RecurringService::class.java).pauseContract(contractId, dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), ContractResponse::class.java))
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

    fun unlockContract(contractId: String, callBack: PagcertoCallBack<ContractResponse>){
        val call: Call<String> = appService.create(RecurringService::class.java).unlockContract(contractId)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), ContractResponse::class.java))
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

    fun addendumContract(contractId: String, addendums: Addendums, callBack: PagcertoCallBack<ContractResponse>){
        val json = gson.toJson(addendums)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(RecurringService::class.java).addendumContract(contractId, dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), ContractResponse::class.java))
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

    fun deleteAddendumContract(contractId: String, addendumId: String, callBack: PagcertoCallBack<ContractResponse>){
        val call: Call<String> = appService.create(RecurringService::class.java).deleteAddendumContract(contractId, addendumId)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), ContractResponse::class.java))
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

    fun getInvoices(filterContract: FilterContract, callBack: PagcertoCallBack<InvoicesList>){
        try {
            var json = gson.toJson(filterContract)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String> = appService.create(RecurringService::class.java).invoices(retMap)
            call.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), InvoicesList::class.java))
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
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }

    fun searchInvoice(invoiceId: String, callBack: PagcertoCallBack<Invoice>){
        val call: Call<String> = appService.create(RecurringService::class.java).searchInvoice(invoiceId)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Invoice::class.java))
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

    fun confirmInvoicePayment(invoiceId: String, description: String, callBack: PagcertoCallBack<Invoice>){
        val json = "{\"description\":\"$description\"}"
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(RecurringService::class.java).confirmInvoicePayment(invoiceId, dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Invoice::class.java))
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
}