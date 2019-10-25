package br.com.paggcerto.pagcertosdk.rest.account

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

internal interface AccountService {
    /**Require token**/

    @GET("whoami")
    fun identify(): Call<String>

    @GET("presets")
    fun presets(): Call<String>

    @POST("presets")
    fun updatePresets(@Body presetsRequest: RequestBody): Call<String>

    @Multipart
    @POST("confirmation/send-selfie")
    fun sendSelfie(@Part sendSelfieObject: MultipartBody.Part): Call<String>

    @Multipart
    @POST("confirmation/send-document-front")
    fun sendDocumentFront(@Part sendDocumentFrontObject: MultipartBody.Part): Call<String>

    @Multipart
    @POST("confirmation/send-document-back")
    fun sendDocumentBack(@Part sendDocumentBackObject: MultipartBody.Part): Call<String>

    @POST("roles")
    fun createProfile(@Body name: RequestBody): Call<String>

    @PUT("roles/{id}")
    fun updateProfile(@Path("id") id: String, @Body profileRequest: RequestBody): Call<String>

    @GET("roles")
    fun listProfiles(@QueryMap filter: Map<String, String>): Call<String>

    @GET("roles/{id}")
    fun searchProfile(@Path("id") id: String): Call<String>

    @POST("roles/{id}/deactivate")
    fun disableProfile(@Path("id") id: String): Call<String>

    @POST("roles/{id}/activate")
    fun enableProfile(@Path("id") id: String): Call<String>

    @DELETE("roles/{id}")
    fun deleteProfile(@Path("id") id: String): Call<String>

    @POST("roles/{roleId}/scopes/grant")
    fun grantPermission(@Path("roleId") roleId: String, @Body scopes: RequestBody): Call<String>

    @POST("roles/{roleId}/scopes/revoke")
    fun revokePermission(@Path("roleId") roleId: String, @Body scopes: RequestBody): Call<String>

    @POST("partner/sellers/presets")
    fun updatePartnerClients(@Body partnerClientRequest: RequestBody): Call<String>

    @GET("partner/sellers")
    fun getPartnerClients(@QueryMap filter: Map<String, String>): Call<String>

    /**No require token**/

    @POST("{applicationId}/signin")
    fun login(@Path("applicationId") applicationId: String, @Body loginObject: RequestBody): Call<String>

    @POST("{applicationId}/signin/{hash}")
    fun authHash(@Path("applicationId") applicationId: String, @Path("hash") hash: String): Call<String>

    @POST("create-password")
    fun createNewPassword(@Body jsonObject: RequestBody): Call<String>

    @POST("change-password")
    fun updatePassword(@Body jsonObject: RequestBody): Call<String>

    @GET("banks")
    fun banks(): Call<String>

    @GET("business-types")
    fun businessType(): Call<String>

    @GET("cities/{uf}")
    fun cities(@Path("uf") uf: String): Call<String>

    @GET("business-activities")
    fun businessActivities(): Call<String>

    @GET("marketing-medias")
    fun marketingMedias(): Call<String>

    @POST("{applicationId}/send-email-recovery")
    fun sendRecoveryEmail(@Path("applicationId") applicationId: String, @Body recoveryEmail: RequestBody): Call<String>

    @POST("{applicationId}/signup/seller")
    fun createAccount(@Path("applicationId") applicationId: String, @Body createAccountRequest: RequestBody): Call<String>

}