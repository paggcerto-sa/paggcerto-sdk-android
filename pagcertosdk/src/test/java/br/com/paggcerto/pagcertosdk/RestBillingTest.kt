package br.com.paggcerto.pagcertosdk

import br.com.paggcerto.pagcertosdk.model.billing.request.BillingRequest
import br.com.paggcerto.pagcertosdk.model.billing.request.FilterBilling
import br.com.paggcerto.pagcertosdk.model.billing.response.Billing
import br.com.paggcerto.pagcertosdk.model.billing.response.ListBillings
import br.com.paggcerto.pagcertosdk.rest.billing.BillingNetwork
import org.junit.Test
import org.mockito.Mockito
import java.util.concurrent.CountDownLatch

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RestBillingTest: BaseRest(){
    private val billingNetwork by lazy { BillingNetwork() }

    @Test
    fun createBilling(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Billing>{
            override fun onSuccess(obj: Billing) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        billingNetwork.createBilling(BillingRequest(), callBack)
        signal.await()
    }

    @Test
    fun listBillings(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ListBillings>{
            override fun onSuccess(obj: ListBillings) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        billingNetwork.listBillings(FilterBilling(), callBack)
        signal.await()
    }

    @Test
    fun getBilling(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Billing>{
            override fun onSuccess(obj: Billing) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        billingNetwork.getBilling(Mockito.anyString(), callBack)
        signal.await()
    }

    @Test
    fun cancelBilling(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method, 200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        billingNetwork.cancelBilling(Mockito.anyString(), callBack)
        signal.await()
    }
}