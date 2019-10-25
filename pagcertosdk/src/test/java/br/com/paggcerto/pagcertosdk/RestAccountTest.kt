package br.com.paggcerto.pagcertosdk

import android.graphics.Bitmap
import br.com.paggcerto.pagcertosdk.model.account.request.*
import br.com.paggcerto.pagcertosdk.model.account.request.Address
import br.com.paggcerto.pagcertosdk.model.account.request.BankAccount
import br.com.paggcerto.pagcertosdk.model.account.request.Holder
import br.com.paggcerto.pagcertosdk.model.account.request.TransferPlan
import br.com.paggcerto.pagcertosdk.model.account.request.User
import br.com.paggcerto.pagcertosdk.model.account.response.*
import br.com.paggcerto.pagcertosdk.rest.account.AccountNetwork
import com.google.gson.Gson
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import java.util.concurrent.CountDownLatch
import java.lang.Exception


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RestAccountTest: BaseRest() {
    private val accountNetwork by lazy { AccountNetwork(PagcertoSDK.token) }
    private val applicationId: String = "VX"
    private val testError: String = "ISOLATE TEST"

    //default objects
    private val bankAccount by lazy { BankAccount(true, "", "", "", "", "") }
    private val transferPlan by lazy { TransferPlan(null, null, null, anticipated = false, migrate = false) }
    private val address by lazy { Address("", "", "", "", "", "") }
    private val holder by lazy { Holder("", "", " "[0], "", "", "", null) }
    private val user by lazy { User("", "") }

    @Test
    fun signin(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Token>{
            override fun onSuccess(obj: Token) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.signin(applicationId, LoginForm(), callBack)
        signal.await()
    }

    @Test
    fun identify(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<UserWhoAmI>{
            override fun onSuccess(obj: UserWhoAmI) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.identify(callBack)
        signal.await()
    }

    @Test
    fun presets(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<PresetsResponse>{
            override fun onSuccess(obj: PresetsResponse) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.presets(callBack)
        signal.await()
    }

    @Test
    fun banks(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<BanksList>{
            override fun onSuccess(obj: BanksList) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.banks(callBack)
        signal.await()
    }

    @Test
    fun sendRecoveryEmail(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.sendRecoveryEmail(applicationId, RecoveryEmail(), callBack)
        signal.await()
    }

    @Test
    fun authHash(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Token>{
            override fun onSuccess(obj: Token) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.authHash(applicationId, "", callBack)
        signal.await()
    }

    @Test
    fun createNewPassword(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Token>{
            override fun onSuccess(obj: Token) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.createNewPassword("1234567@", callBack)
        signal.await()
    }

    @Test
    fun createAccount(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CreateAccountResponse>{
            override fun onSuccess(obj: CreateAccountResponse) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }

        val createAccountRequest = Gson().fromJson("{\n" +
                "    \"holder\": {\n" +
                "        \"fullName\": \"Breno Cruz\",\n" +
                "        \"birthDate\": \"1994-01-04\",\n" +
                "        \"gender\": \"M\",\n" +
                "        \"taxDocument\": \"546.249.542-03\",\n" +
                "        \"phone\": \"(79) 2942-7954\",\n" +
                "        \"mobile\": \"(79) 99191-9241\",\n" +
                "        \"company\": {\n" +
                "            \"tradeName\": \"Esportes ME\",\n" +
                "            \"fullName\": \"Breno Esportes ME\",\n" +
                "            \"taxDocument\": \"55.476.472/0001-07\",\n" +
                "            \"businessTypeId\": \"vL\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"address\": {\n" +
                "        \"cityCode\": \"2800308\",\n" +
                "        \"district\": \"Inacio Barbosa\",\n" +
                "        \"line1\": \"R Manoel De Oliveira Martins\",\n" +
                "        \"line2\": \"Ap 001, Cleveland House\",\n" +
                "        \"streetNumber\": \"229\",\n" +
                "        \"zipCode\": \"49040-830\"\n" +
                "    },\n" +
                "    \"bankAccount\": {\n" +
                "        \"bankNumber\": \"001\",\n" +
                "        \"accountNumber\": \"123456-78\",\n" +
                "        \"bankBranchNumber\": \"1232\",\n" +
                "        \"variation\": \"0\",\n" +
                "        \"type\": \"corrente\",\n" +
                "        \"isJuristic\": false\n" +
                "    },\n" +
                "    \"user\": {\n" +
                "        \"email\": \"brenocruz@email.com\",\n" +
                "        \"password\": \"12345678\"\n" +
                "    },\n" +
                "    \"businessActivityId\": \"K8\",\n" +
                "    \"marketingMediaId\": \"eB\",\n" +
                "    \"transferPlan\": {\n" +
                "        \"daysPinpad\": 2,\n" +
                "        \"daysOnline\": 32,\n" +
                "        \"anticipated\": true,\n" +
                "        \"oldPlan\": false\n" +
                "    }\n" +
                "}", CreateAccountRequest::class.java)


//        val createAccountRequest = CreateAccountRequest(
//            holder, address,
//            bankAccount, user,
//            String.toString(), anyString(),
//            transferPlan,
//            anyString(), anyString()
//        )
        accountNetwork.createAccount(applicationId, createAccountRequest, callBack)
        signal.await()
    }

    @Test
    fun updatePresets(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }

        val request = PresetsRequest("", "", "", "", "", transferPlan, bankAccount, address)
        accountNetwork.updatePresets(request, callBack)
        signal.await()
    }

    @Test
    fun businessType(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<BusinessTypesList>{
            override fun onSuccess(obj: BusinessTypesList) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.businessType(callBack)
        signal.await()
    }

    @Test
    fun cities(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CitiesList>{
            override fun onSuccess(obj: CitiesList) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.cities("SE", callBack)
        signal.await()
    }

    @Test
    fun businessActivities(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<BusinessActivitiesList>{
            override fun onSuccess(obj: BusinessActivitiesList) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.businessActivities(callBack)
        signal.await()
    }

    @Test
    fun marketingMedias(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<MarketingMediasList>{
            override fun onSuccess(obj: MarketingMediasList) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.marketingMedias(callBack)
        signal.await()
    }

    @Test
    fun sendDocumentFront(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.sendDocumentFront(mock(Bitmap::class.java), callBack)
        signal.await()
    }

    @Test
    fun sendDocumentBack(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.sendDocumentBack(mock(Bitmap::class.java), callBack)
        signal.await()
    }

    @Test
    fun sendSelfie(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        try {
            accountNetwork.sendSelfie(mock(Bitmap::class.java), callBack)
        }catch (ex: Exception){
            signal.countDown()
            System.err.println("[[$method]] -> $testError")
        }

        signal.await()
    }

    @Test
    fun updatePassword(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Token>{
            override fun onSuccess(obj: Token) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.updatePassword("ggs", "gta", callBack)
        signal.await()
    }

    @Test
    fun createProfile(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ProfileResponse>{
            override fun onSuccess(obj: ProfileResponse) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.createProfile("Master of Puppets", callBack)
        signal.await()
    }

    @Test
    fun updateProfile(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ProfileResponse>{
            override fun onSuccess(obj: ProfileResponse) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.updateProfile("mYG", ProfileRequest("The unforgiven", false), callBack)
        signal.await()
    }

    @Test
    fun listProfiles(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ProfilesResponseList>{
            override fun onSuccess(obj: ProfilesResponseList) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.listProfiles(FilterProfile(), callBack)
        signal.await()
    }

    @Test
    fun searchProfile(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ProfileResponse>{
            override fun onSuccess(obj: ProfileResponse) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.searchProfile("mYG", callBack)
        signal.await()
    }

    @Test
    fun disableProfile(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ProfileResponse>{
            override fun onSuccess(obj: ProfileResponse) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.disableProfile("mYG", callBack)
        signal.await()
    }

    @Test
    fun enableProfile(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ProfileResponse>{
            override fun onSuccess(obj: ProfileResponse) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.enableProfile("mYG", callBack)
        signal.await()
    }

    @Test
    fun deleteProfile(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ProfileResponse>{
            override fun onSuccess(obj: ProfileResponse) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.deleteProfile("mYG", callBack)
        signal.await()
    }

    @Test
    fun grantPermission(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.grantPermission(anyString(), ScopesList(emptyList()), callBack)
        signal.await()
    }

    @Test
    fun revokePermission(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.revokePermission("fsd", ScopesList(emptyList()), callBack)
        signal.await()
    }

    @Test
    fun updatePartnerClients(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.updatePartnerClients(PartnerClientRequest(emptyList(), transferPlan), callBack)
        signal.await()
    }

    @Test
    fun getPartnerClients(){
        val signal = CountDownLatch(1)
        val method = object {}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<SellersList>{
            override fun onSuccess(obj: SellersList) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        accountNetwork.getPartnerClients(FilterProfile(), callBack)
        signal.await()
    }
}