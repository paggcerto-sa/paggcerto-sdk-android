package br.com.paggcerto.pagcertosdk.model.account.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginForm: Serializable{
    @Expose
    @SerializedName("login")
    var login: String? = null

    @Expose
    @SerializedName("password")
    var password: String? = null
}