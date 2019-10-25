package br.com.paggcerto.pagcertosdk.command.request

import br.com.paggcerto.pagcertosdk.model.table.Aid
import br.com.paggcerto.pagcertosdk.model.table.Capk

internal class CommandRequestTlr: RequestCard {

    var CMD_ID: String? = null
    var CMD_LEN1: String? = null
    var tableToUpload: String? = null

    init{
        CMD_ID = "TLR"
    }

    constructor(aid: Aid){
        CMD_LEN1 = aid.toString().length.toString()
        tableToUpload = aid.toString()
    }

    constructor(capk: Capk){
        CMD_LEN1 = capk.toString().length.toString()
        tableToUpload = capk.toString()
    }

    override fun createRequest(): String {
        return CMD_ID + CMD_LEN1 + tableToUpload
    }

}