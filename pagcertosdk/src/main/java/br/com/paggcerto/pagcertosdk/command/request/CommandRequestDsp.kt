package br.com.paggcerto.pagcertosdk.command.request

internal class CommandRequestDsp constructor(dspMessage: String): RequestCard {

    var CMD_ID: String? = null
    var CMD_LEN1: String? = null

    var DSP_MSG: String? = null


    init{
        CMD_ID = "DSP"
        CMD_LEN1 = "032"

        DSP_MSG = if(dspMessage.length <= 32) dspMessage.padEnd(32, ' ') else dspMessage.substring(0, 32)
    }

    /**Este comando envia uma mensagem ao display do pinpad**/
    override fun createRequest(): String {
        return CMD_ID + CMD_LEN1 + DSP_MSG
    }
}