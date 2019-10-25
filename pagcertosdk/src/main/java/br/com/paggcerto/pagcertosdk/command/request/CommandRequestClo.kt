package br.com.paggcerto.pagcertosdk.command.request

internal class CommandRequestClo constructor(cloMessage: String): RequestCard {

    var CMD_ID: String? = null
    var CMD_LEN1: String? = null
    var CLO_MSG: String? = null

    init{
        CMD_ID = "CLO"
        CMD_LEN1 = "032"

        CLO_MSG = if(cloMessage.length <= 32) cloMessage.padEnd(32, ' ') else cloMessage.substring(0, 32)
    }

    /**Este comando libera os recursos de hardware e software alocados pelo pinpad**/
    override fun createRequest(): String {
        return CMD_ID + CMD_LEN1 + CLO_MSG
    }
}