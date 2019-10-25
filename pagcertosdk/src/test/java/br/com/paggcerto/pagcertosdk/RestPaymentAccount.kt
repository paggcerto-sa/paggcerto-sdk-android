package br.com.paggcerto.pagcertosdk

import br.com.paggcerto.pagcertosdk.model.payment_account.request.*
import br.com.paggcerto.pagcertosdk.model.payment_account.response.*
import br.com.paggcerto.pagcertosdk.rest.payment_account.PaymentAccountNetwork
import org.junit.Test
import java.util.concurrent.CountDownLatch

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RestPaymentAccount: BaseRest() {

    private val paymentAccountNetwork by lazy { PaymentAccountNetwork() }

    @Test
    fun getBalance(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Balance>{
            override fun onSuccess(obj: Balance) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.getBalance(callBack)
        signal.await()
    }

    @Test
    fun minimumAmount(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Number>{
            override fun onSuccess(obj: Number) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.minimumAmount(callBack)
        signal.await()
    }

    @Test
    fun splitterBalance(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<BalanceList>{
            override fun onSuccess(obj: BalanceList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.splitterBalance(listOf("Em", "jkBQ", "a9PR"), callBack)
        signal.await()
    }

    @Test
    fun getSettings(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<SettingsResponse>{
            override fun onSuccess(obj: SettingsResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.getSettings(callBack)
        signal.await()
    }

    @Test
    fun setSettings(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<SettingsResponse>{
            override fun onSuccess(obj: SettingsResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.setSettings(SettingsRequest().apply {
            monday = true
            tuesday = false
            wednesday = true
            thursday = false
            friday = true
        }, callBack)
        signal.await()
    }

    @Test
    fun setSplitterSettings(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<List<SettingsResponse>>{
            override fun onSuccess(obj: List<SettingsResponse>) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.setSplitterSettings(listOf(SettingsRequest().apply {
            splitterId = "Em"
            monday = true
            tuesday = false
            wednesday = true
            thursday = false
            friday = true
        }), callBack)
        signal.await()
    }

    @Test
    fun getSplitterSettings(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<SettingsResponse>{
            override fun onSuccess(obj: SettingsResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.getSplitterSettings("Em", callBack)
        signal.await()
    }

    @Test
    fun requestCashOut(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CashOutResponse>{
            override fun onSuccess(obj: CashOutResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.requestCashOut(CashOutRequest(30.0), callBack)
        signal.await()
    }

    @Test
    fun listCashOut(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CashOutList>{
            override fun onSuccess(obj: CashOutList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.listCashOut(FilterCashOut(), callBack)
        signal.await()
    }

    @Test
    fun requestCashOutSplitter(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CashOutResponse>{
            override fun onSuccess(obj: CashOutResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.requestCashOutSplitter(CashOutSplitterRequest("Em", 30.0), callBack)
        signal.await()
    }

    @Test
    fun listCashOutSplitter(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CashOutList>{
            override fun onSuccess(obj: CashOutList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.listCashOutSplitter(FilterCashOut().apply { splitterId = "Em" }, callBack)
        signal.await()
    }

    @Test
    fun transferSplitterCashOut(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CashOutResponse>{
            override fun onSuccess(obj: CashOutResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.transferSplitterCashOut(SplitterCashOutRequest(1.0, "Em"), callBack)
        signal.await()
    }

    @Test
    fun getStatements(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Statement>{
            override fun onSuccess(obj: Statement) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.getStatements(FilterStatement(), callBack)
        signal.await()
    }

    @Test
    fun transactionDetail(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<TransactionsDetailList>{
            override fun onSuccess(obj: TransactionsDetailList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.transactionDetail("2019-07-12", FilterStatementDetail(), callBack)
        signal.await()
    }

    @Test
    fun transactionLinkDetail(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<TransactionLinkDetail>{
            override fun onSuccess(obj: TransactionLinkDetail) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentAccountNetwork.transactionLinkDetail("p6g6", callBack)
        signal.await()
    }
}