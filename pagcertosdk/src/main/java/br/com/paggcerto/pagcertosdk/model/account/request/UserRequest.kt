package br.com.paggcerto.pagcertosdk.model.account.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class UserRequest: Serializable {

    @SerializedName("roleId")
    @Expose
    var roleId: String? = null

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("taxDocument")
    @Expose
    var taxDocument: String? = null

    @SerializedName("appUrl")
    @Expose
    var appUrl: String? = null
}