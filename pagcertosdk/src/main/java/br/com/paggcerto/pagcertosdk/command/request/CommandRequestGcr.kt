package br.com.paggcerto.pagcertosdk.command.request

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

internal class CommandRequestGcr constructor(val credit: Boolean, val amount: String): RequestCard {

    var CMD_ID: String? = null
    var CMD_LEN1: String? = null
    var GCR_ACQIDXREQ: String? = null
    var GCR_APPTYPREQ: String? = null
    var GCR_AMOUNT: String? = null
    var GCR_DATE: String? = null
    var GCR_TIME: String? = null
    var GCR_TABVER: String? = null
    var GCR_QTDAPP: String? = null

    init {
        val dateFormat = SimpleDateFormat("yyMMdd", Locale("pt", "BR"))
        val timeFormat = SimpleDateFormat("HHmmss", Locale("pt", "BR"))
        val date = Calendar.getInstance().time
        CMD_ID = "GCR"
        CMD_LEN1 = "038"
        GCR_ACQIDXREQ = "08"/*key.toString().padStart(2, '0')*/
        GCR_APPTYPREQ = if(credit) "01" else "02"
        GCR_AMOUNT = amount.padStart(12, '0')
        GCR_DATE = dateFormat.format(date)
        GCR_TIME = timeFormat.format(date)
        GCR_TABVER = "3714633297"
        GCR_QTDAPP = "00"
    }

    /**Este comando inicia um processo de transação com cartão de pagamento**/
    override fun createRequest(): String{
        return CMD_ID + CMD_LEN1 + GCR_ACQIDXREQ + GCR_APPTYPREQ + GCR_AMOUNT + GCR_DATE + GCR_TIME + GCR_TABVER + GCR_QTDAPP
    }

    fun updateAmmountGoc(){
        val newAmmount = (amount.toDouble() * 100).toInt()
        GCR_AMOUNT = newAmmount.toString().padStart(12, '0')
    }

    fun updateAmmountGpn(){
        val format = NumberFormat.getCurrencyInstance()

        GCR_AMOUNT = format.format(amount.toDouble())
    }
}