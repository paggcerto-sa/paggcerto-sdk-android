package br.com.paggcerto.pagcertosdk

import br.com.paggcerto.pagcertosdk.model.recurring.RecurringSetting
import br.com.paggcerto.pagcertosdk.model.recurring.request.*
import br.com.paggcerto.pagcertosdk.model.recurring.response.ContractResponse
import br.com.paggcerto.pagcertosdk.model.recurring.response.ContractResponseList
import br.com.paggcerto.pagcertosdk.model.recurring.response.Invoice
import br.com.paggcerto.pagcertosdk.model.recurring.response.InvoicesList
import br.com.paggcerto.pagcertosdk.rest.recurring.RecurringNetwork
import org.junit.Test
import java.util.concurrent.CountDownLatch

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RestRecurringTest: BaseRest() {
    private val recurringNetwork by lazy { RecurringNetwork() }

    @Test
    fun registerSettings(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<RecurringSetting>{
            override fun onSuccess(obj: RecurringSetting) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        recurringNetwork.registerSettings(RecurringSetting(
            payableWithCard = false,
            payableWithBankSlip = false
        ), callBack)
        signal.await()
    }

    @Test
    fun updateSettings(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<RecurringSetting>{
            override fun onSuccess(obj: RecurringSetting) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        recurringNetwork.updateSettings(RecurringSetting(
            payableWithCard = false,
            payableWithBankSlip = false
        ), callBack)
        signal.await()
    }

    @Test
    fun registerContract(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ContractResponse>{
            override fun onSuccess(obj: ContractResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        val cardConfiguration = CardConfiguration(null, "", 0, 0, 0, 0, 0, 0, 0, "", "", "")
        val bankSlipConfiguration = BankSlipConfiguration(0, 0, 0, "", "", "")
        val paymentConfiguration = PaymentConfiguration(0, "", cardConfiguration, bankSlipConfiguration)
        val customer = Customer("", "", "", "")
        val contractRequest = ContractRequest("", "", paymentConfiguration, customer, "", null, "", false, null, emptyList())

        recurringNetwork.registerContract(contractRequest, callBack)
        signal.await()
    }

    @Test
    fun updateContract(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ContractResponse>{
            override fun onSuccess(obj: ContractResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        val cardConfiguration = CardConfiguration(null, "", 0, 0, 0, 0, 0, 0, 0, "", "", "")
        val bankSlipConfiguration = BankSlipConfiguration(0, 0, 0, "", "", "")
        val paymentConfiguration = PaymentConfiguration(0, "", cardConfiguration, bankSlipConfiguration)
        val customer = Customer("", "", "", "")
        val contractRequest = ContractRequest("", "", paymentConfiguration, customer, "", null, "", false, null, emptyList())

        recurringNetwork.updateContract("", contractRequest, callBack)
        signal.await()
    }

    @Test
    fun getContract(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ContractResponseList>{
            override fun onSuccess(obj: ContractResponseList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
       }

        recurringNetwork.getContract(FilterContract(), callBack)
        signal.await()
    }

    @Test
    fun searchContract(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ContractResponse>{
            override fun onSuccess(obj: ContractResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
       }

        recurringNetwork.searchContract("", callBack)
        signal.await()
    }

    @Test
    fun cancelContract(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ContractResponse>{
            override fun onSuccess(obj: ContractResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
       }

        recurringNetwork.cancelContract("", "", callBack)
        signal.await()
    }

    @Test
    fun pauseContract(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ContractResponse>{
            override fun onSuccess(obj: ContractResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
       }

        recurringNetwork.pauseContract("", "", callBack)
        signal.await()
    }

    @Test
    fun unlockContract(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ContractResponse>{
            override fun onSuccess(obj: ContractResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
       }

        recurringNetwork.unlockContract("", callBack)
        signal.await()
    }

    @Test
    fun addendumContract(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ContractResponse>{
            override fun onSuccess(obj: ContractResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
       }

        recurringNetwork.addendumContract("oknj", Addendums(0, null, null, null, null), callBack)
        signal.await()
    }

    @Test
    fun deleteAddendumContract(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ContractResponse>{
            override fun onSuccess(obj: ContractResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
       }

        recurringNetwork.deleteAddendumContract("oknj", "", callBack)
        signal.await()
    }

    @Test
    fun getInvoices(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<InvoicesList>{
            override fun onSuccess(obj: InvoicesList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
       }

        recurringNetwork.getInvoices(FilterContract(), callBack)
        signal.await()
    }

    @Test
    fun searchInvoice(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Invoice>{
            override fun onSuccess(obj: Invoice) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
       }

        recurringNetwork.searchInvoice("id", callBack)
        signal.await()
    }

    @Test
    fun confirmInvoicePayment(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Invoice>{
            override fun onSuccess(obj: Invoice) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
       }

        recurringNetwork.confirmInvoicePayment("id", "XD", callBack)
        signal.await()
    }
}