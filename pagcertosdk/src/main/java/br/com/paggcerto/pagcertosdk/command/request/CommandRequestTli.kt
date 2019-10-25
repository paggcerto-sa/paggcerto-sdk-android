package br.com.paggcerto.pagcertosdk.command.request

internal class CommandRequestTli constructor(commandRequestGcr: CommandRequestGcr): RequestCard {

    var CMD_ID: String? = null
    var CMD_LEN1: String? = null
    var TLI_ACQIDX: String? = null
    var TLI_TABVER: String? = null

    init {
        CMD_ID = "TLI"
        CMD_LEN1 = "012"
        TLI_ACQIDX = commandRequestGcr.GCR_ACQIDXREQ
        TLI_TABVER = commandRequestGcr.GCR_TABVER
    }

    override fun createRequest(): String {
        return CMD_ID + CMD_LEN1 + TLI_ACQIDX + TLI_TABVER
    }
}