package br.com.paggcerto.pagcertosdk

import br.com.paggcerto.pagcertosdk.model.payments.request.*
import br.com.paggcerto.pagcertosdk.model.payments.response.*
import br.com.paggcerto.pagcertosdk.rest.payment.PaymentNetwork
import org.junit.Test
import org.mockito.Mockito.mock
import java.util.concurrent.CountDownLatch

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RestPaymentsTest: BaseRest() {
    private val paymentNetwork by lazy { PaymentNetwork() }
    private val address = Address("", "", "", 0, "", null, null, null)
    private val bankAccount =  BankAccount("", "", 0, "", "", "", null)

    @Test
    fun getBin(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<List<Bin>>{
            override fun onSuccess(obj: List<Bin>) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.getBin(callBack)
        signal.await()
    }

    @Test
    fun payWithCard(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Payment>{
            override fun onSuccess(obj: Payment) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.payWithCard(mock(Pay::class.java), callBack)
        signal.await()
    }

    @Test
    fun getTransferDays(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<TransferDays>{
            override fun onSuccess(obj: TransferDays) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.getTransferDays(callBack)
        signal.await()
    }

    @Test
    fun payments(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<HistoryPayment>{
            override fun onSuccess(obj: HistoryPayment) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.payments(FilterHistoryPayment(), callBack)
        signal.await()
    }

    @Test
    fun findPayment(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Payment>{
            override fun onSuccess(obj: Payment) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.findPayment("xW91", callBack)
        signal.await()
    }


    @Test/******************404 not found***************/
    fun sendReceipt(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.sendReceipt("a86V", SendReceipt().apply {
            email = "email@email.com"
        }, callBack)
        signal.await()
    }

    @Test
    fun pdfBankSlipPayment(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ByteArray?>{
            override fun onSuccess(obj: ByteArray?) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.pdfBankSlipPayment("37m", callBack)
        signal.await()
    }

    @Test/******************404 not found***************/
    fun cancelCardTransaction(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Payment>{
            override fun onSuccess(obj: Payment) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.cancelCardTransaction("37m", callBack)
        signal.await()
    }

    @Test
    fun cancelPayment(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Payment>{
            override fun onSuccess(obj: Payment) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.cancelPayment("37m", callBack)
        signal.await()
    }

    @Test
    fun bankSlipPay(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<BankSlipPayment>{
            override fun onSuccess(obj: BankSlipPayment) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.bankSlipPay(BankSlipsPay(), callBack)
        signal.await()
    }

    @Test
    fun finalizePayment(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Payment>{
            override fun onSuccess(obj: Payment) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.finalizePayment("8Qb7", "test api", callBack)
        signal.await()
    }

    @Test
    fun cancelBankSlip(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Payment>{
            override fun onSuccess(obj: Payment) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.cancelBankSlip("28b", "test api", callBack)
        signal.await()
    }

    @Test
    fun replaceBankslip(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Payment>{
            override fun onSuccess(obj: Payment) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.replaceBankslip("nbN5", ReplaceBankSlips().apply {
            dueDate = "2019-10-12"
            acceptedUntil = 10
        }, callBack)
        signal.await()
    }

    @Test
    fun historyAnticipation(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<AnticipationHistory>{
            override fun onSuccess(obj: AnticipationHistory) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.historyAnticipation(FilterAnticipation(), callBack)
        signal.await()
    }

    @Test
    fun cardTransactionsAvailable(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<AnticipableTransaction>{
            override fun onSuccess(obj: AnticipableTransaction) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.cardTransactionsAvailable(FilterTransaction(), callBack)
        signal.await()
    }

    @Test
    fun requestAnticipation(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Anticipation>{
            override fun onSuccess(obj: Anticipation) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.requestAnticipation(TransactionsToAnticipate(), callBack)
        signal.await()
    }

    @Test
    fun anticipationTransactions(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<AnticipableTransaction>{
            override fun onSuccess(obj: AnticipableTransaction) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.anticipationTransactions("pX", FilterTransaction(), callBack)
        signal.await()
    }

    @Test
    fun simulate(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<SimulationResult>{
            override fun onSuccess(obj: SimulationResult) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.simulate(Simulation().apply {
            amount = 150
            credit = true
            pinpad = false
            cardBrand = "visa"
            installments = 1
            customerPaysFee = false
        }, callBack)
        signal.await()
    }

    @Test
    fun createCard(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CardResponse>{
            override fun onSuccess(obj: CardResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.createCard(CardRequest("Maria", "5168441223630339" , 12, 2020, 123, false), callBack)
        signal.await()
    }

    @Test
    fun cards(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CardResponseList>{
            override fun onSuccess(obj: CardResponseList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.cards(FilterCards(), callBack)
        signal.await()
    }

    @Test
    fun findCard(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CardResponse>{
            override fun onSuccess(obj: CardResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.findCard("LMrE", callBack)
        signal.await()
    }

    @Test
    fun deleteCard(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CardResponse>{
            override fun onSuccess(obj: CardResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.deleteCard("LMrE", callBack)
        signal.await()
    }

    @Test
    fun createSplitter(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<SplitterResponse>{
            override fun onSuccess(obj: SplitterResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.createSplitter(SplitterRequest("Zé Um", 32, false, address, bankAccount), callBack)
        signal.await()
    }

    @Test
    fun updateSplitter(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<SplitterResponse>{
            override fun onSuccess(obj: SplitterResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.updateSplitter("xnlv", SplitterRequest("", 0, false, address, bankAccount), callBack)
        signal.await()
    }

    @Test
    fun splitters(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<SplitterResponseList>{
            override fun onSuccess(obj: SplitterResponseList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.splitters(FilterSplitter(), callBack)
        signal.await()
    }

    @Test
    fun findSplitter(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<SplitterResponse>{
            override fun onSuccess(obj: SplitterResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.findSplitter("xnlv", callBack)
        signal.await()
    }

    @Test
    fun getTransfersSplitter(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<TransfersSplit>{
            override fun onSuccess(obj: TransfersSplit) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.getTransfersSplitter(FilterTransferSplitter(), callBack)
        signal.await()
    }

    @Test
    fun getCardTransactions(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CardTransactionLink>{
            override fun onSuccess(obj: CardTransactionLink) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.getCardTransactions("XdAg", 1, callBack)
        signal.await()
    }

    @Test
    fun getBankSlipTransaction(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<BankSlipTransactionLink>{
            override fun onSuccess(obj: BankSlipTransactionLink) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.getBankSlipTransaction("xW91", callBack)
        signal.await()
    }

    @Test
    fun getOtherTransaction(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<OtherTransactionLink>{
            override fun onSuccess(obj: OtherTransactionLink) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.getOtherTransaction("gg", callBack)
        signal.await()
    }

    @Test
    fun futureTransaction(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<FutureTransaction>{
            override fun onSuccess(obj: FutureTransaction) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.futureTransaction(FilterFutureTransaction(), callBack)
        signal.await()
    }

    @Test
    fun getFutureTransactionDetail(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<TransactionsList>{
            override fun onSuccess(obj: TransactionsList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.getFutureTransactionDetail("2019-07-12", FilterFutureTransaction(), callBack)
        signal.await()
    }

    @Test
    fun getBills(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Bills>{
            override fun onSuccess(obj: Bills) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.getBills(callBack)
        signal.await()
    }

    @Test
    fun transactions(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<TransactionResponse>{
            override fun onSuccess(obj: TransactionResponse) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.transactions(FilterTransactionRecord("2019-01-12", "2019-07-12"), callBack)
        signal.await()
    }

    @Test
    fun defaultFees(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Fee>{
            override fun onSuccess(obj: Fee) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.defaultFees(FilterFee().apply {
            daysOnline = 32
            daysPinpad = 2
            anticipated = true
        }, callBack)
        signal.await()
    }

    @Test
    fun fees(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Fee>{
            override fun onSuccess(obj: Fee) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.fees(callBack)
        signal.await()
    }

    @Test/************401 - Não autenticado*********************/
    fun registerBankSlipFee(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.registerBankSlipFee(HoldersBankSlipList(emptyList()), callBack)
        signal.await()
    }

    @Test/************401 - Não autenticado*********************/
    fun updateBankSlipFee(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.updateBankSlipFee(HoldersBankSlipList(emptyList()), callBack)
        signal.await()
    }

    @Test/************401 - Não autenticado*********************/
    fun deleteBankSlipFee(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.deleteBankSlipFee(HolderIdList(emptyList()), callBack)
        signal.await()
    }

    @Test/************401 - Não autenticado*********************/
    fun updateBankSlipFeeBase(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<FeeBase>{
            override fun onSuccess(obj: FeeBase) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.updateBankSlipFeeBase(0.0, callBack)
        signal.await()
    }

    @Test/************401 - Não autenticado*********************/
    fun registerCardFee(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.registerCardFee(HolderCard("", emptyList(), emptyList()), callBack)
        signal.await()
    }

    @Test/************401 - Não autenticado*********************/
    fun updateCardFeeBase(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<BrandFees>{
            override fun onSuccess(obj: BrandFees) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.updateCardFeeBase(BrandFees("", 0, 0, 0, 0), callBack)
        signal.await()
    }

    @Test/************401 - Não autenticado*********************/
    fun getCommission(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Commission>{
            override fun onSuccess(obj: Commission) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.getCommission(FilterComission(), callBack)
        signal.await()
    }

    @Test
    fun continuePayment(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Payment>{
            override fun onSuccess(obj: Payment) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.continuePayment("", Pay(), callBack)
        signal.await()
    }

    @Test
    fun capturePayment(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Payment>{
            override fun onSuccess(obj: Payment) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.capturePayment("gg", CapturePayment(0.0, emptyList()), callBack)
        signal.await()
    }

    @Test
    fun duplicateBankSlip(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Payment>{
            override fun onSuccess(obj: Payment) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.duplicateBankSlip("gg", "ggs", callBack)
        signal.await()
    }

    @Test
    fun getBankSlipPdfListZip(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ByteArray?>{
            override fun onSuccess(obj: ByteArray?) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.getBankSlipPdfListZip(emptyList(), callBack)
        signal.await()
    }

    @Test
    fun getBankSlipPdfList(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ByteArray?>{
            override fun onSuccess(obj: ByteArray?) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.getBankSlipPdfList(emptyList(), callBack)
        signal.await()
    }

    @Test
    fun sendSingleBankSlipByEmail(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.sendSingleBankSlipByEmail("xW91", "email@email.com", callBack)
        signal.await()
    }

    @Test
    fun sendMultipleBankSlipByEmail(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.sendMultipleBankSlipByEmail("xW91", "email@email.com", callBack)
        signal.await()
    }

    @Test
    fun checkoutBankSlipDetail(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CheckoutBankSlip>{
            override fun onSuccess(obj: CheckoutBankSlip) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.checkoutBankSlipDetail("1QA5", callBack)
        signal.await()
    }

    @Test
    fun checkoutBankSlipPdf(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ByteArray?>{
            override fun onSuccess(obj: ByteArray?) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.checkoutBankSlipPdf("gg", callBack)
        signal.await()
    }

    @Test
    fun checkoutBankSlipListDetail(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CheckoutBankSlipList>{
            override fun onSuccess(obj: CheckoutBankSlipList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.checkoutBankSlipListDetail("1QA5", callBack)
        signal.await()
    }

    @Test
    fun checkoutBankSlipListPdf(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<ByteArray?>{
            override fun onSuccess(obj: ByteArray?) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.checkoutBankSlipListPdf("gg", callBack)
        signal.await()
    }

    @Test
    fun getBankSlips(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<BankSlipsList>{
            override fun onSuccess(obj: BankSlipsList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.getBankSlips(FilterBankSlips(), callBack)
        signal.await()
    }

    @Test
    fun paymentsPerHourly(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<StatisticList>{
            override fun onSuccess(obj: StatisticList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.paymentsPerHourly(callBack)
        signal.await()
    }

    @Test
    fun paymentsPerWeekly(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<StatisticList>{
            override fun onSuccess(obj: StatisticList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.paymentsPerWeekly(callBack)
        signal.await()
    }

    @Test
    fun paymentsPerMonthly(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<StatisticList>{
            override fun onSuccess(obj: StatisticList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.paymentsPerMonthly(callBack)
        signal.await()
    }

    @Test
    fun paymentsPerYearly(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<StatisticList>{
            override fun onSuccess(obj: StatisticList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.paymentsPerYearly(callBack)
        signal.await()
    }

    @Test
    fun statisticsPaymentMethods(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<StatisticPayment>{
            override fun onSuccess(obj: StatisticPayment) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.statisticsPaymentMethods(callBack)
        signal.await()
    }

    @Test
    fun paymentsPerBankSlipsCurrentMonth(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<BankSlipPercentages>{
            override fun onSuccess(obj: BankSlipPercentages) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.paymentsPerBankSlipsCurrentMonth(callBack)
        signal.await()
    }

    @Test
    fun bankTransferFees(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<TransferFee>{
            override fun onSuccess(obj: TransferFee) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.bankTransferFees(callBack)
        signal.await()
    }

    @Test
    fun acceptTerms(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.acceptTerms(callBack)
        signal.await()
    }

    @Test
    fun canAnticipate(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<CanAnticipate>{
            override fun onSuccess(obj: CanAnticipate) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.canAnticipate(callBack)
        signal.await()
    }

    @Test
    fun anticipateAllTransactions(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Anticipation>{
            override fun onSuccess(obj: Anticipation) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.anticipateAllTransactions(callBack)
        signal.await()
    }

    @Test
    fun anticipationInProgress(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Anticipation>{
            override fun onSuccess(obj: Anticipation) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.anticipationInProgress(callBack)
        signal.await()
    }

    @Test
    fun anticipationDetail(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Anticipation>{
            override fun onSuccess(obj: Anticipation) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.anticipationDetail("gg", callBack)
        signal.await()
    }

    @Test
    fun transactionsInProgress(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<RequestedCardTransactionsList>{
            override fun onSuccess(obj: RequestedCardTransactionsList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.transactionsInProgress(FilterTransaction(), callBack)
        signal.await()
    }

    @Test
    fun futureTransactionSplitter(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<FutureTransaction>{
            override fun onSuccess(obj: FutureTransaction) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.futureTransactionSplitter(FilterSplitterTransaction(), callBack)
        signal.await()
    }

    @Test
    fun futureTransactionSplitterDetail(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<TransactionsList>{
            override fun onSuccess(obj: TransactionsList) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.futureTransactionSplitterDetail("2019-07-12" , FilterSplitterTransaction(), callBack)
        signal.await()
    }

    @Test
    fun simulateBankSlipPay(){
        val signal = CountDownLatch(1)
        val method = object{}.javaClass.enclosingMethod.name
        val callBack = object : PagcertoCallBack<Boolean>{
            override fun onSuccess(obj: Boolean) { assert(signal, method,200) }
            override fun onError(code: Int, message: String) { assert(signal, method, code, message) }
        }
        paymentNetwork.simulateBankSlipPay(BankSlipsIdList(listOf("xW91")), callBack)
        signal.await()
    }
}