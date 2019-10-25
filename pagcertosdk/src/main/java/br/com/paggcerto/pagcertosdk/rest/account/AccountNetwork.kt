package br.com.paggcerto.pagcertosdk.rest.account

import android.graphics.Bitmap
import br.com.paggcerto.pagcertosdk.PagcertoCallBack
import br.com.paggcerto.pagcertosdk.model.account.request.*
import br.com.paggcerto.pagcertosdk.model.account.response.*
import br.com.paggcerto.pagcertosdk.util.JSONUtils
import br.com.paggcerto.pagcertosdk.util.Util
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class AccountNetwork(token: Token? = null) {

    private val appService = AccountClient.getClient(token)

    private val connectionError = "Não foi possível conectar ao servidor Paggcerto. Tente novamente."

    private val error401 = "Usuário não autenticado (credenciais incorretas ou token inválido)"
    private val error403 = "Usuário autenticado, porém sem permissão (acesso negado)"
    private val unknownError = "Erro inesperado."

    private val gson = Gson()

    fun signin(applicationId: String, loginForm: LoginForm, callBack: PagcertoCallBack<Token>) {

        val json = gson.toJson(loginForm)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        appService.create(AccountService::class.java).login(applicationId, dataObject).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson(response.body(), Token::class.java))
                    response.code() == 422 -> callBack.onError(response.code(), Util.printError(response))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
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

    fun identify(callBack: PagcertoCallBack<UserWhoAmI>){

        appService.create(AccountService::class.java).identify().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), UserWhoAmI::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")

                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun presets(callBack: PagcertoCallBack<PresetsResponse>){
        appService.create(AccountService::class.java).presets().enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), PresetsResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun banks(callBack: PagcertoCallBack<BanksList>){
        appService.create(AccountService::class.java).banks().enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), BanksList::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun sendRecoveryEmail(applicationId: String, recoveryEmail: RecoveryEmail, callBack: PagcertoCallBack<Boolean>){
        val json = gson.toJson(recoveryEmail)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        appService.create(AccountService::class.java).sendRecoveryEmail(applicationId, dataObject).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    204 -> callBack.onSuccess(true)
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun authHash(applicationId: String, hash: String, callBack: PagcertoCallBack<Token>){
        appService.create(AccountService::class.java).authHash(applicationId, hash).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), Token::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun createNewPassword(password: String, callBack: PagcertoCallBack<Token>){
        val json = "{\"password\":\"$password\"}"
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        appService.create(AccountService::class.java).createNewPassword(dataObject).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), Token::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun createAccount(applicationId: String, createAccountRequest: CreateAccountRequest, callBack: PagcertoCallBack<CreateAccountResponse>){
        val json = gson.toJson(createAccountRequest)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        appService.create(AccountService::class.java).createAccount(applicationId, dataObject).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), CreateAccountResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun updatePresets(presetsRequest: PresetsRequest, callBack: PagcertoCallBack<Boolean>){
        val json = gson.toJson(presetsRequest)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        appService.create(AccountService::class.java).updatePresets(dataObject).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    204 -> callBack.onSuccess(true)
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun businessType(callBack: PagcertoCallBack<BusinessTypesList>){
        appService.create(AccountService::class.java).businessType().enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), BusinessTypesList::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun cities(uf: String, callBack: PagcertoCallBack<CitiesList>){
        appService.create(AccountService::class.java).cities(uf).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), CitiesList::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun businessActivities(callBack: PagcertoCallBack<BusinessActivitiesList>){
        appService.create(AccountService::class.java).businessActivities().enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), BusinessActivitiesList::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun marketingMedias(callBack: PagcertoCallBack<MarketingMediasList>){
        appService.create(AccountService::class.java).marketingMedias().enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), MarketingMediasList::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    @Throws(Exception::class)
    fun sendDocumentFront(documentFront: Bitmap, callBack: PagcertoCallBack<Boolean>) {
        val photoDocumentFrontByteArray = getByteArray(documentFront)

        if (photoDocumentFrontByteArray != null) {
            val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), photoDocumentFrontByteArray)
            val body = MultipartBody.Part.createFormData("document", "doc_front.png", requestFile)
            appService.create(AccountService::class.java).sendDocumentFront(body).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when(response.code()) {
                        204 -> callBack.onSuccess(true)
                        400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        422 -> callBack.onError(response.code(), Util.printError(response))
                        401 -> callBack.onError(response.code(), error401)
                        403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })

        } else {
            throw Exception("Falha o converter imagem em array.")
        }
    }

    @Throws(Exception::class)
    fun sendDocumentBack(documentBack: Bitmap, callBack: PagcertoCallBack<Boolean>) {
        val photoDocumentFrontByteArray = getByteArray(documentBack)

        if (photoDocumentFrontByteArray != null) {
            val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), photoDocumentFrontByteArray)
            val body = MultipartBody.Part.createFormData("document", "doc_back.png", requestFile)
            appService.create(AccountService::class.java).sendDocumentBack(body).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when(response.code()) {
                        204 -> callBack.onSuccess(true)
                        400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        422 -> callBack.onError(response.code(), Util.printError(response))
                        401 -> callBack.onError(response.code(), error401)
                        403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })

        } else {
            throw Exception("Falha o converter imagem em array.")
        }
    }

    @Throws(Exception::class)
    fun sendSelfie(documentSelfie: Bitmap, callBack: PagcertoCallBack<Boolean>) {
        val photoDocumentFrontByteArray = getByteArray(documentSelfie)

        if (photoDocumentFrontByteArray != null) {
            val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), photoDocumentFrontByteArray)
            val body = MultipartBody.Part.createFormData("selfie", "selfie.png", requestFile)
            appService.create(AccountService::class.java).sendSelfie(body).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when(response.code()) {
                        204 -> callBack.onSuccess(true)
                        400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        422 -> callBack.onError(response.code(), Util.printError(response))
                        401 -> callBack.onError(response.code(), error401)
                        403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })

        } else {
            throw Exception("Falha o converter imagem em array.")
        }
    }

    private fun getByteArray(bmp: Bitmap): ByteArray? {
        try {
            val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            return stream.toByteArray()

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun updatePassword(oldPassword: String, newPassword: String, callBack: PagcertoCallBack<Token>){
        val json = "{\n" +
                "\"oldPassword\": \"${oldPassword}\",\n" +
                "\"password\": \"${newPassword}\"\n" +
                "}"
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        appService.create(AccountService::class.java).updatePassword(dataObject).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), Token::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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


    fun createProfile(name: String, callBack: PagcertoCallBack<ProfileResponse>){
        val json = "{\n" +
                "  \"name\": \"${name}\"\n" +
                "}"
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        appService.create(AccountService::class.java).createProfile(dataObject).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), ProfileResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun updateProfile(idProfile: String, profileRequest: ProfileRequest, callBack: PagcertoCallBack<ProfileResponse>){
        val json = gson.toJson(profileRequest)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        appService.create(AccountService::class.java).updateProfile(idProfile, dataObject).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), ProfileResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun listProfiles(filterProfile: FilterProfile, callBack: PagcertoCallBack<ProfilesResponseList>){
        try {
            var json = gson.toJson(filterProfile)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()

            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            appService.create(AccountService::class.java).listProfiles(retMap).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when(response.code()) {
                        200 -> callBack.onSuccess(gson.fromJson(response.body(), ProfilesResponseList::class.java))
                        400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        422 -> callBack.onError(response.code(), Util.printError(response))
                        401 -> callBack.onError(response.code(), error401)
                        403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
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

    fun searchProfile(idProfile: String, callBack: PagcertoCallBack<ProfileResponse>){
        appService.create(AccountService::class.java).searchProfile(idProfile).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), ProfileResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun disableProfile(idProfile: String, callBack: PagcertoCallBack<ProfileResponse>){
        appService.create(AccountService::class.java).disableProfile(idProfile).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), ProfileResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun enableProfile(idProfile: String, callBack: PagcertoCallBack<ProfileResponse>){
        appService.create(AccountService::class.java).enableProfile(idProfile).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), ProfileResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun deleteProfile(idProfile: String, callBack: PagcertoCallBack<ProfileResponse>){
        appService.create(AccountService::class.java).deleteProfile(idProfile).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson(response.body(), ProfileResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun grantPermission(roleId: String, scopesList: ScopesList, callBack: PagcertoCallBack<Boolean>){
        val json = gson.toJson(scopesList)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        appService.create(AccountService::class.java).grantPermission(roleId, dataObject).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    204 -> callBack.onSuccess(true)
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun revokePermission(roleId: String, scopesList: ScopesList, callBack: PagcertoCallBack<Boolean>){
        val json = gson.toJson(scopesList)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        appService.create(AccountService::class.java).revokePermission(roleId, dataObject).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    204 -> callBack.onSuccess(true)
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun updatePartnerClients(partnerClientRequest: PartnerClientRequest, callBack: PagcertoCallBack<Boolean>){
        val json = gson.toJson(partnerClientRequest)
        val dataObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)

        appService.create(AccountService::class.java).updatePartnerClients(dataObject).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    204 -> callBack.onSuccess(true)
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), Util.printError(response))
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

    fun getPartnerClients(filterProfile: FilterProfile, callBack: PagcertoCallBack<SellersList>){
        try {
            var json = gson.toJson(filterProfile)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()

            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            appService.create(AccountService::class.java).getPartnerClients(retMap).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when(response.code()) {
                        200 -> callBack.onSuccess(gson.fromJson(response.body(), SellersList::class.java))
                        400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                        422 -> callBack.onError(response.code(), Util.printError(response))
                        401 -> callBack.onError(response.code(), error401)
                        403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
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
}