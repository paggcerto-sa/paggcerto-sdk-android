package br.com.paggcerto.pagcertosdk.command.request

internal class CommandRequestGin: RequestCard {
    var CMD_ID: String? = null
    var CMD_LEN1: String? = null
    var GIN_ACQIDX: String? = null

    init{
        CMD_ID = "GIN"
        CMD_LEN1 = "002"
        GIN_ACQIDX = "00"
    }

    /**Este comando obtém informações gerais sobre o pinpad e seu software/firmware**/
    override fun createRequest(): String {
        return CMD_ID + CMD_LEN1 + GIN_ACQIDX
    }
}