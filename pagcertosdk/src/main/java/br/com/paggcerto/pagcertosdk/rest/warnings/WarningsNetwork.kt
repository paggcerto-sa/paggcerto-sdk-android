package br.com.paggcerto.pagcertosdk.rest.warnings

import br.com.paggcerto.pagcertosdk.PagcertoCallBack
import br.com.paggcerto.pagcertosdk.PagcertoSDK
import br.com.paggcerto.pagcertosdk.model.warnings.request.*
import br.com.paggcerto.pagcertosdk.model.warnings.response.*
import br.com.paggcerto.pagcertosdk.util.JSONUtils
import br.com.paggcerto.pagcertosdk.util.Util
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WarningsNetwork {
    private val appService = WarningsClient.getClient(PagcertoSDK.token)

    private val connectionError = "Não foi possível conectar ao servidor Paggcerto. Tente novamente."

    private val error401 = "Usuário não autenticado (credenciais incorretas ou token inválido)"
    private val error403 = "Usuário autenticado, porém sem permissão (acesso negado)"
    private val unknownError = "Erro inesperado."

    private val gson = Gson()

    fun messages(filterMessages: FilterMessages, callBack: PagcertoCallBack<Messages>){
        var json = gson.toJson(filterMessages)
        json = JSONUtils.removeArrays(JSONObject(json)).toString()
        val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

        val call = appService.create(WarningsService::class.java).messages(retMap)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Messages::class.java))
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

    fun readMessages(unreadMessages: UnreadMessages, callBack: PagcertoCallBack<Boolean>){
        val json = gson.toJson(unreadMessages)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String> = appService.create(WarningsService::class.java).unreadMessage(dataObject)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 204 -> callBack.onSuccess(true)
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    response.code() == 422 -> callBack.onError(response.code(), Util.printError(response))
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