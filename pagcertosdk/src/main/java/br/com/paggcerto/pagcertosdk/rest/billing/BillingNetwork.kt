package br.com.paggcerto.pagcertosdk.rest.billing

import br.com.paggcerto.pagcertosdk.PagcertoCallBack
import br.com.paggcerto.pagcertosdk.PagcertoSDK
import br.com.paggcerto.pagcertosdk.model.billing.request.BillingRequest
import br.com.paggcerto.pagcertosdk.model.billing.request.FilterBilling
import br.com.paggcerto.pagcertosdk.model.billing.response.Billing
import br.com.paggcerto.pagcertosdk.model.billing.response.ListBillings
import br.com.paggcerto.pagcertosdk.util.JSONUtils
import br.com.paggcerto.pagcertosdk.util.Util.printError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class BillingNetwork {
    private val appService = BillingClient.getClient(PagcertoSDK.token)

    private val connectionError = "Não foi possível conectar ao servidor Paggcerto. Tente novamente."

    private val error401 = "Usuário não autenticado (credenciais incorretas ou token inválido)"
    private val error403 = "Usuário autenticado, porém sem permissão (acesso negado)"
    private val unknownError = "Erro inesperado."

    private val gson = Gson()

    fun createBilling(billingRequest: BillingRequest, callBack: PagcertoCallBack<Billing>){
        val json = gson.toJson(billingRequest)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call = appService.create(BillingService::class.java).createBilling(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), Billing::class.java))
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

    fun listBillings(filterBilling: FilterBilling, callBack: PagcertoCallBack<ListBillings>){
        var json = gson.toJson(filterBilling)
        json = JSONUtils.removeArrays(JSONObject(json)).toString()

        val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

        val call = appService.create(BillingService::class.java).listBillings(retMap)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), ListBillings::class.java))
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

    fun getBilling(idBilling: String, callBack: PagcertoCallBack<Billing>){
        val call = appService.create(BillingService::class.java).getBilling(idBilling)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), Billing::class.java))
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

    fun cancelBilling(idBilling: String, callBack: PagcertoCallBack<Boolean>){
        val call = appService.create(BillingService::class.java).cancelBilling(idBilling)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(true)
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
}